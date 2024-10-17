package ru.shift;

import org.apache.commons.cli.ParseException;
import ru.shift.io.parsingParameter.OptionsParser;
import ru.shift.io.parsingParameter.UtilOptions;

import static ru.shift.figure.FigureFactory.createFigure;
import static ru.shift.io.OutputResultUtils.writeData;
import static ru.shift.io.ReaderParameterUtils.readFile;

public class Main {
    public static void main(String[] args) {
        OptionsParser optionsParser = OptionsParser.getOptionsParser();

        try {
            UtilOptions utilOptions = optionsParser.parseOption(args);

            HandlerParameter handlerParameter = new HandlerParameter(readFile(utilOptions.getInputFiles()));
            if (handlerParameter.checkComplianceNumberParameters()) {
                writeData(utilOptions, createFigure(handlerParameter));
            }
        } catch (IllegalArgumentException | ParseException e) {
            System.err.println(e.getMessage());
            optionsParser.printUsage();
        }
    }
}