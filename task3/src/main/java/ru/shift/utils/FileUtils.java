package ru.shift.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.model.Record;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    private FileUtils() {

    }

    public static void writeObjectListToFile(List<Record> list, String fileName) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName, false))) {
            objectOutputStream.writeObject(list);
        } catch (IOException e) {
            logger.error("Error writing file", e);
        }
    }

    public static void createFileIfNotExist(File file) {
        try {
            if (!file.exists()) {
                boolean isExit = file.createNewFile();
            }
        } catch (IOException e) {
            logger.error("Error creating file", e);
        }
    }

    public static List<Record> readObjectListFromFile(String fileName) {
        List<Record> newList = new ArrayList<>();
        File file = new File(fileName);

        createFileIfNotExist(file);
        if (file.length() != 0) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
                newList = (List<Record>) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                logger.error("Error reading file", e);
            }
        }
        return newList;
    }
}
