package ru.shift;

import java.util.InputMismatchException;

public class MultiplicationTable {
    private int tableSize;
    int[][] table;

    public MultiplicationTable() {
        try {
            this.tableSize = new ReaderParameter().readTableSize();
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

    void createMultiplicationTable() {
        for (int i = 1; i <= tableSize; i++) {
            for (int j = 0; j <= tableSize; j++) {
                int multiplierJ = (j == 0) ? 1 : j;
                table[i][j] = multiplierJ * i;
            }
        }
    }
}
