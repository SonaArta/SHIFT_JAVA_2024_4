package ru.shift;

import java.util.InputMismatchException;
import java.util.Scanner;

import static ru.shift.Constants.MAX_TABLE_SIZE;
import static ru.shift.Constants.MIN_TABLE_SIZE;

final class ReaderParameterUtils {
    private ReaderParameterUtils() {
    }

    static int readTableSize() {
        int tableSize = 0;
        do {
            try {
                System.out.println("Введите целочисленное значение " + MIN_TABLE_SIZE + " <= n <= " + MAX_TABLE_SIZE);
                Scanner scanner = new Scanner(System.in);
                tableSize = scanner.nextInt();
                if (tableSize < MIN_TABLE_SIZE || tableSize > MAX_TABLE_SIZE) {
                    System.out.println("Введенное значение не является допустимым: выход за рамки диапазона.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Введенное значение размера таблицы не является допустимым: введено не целое число.");
            }
        } while (tableSize < MIN_TABLE_SIZE || tableSize > MAX_TABLE_SIZE);
        return tableSize;
    }
}

