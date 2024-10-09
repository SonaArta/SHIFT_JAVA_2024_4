package ru.shift;

import java.io.PrintWriter;
import static ru.shift.Constants.*;

class FormattedTableOutput {
    private final String stringDelimiter;
    private final int minFieldWidth;
    private final int maxFieldWidth;
    private final String format;

    FormattedTableOutput(int tableSize) {
        this.minFieldWidth = String.valueOf(tableSize).length();
        this.maxFieldWidth = String.valueOf(tableSize * tableSize).length();
        this.stringDelimiter = createDelimiter(tableSize);
        this.format = "%" + maxFieldWidth + "d%s";
    }

    private String createDelimiter(int tableSize) {
        int differenceMaxMinFieldWidth = maxFieldWidth - minFieldWidth;

        String zeroDelimiter = SPACE.repeat(differenceMaxMinFieldWidth) + MINUS.repeat(minFieldWidth);
        String singleDelimiter = PLUS + MINUS.repeat(maxFieldWidth);

        return zeroDelimiter + singleDelimiter.repeat(tableSize);
    }

    private void printHeadMultiplicationTable(int tableSize, PrintWriter writer) {
        writer.print(SPACE.repeat(maxFieldWidth) + "|");
        for (int i = 1; i <= tableSize; i++) {
            String valueDelimiter = (i == tableSize) ? "\n" : "|";
            writer.printf(format, i, valueDelimiter);
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
                writer.printf(format, table[i][j], valueDelimiter);
            }
            writer.println(stringDelimiter);
        }
    }
}
