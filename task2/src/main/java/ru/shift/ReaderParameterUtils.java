package ru.shift;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

final class ReaderParameterUtils extends InputOutputWorker {
    private ReaderParameterUtils() {
        super();
    }

    static String readFileName() {
        System.out.println("Введите имя файла:");
        return repeatReadInfoFromConsole();
    }

    static List<String> readFile(String fileName) {
        List<String> dataFile = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isEmpty()) {
                    dataFile.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Файл не существует");
        }
        return dataFile;
    }

    static HandlerParameter loopingGettingParameters() {
        HandlerParameter handlerParameter;
        do {
            List<String> dataFile = readFile(readFileName());
            handlerParameter = new HandlerParameter(dataFile);
        } while (!(handlerParameter.checkingComplianceNumberParameters() && handlerParameter.checkingSideTriangle()));
        return handlerParameter;
    }

}

