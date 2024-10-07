package ru.shift;

import java.util.Scanner;

import static ru.shift.Constants.*;

public class ReaderParameter {
    public ReaderParameter() {
    }

    public int readTableSize() {
        int tableSize;
        do {
            System.out.println("Введите целочисленное значение " + MIN_TABLE_SIZE + " <= n <= " + MAX_TABLE_SIZE);
            Scanner scanner = new Scanner(System.in);
            tableSize = scanner.nextInt();
            if (tableSize < MIN_TABLE_SIZE || tableSize > MAX_TABLE_SIZE) {
                System.out.println("Введенное значение не является допустимым: выход за рамки диапазона.");
            }
        } while (tableSize < MIN_TABLE_SIZE || tableSize > MAX_TABLE_SIZE);
        return tableSize;
    }
}
