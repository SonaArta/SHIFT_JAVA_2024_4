package ru.shift;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static ru.shift.ReaderParameterUtils.readFileName;

final class OutputResultUtils extends InputOutputWorker {
    private OutputResultUtils() {
        super();
    }

    static String selectionResultRecordArea() {
        System.out.println("Вывод результата должен осуществляться в файл или консоль? Введите 'file' или 'console'.");
        return repeatReadInfoFromConsole();
    }

    static void writeData(Figure figure) {
        if (selectionResultRecordArea().equals("file")) {
            try (PrintWriter writer = new PrintWriter(readFileName())) {
                writer.println(figure.toString());
            } catch (FileNotFoundException e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.out.println(figure.toString());
        }
    }
}
