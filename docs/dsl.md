# Qalib model language (DSL) — draft v0.1

A Qalib model is a single YAML file, usually `qalib.yaml`, kept in git next to
the generated code. It is the source of truth: you change the model and
regenerate, you don't edit generated base code by hand.

> Status: draft. Everything here may change until v1.0.

## Top-level keys

| Key        | Required | Purpose                                         |
|------------|----------|-------------------------------------------------|
| `qalib`     | yes      | DSL version the file is written for (`0.1`)     |
| `app`      | yes      | Name, base package, description                 |
| `auth`     | no       | Auth provider and the list of roles             |
| `entities` | yes      | The domain model                                |
| `targets`  | yes      | Which stacks to generate                        |

## `app`
```yaml
app:
  name: clinic                 # lowercase, letters/digits/dashes
  package: com.example.clinic  # base package / namespace
  description: optional text
```

## `auth`
```yaml
auth:
  provider: jwt                # jwt | keycloak | google | github
  roles: [admin, doctor]       # roles referenced by `access` rules
```
If `auth` is omitted, the generated app has no authentication.
`admin` is not special unless you list it.

## `entities`
Entity names are PascalCase. Every entity gets an `id` automatically
(UUID by default) plus `createdAt` / `updatedAt`.

### Fields
```yaml
fields:
  fullName: { type: string, required: true, min: 2, max: 120 }
```

| Type       | Notes                                   |
|------------|-----------------------------------------|
| `string`   | `min`, `max`, `pattern` (`email`, `phone`, `url`, or a regex) |
| `text`     | long text, no length limit              |
| `int`, `long`, `decimal` | `min`, `max`; `decimal` takes `scale` |
| `boolean`  |                                         |
| `date`, `datetime`, `time` |                       |
| `enum`     | `values: [A, B, C]`                     |

Common options: `required` (default `false`), `unique`, `default`.

### Relations
```yaml
relations:
  patient: { to: Patient, kind: many-to-one, required: true }
```
`kind`: `many-to-one`, `one-to-many`, `one-to-one`, `many-to-many`.
A `many-to-one` on one side implies the inverse `one-to-many` is available;
you only declare it on the other side if you want it exposed there.

### Access rules
```yaml
access:
  read:   [doctor, receptionist]
  write:  [receptionist]        # create + update
  delete: [admin]
  "*":    [admin]               # shorthand for all three
```
Roles must be declared in `auth.roles`. With `auth` present and no `access`
block, an entity is readable and writable by any authenticated user.
Access rules generate backend security config, frontend route guards,
and UI visibility (hidden buttons, read-only forms).

## `targets`
```yaml
targets:
  backend:  { stack: spring-boot, database: postgresql }
  frontend: { stack: angular }
```
v0.1 targets: `spring-boot` (backend), `angular` (frontend).
Planned: `react`, `dotnet`.

## Validation rules (enforced by `qalib validate`)
- Unknown keys are errors, with a suggestion when close to a valid key.
- Every `to:` must name an existing entity.
- Every role in `access` must exist in `auth.roles`.
- Names must be valid identifiers in every selected target language,
  and must not collide with reserved words (`class`, `order`, `user` in SQL...).
- Errors report file, line and column.

## Open questions
- UUID vs. long ids as default; per-entity override?
- Soft delete as an entity option (`softDelete: true`)?
- Field-level access (e.g. hide `notes` from receptionists)?
- Embedded value objects (address, money)?
