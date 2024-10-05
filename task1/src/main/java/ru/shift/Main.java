package ru.shift;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            int number = enterNumber();

            int minFieldWidth = String.valueOf(number).length();
            int maxFieldWidth = String.valueOf(number * number).length();
            String stringDelimiter = createDelimiter(number, minFieldWidth, maxFieldWidth);

            if (number >= 1 && number <= 32) {
                printMultiplicationTable(number, minFieldWidth, maxFieldWidth, stringDelimiter);
            } else {
                System.out.println("Введенное значение не является допустимым: выход за рамки диапазона.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Введенное значение не является допустимым: введено не целое число.");
        }
    }

    static int enterNumber() {
        System.out.println("Введите целочисленное значение 1 <= n <= 32");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    static String createDelimiter(int number, int minFieldWidth, int maxFieldWidth) {
        StringBuilder stringDelimiter = new StringBuilder(" ".repeat(maxFieldWidth - minFieldWidth) + "-".repeat(minFieldWidth) + "+");
        String singleDelimiter = "-".repeat(maxFieldWidth) + "+";
        stringDelimiter.append(singleDelimiter.repeat(number)).deleteCharAt(stringDelimiter.length() - 1);

        return stringDelimiter.toString();
    }

    static void printMultiplicationTable(int number, int minFieldWidth, int maxFieldWidth, String stringDelimiter) {
        for (int i = 0; i <= number; i++) {
            var insert = (i == 0) ? " ".repeat(maxFieldWidth - minFieldWidth) : i;

            System.out.printf("%" + maxFieldWidth + "s" + "|", insert);

            for (int j = 1; j <= number; j++) {
                int multiplierI = (i == 0) ? 1 : i;
                System.out.printf("%" + maxFieldWidth + "d", multiplierI * j);
                if (j < number) {
                    System.out.print("|");
                }
            }
            System.out.println();
            System.out.println(stringDelimiter);
        }
    }
}