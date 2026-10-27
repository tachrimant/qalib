# Qalib

*Qalib* (قالب) is Arabic for *mold* or *template*: pour in your model, get your app.

Model-driven generator for full-stack applications.
Describe your domain once in a YAML model; Qalib generates production-ready
backends and frontends — and regenerates safely as the model evolves.

## Status
Phase 0 — project skeleton and DSL draft. Nothing is generated yet.

## Modules
| Module | Purpose |
|---|---|
| `qalib-core` | DSL parser, validator, intermediate model (IR) |
| `qalib-engine` | Template rendering, file writer, safe regeneration |
| `targets/qalib-target-spring-boot` | Spring Boot target |
| `qalib-cli` | The `qalib` command-line tool |

## Build
Requires Java 21 and Maven 3.9+.
```bash
mvn package
java -jar qalib-cli/target/qalib.jar --version
java -jar qalib-cli/target/qalib.jar validate examples/clinic.yaml
```

## Roadmap
1. DSL spec, parser, validator with line-numbered errors, IR, `qalib validate`
2. Spring Boot target: entities, repositories, services, REST, Flyway migrations
3. Auth: JWT, then OAuth2/OIDC and Keycloak, driven by `access` rules
4. Angular target, then React
5. Safe regeneration and migrations from model diffs (`qalib diff`)
6. .NET target, OpenAPI, Docker/CI, plugin SDK

See [docs/dsl.md](docs/dsl.md) for the model language.

## License
Proprietary — all rights reserved (for now).
