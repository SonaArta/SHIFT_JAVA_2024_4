package ru.shift.utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ReaderParameter {
    private static final int MIN_NUMBER = 1;
    private static final long MAX_NUMBER = 1000000000;
    public static final int NUMBER_PROCESSOR = Runtime.getRuntime().availableProcessors();

    public static long readNumber() {
        long number = 0;
        do {
            try {
                System.out.println("Введите целочисленное значение " + MIN_NUMBER + " <= n <= " + MAX_NUMBER);
                Scanner scanner = new Scanner(System.in);
                number = scanner.nextLong();
                if (number < MIN_NUMBER || number > MAX_NUMBER) {
                    System.out.println("Введенное значение не является допустимым: выход за рамки диапазона.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Введенное значение размера таблицы не является допустимым: введено не целое число.");
            }
        } while (number < MIN_NUMBER || number > MAX_NUMBER);
        return number;
    }
}
