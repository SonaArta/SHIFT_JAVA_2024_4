package ru.shift;

import java.util.InputMismatchException;
import java.util.Scanner;

import static ru.shift.Constants.MAX_TABLE_SIZE;
import static ru.shift.Constants.MIN_TABLE_SIZE;

public class MultiplicationTable {
    private int tableSize;
    int[][] table;

    public MultiplicationTable() {
        try {
            this.tableSize = readTableSize();
            this.table = new int[tableSize + 1][tableSize + 1];
            createMultiplicationTable();
        } catch (InputMismatchException e) {
            System.out.println("Введенное значение размера таблицы не является допустимым: введено не целое число.");
        }
    }

    public int getTableSize() {
        return tableSize;
    }

    public void setTableSize(int tableSize) {
        this.tableSize = tableSize;
    }

    public int[][] getTable() {
        return table;
    }

    public void setTable(int[][] table) {
        this.table = table;
    }

    private int readTableSize() {
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

    void createMultiplicationTable() {
        for (int i = 1; i <= tableSize; i++) {
            for (int j = 0; j <= tableSize; j++) {
                int multiplierJ = (j == 0) ? 1 : j;
                table[i][j] = multiplierJ * i;
            }
        }
    }
}
