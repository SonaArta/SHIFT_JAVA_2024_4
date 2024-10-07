package ru.shift;

import java.io.PrintWriter;

import static ru.shift.Constants.*;

public class FormattedTableOutput {
    String stringDelimiter;
    int minFieldWidth;
    int maxFieldWidth;

    public FormattedTableOutput(int tableSize) {
        this.minFieldWidth = String.valueOf(tableSize).length();
        this.maxFieldWidth = String.valueOf(tableSize * tableSize).length();
        this.stringDelimiter = createDelimiter(tableSize);
    }

    String createDelimiter(int tableSize) {
        int differenceMaxMinFieldWidth = maxFieldWidth - minFieldWidth;

        String zeroDelimiter = SPACE.repeat(differenceMaxMinFieldWidth) + MINUS.repeat(minFieldWidth);
        String singleDelimiter = PLUS + MINUS.repeat(maxFieldWidth);
        //int capacity = differenceMaxMinFieldWidth + minFieldWidth + singleDelimiter.length() * tableSize;

        return zeroDelimiter + singleDelimiter.repeat(tableSize);
    }

    void printHeadMultiplicationTable(int tableSize, PrintWriter writer) {
        System.out.print(SPACE.repeat(maxFieldWidth) + "|");
        for (int i = 1; i <= tableSize; i++) {
            String valueDelimiter = (i == tableSize) ? "\n" : "|";
            writer.printf("%" + maxFieldWidth + "d%s", i, valueDelimiter);
        }
        writer.println(stringDelimiter);
    }

    void printMultiplicationTable(MultiplicationTable multiplicationTable, PrintWriter writer) {
        int[][] table = multiplicationTable.getTable();
        int tableSize = multiplicationTable.getTableSize();
        printHeadMultiplicationTable(tableSize, writer);
        for (int i = 1; i <= tableSize; i++) {
            for (int j = 0; j <= tableSize; j++) {
                String valueDelimiter = (j == tableSize) ? "\n" : "|";

                writer.printf("%" + maxFieldWidth + "d%s", table[i][j], valueDelimiter);
            }
            writer.println(stringDelimiter);
        }
    }
}
