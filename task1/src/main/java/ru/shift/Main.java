package ru.shift;

import java.io.PrintWriter;

import static ru.shift.ReaderParameterUtils.readTableSize;

public class Main {
    public static void main(String[] args) {
        int tableSize = readTableSize();
        try (PrintWriter writer = new PrintWriter(System.out)) {
            new FormattedTableOutput(tableSize).printMultiplicationTable(new MultiplicationTable(tableSize), writer);
        }
    }
}
