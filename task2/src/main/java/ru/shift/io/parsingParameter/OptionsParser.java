package ru.shift.io.parsingParameter;

import org.apache.commons.cli.*;

public class OptionsParser {
    private static OptionsParser optionsParser;
    private final Options options;

    private OptionsParser() {
        options = new Options();
        options.addOption(OptionName.FILE_WRITE.getOptionName(), true, "Указание на запись информации о фигурах в файл");
        options.addOption(OptionName.CONSOLE_WRITE.getOptionName(), false, "Указание на запись информации о фигурах в консоль");
        options.addOption("h", "help", false, "Info");
    }

    public static OptionsParser getOptionsParser() {
        if (optionsParser == null) {
            optionsParser = new OptionsParser();
        }
        return optionsParser;
    }

    public UtilOptions parseOption(String[] args) throws ParseException {
        CommandLineParser commandLineParser = new PosixParser();
        CommandLine commandLine = commandLineParser.parse(options, args);

        if (commandLine.hasOption(OptionName.FILE_WRITE.getOptionName()) && commandLine.hasOption(OptionName.CONSOLE_WRITE.getOptionName())) {
            throw new IllegalArgumentException("Запись лишь в один источник - консоль или файл");
        }

        if (!commandLine.hasOption(OptionName.FILE_WRITE.getOptionName()) && !commandLine.hasOption(OptionName.CONSOLE_WRITE.getOptionName())) {
            throw new IllegalArgumentException("Необходимо указать куда будет записываться информация о фигуре - в консоль или файл");
        }

        if (commandLine.getArgs().length < 1) {
            throw new IllegalArgumentException("Нет входного файла для считывания информации об объекте");
        }

        return buildUtilOptions(commandLine);
    }

    private UtilOptions buildUtilOptions(CommandLine commandLine) {
        UtilOptions utilOptions = new UtilOptions();

        if (commandLine.hasOption(OptionName.FILE_WRITE.getOptionName())) {
            String outputPath = commandLine.getOptionValue(OptionName.FILE_WRITE.getOptionName());
            if (outputPath.charAt(0) != '\\') {
                outputPath = '\\' + outputPath;
            }
            utilOptions.setOutputFileName(System.getProperty("user.dir") + outputPath);
            utilOptions.setWriteMode(WriteMode.FILE);
        } else {
            utilOptions.setWriteMode(WriteMode.CONSOLE);
        }

        utilOptions.setInputFiles(commandLine.getArgList().get(0));

        return utilOptions;
    }

    public void printUsage() {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("HELP", options);
    }
}