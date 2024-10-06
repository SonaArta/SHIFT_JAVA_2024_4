package ru.shift;

import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static final int MIN_TABLE_SIZE = 1;
    public static final int MAX_TABLE_SIZE = 32;
    public static final String PLUS = "+";
    public static final String MINUS = "-";
    public static final String SPACE = " ";

    public static void main(String[] args) {
        try (PrintWriter writer = new PrintWriter(System.out)) {
            int tableSize;
            do {
                tableSize = readTableSize();
                if (tableSize >= MIN_TABLE_SIZE && tableSize <= MAX_TABLE_SIZE) {
                    int minFieldWidth = String.valueOf(tableSize).length();
                    int maxFieldWidth = String.valueOf(tableSize * tableSize).length();
                    String stringDelimiter = createDelimiter(tableSize, minFieldWidth, maxFieldWidth);

                    printMultiplicationTable(writer, tableSize, maxFieldWidth, stringDelimiter);
                } else {
                    writer.println("Введенное значение не является допустимым: выход за рамки диапазона.");
                }
            } while (tableSize < MIN_TABLE_SIZE || tableSize > MAX_TABLE_SIZE);
        } catch (InputMismatchException e) {
            System.out.println("Введенное значение не является допустимым: введено не целое число.");
        }
    }

    private static int readTableSize() {
        System.out.println("Введите целочисленное значение " + MIN_TABLE_SIZE + " <= n <= " + MAX_TABLE_SIZE);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    static String createDelimiter(int tableSize, int minFieldWidth, int maxFieldWidth) {
        int differenceMaxMinFieldWidth = maxFieldWidth - minFieldWidth;

        String zeroDelimiter = SPACE.repeat(differenceMaxMinFieldWidth) + MINUS.repeat(minFieldWidth);
        String singleDelimiter = PLUS + MINUS.repeat(maxFieldWidth);
        //int capacity = differenceMaxMinFieldWidth + minFieldWidth + singleDelimiter.length() * tableSize;

        return zeroDelimiter + singleDelimiter.repeat(tableSize);
    }


    static void printHeadMultiplicationTable(PrintWriter writer, int tableSize, int maxFieldWidth, String stringDelimiter) {
        System.out.print(SPACE.repeat(maxFieldWidth) + "|");
        for (int i = 1; i <= tableSize; i++) {
            String valueDelimiter = (i == tableSize) ? "\n" : "|";
            writer.printf("%" + maxFieldWidth + "d%s", i, valueDelimiter);
        }
        writer.println(stringDelimiter);
    }

    static void printMultiplicationTable(PrintWriter writer, int tableSize, int maxFieldWidth, String stringDelimiter) {
        printHeadMultiplicationTable(writer, tableSize, maxFieldWidth, stringDelimiter);
        for (int i = 1; i <= tableSize; i++) {
            for (int j = 0; j <= tableSize; j++) {
                int multiplierJ = (j == 0) ? 1 : j;
                String valueDelimiter = (j == tableSize) ? "\n" : "|";

                writer.printf("%" + maxFieldWidth + "d%s", multiplierJ * i, valueDelimiter);
            }
            writer.println(stringDelimiter);
        }
    }
}