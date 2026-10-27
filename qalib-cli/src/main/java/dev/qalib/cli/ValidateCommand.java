package dev.qalib.cli;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;

@Command(name = "validate", description = "Check a model file for errors.")
class ValidateCommand implements Callable<Integer> {

    @Parameters(index = "0", description = "Path to the model file (e.g. qalib.yaml).")
    Path model;

    @Override
    public Integer call() {
        if (!Files.isRegularFile(model)) {
            System.err.println("error: model file not found: " + model);
            return 2;
        }
        // Phase 1: parse with qalib-core, validate, and report errors with line numbers.
        System.out.println("validate: not implemented yet (" + model + ")");
        return 0;
    }
}
