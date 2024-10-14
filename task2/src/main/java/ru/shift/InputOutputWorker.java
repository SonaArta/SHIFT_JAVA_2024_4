package ru.shift;

import java.util.Scanner;

class InputOutputWorker {
    InputOutputWorker() {
    }

    static String repeatReadInfoFromConsole() {
        String fileName;
        do {
            Scanner scanner = new Scanner(System.in);
            fileName = scanner.nextLine();
        } while (fileName.isEmpty());
        return fileName;
    }
}
