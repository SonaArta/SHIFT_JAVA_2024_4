package ru.shift;

import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) {
        MultiplicationTable multiplicationTable = new MultiplicationTable();
        FormattedTableOutput formattedTableOutput = new FormattedTableOutput(multiplicationTable.getTableSize());
        try (PrintWriter writer = new PrintWriter(System.out)) {
            formattedTableOutput.printMultiplicationTable(multiplicationTable, writer);
        }
    }
}
