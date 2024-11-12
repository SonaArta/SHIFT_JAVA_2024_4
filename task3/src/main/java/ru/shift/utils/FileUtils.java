package ru.shift.utils;

import ru.shift.model.Record;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public final class FileUtils {
    private FileUtils() {

    }

    public static void writeObjectListToFile(List<Record> list, String fileName) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName, false))) {
            objectOutputStream.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createFileIfNotExist(File file) {
        try {
            if (!file.exists()) {
                boolean isExit = file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Record> readObjectListFromFile(String fileName) {
        List<Record> newList = new LinkedList<>();
        File file = new File(fileName);

        createFileIfNotExist(file);
        if (file.length() != 0) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
                newList = (List<Record>) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return newList;
    }
}
