package dev.qalib.cli;

import dev.qalib.core.Qalib;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
        name = "qalib",
        mixinStandardHelpOptions = true,
        versionProvider = QalibCli.Version.class,
        description = "Generate full-stack applications from a model.",
        subcommands = {ValidateCommand.class, CommandLine.HelpCommand.class})
public class QalibCli implements Runnable {

    public static void main(String[] args) {
        System.exit(new CommandLine(new QalibCli()).execute(args));
    }

    @Override
    public void run() {
        new CommandLine(this).usage(System.out);
    }

    static class Version implements CommandLine.IVersionProvider {
        @Override
        public String[] getVersion() {
            return new String[] {"qalib " + Qalib.VERSION + " (DSL " + Qalib.DSL_VERSION + ")"};
        }
    }
}
