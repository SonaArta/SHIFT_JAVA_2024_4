package ru.shift.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class ReaderParameterUtils {
    private ReaderParameterUtils() {
    }

    public static List<String> readFile(String fileName) {
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


}

