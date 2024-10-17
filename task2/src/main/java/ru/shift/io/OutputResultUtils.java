package ru.shift.io;

import ru.shift.figure.Figure;
import ru.shift.io.parsingParameter.UtilOptions;
import ru.shift.io.parsingParameter.WriteMode;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public final class OutputResultUtils {
    private OutputResultUtils() {
    }

    public static void writeData(UtilOptions utilOptions, Figure figure) {
        if ((utilOptions.getWriteMode()).equals(WriteMode.FILE)) {
            try (PrintWriter writer = new PrintWriter(utilOptions.getOutputFileName())) {
                writer.println(figure.getInfoFigure());
            } catch (FileNotFoundException e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.out.println(figure.getInfoFigure());
        }
    }
}
