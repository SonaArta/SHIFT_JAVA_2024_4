package ru.shift;

class MultiplicationTable {
    private final int tableSize;
    private final int[][] table;

    MultiplicationTable(int tableSize) {
        this.tableSize = tableSize;
        this.table = new int[tableSize + 1][tableSize + 1];
        createMultiplicationTable();
    }

    int getTableSize() {
        return tableSize;
    }

    int[][] getTable() {
        return table;
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
