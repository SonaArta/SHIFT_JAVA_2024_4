package ru.shift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.calculation.*;
import ru.shift.calculation.function.*;
import static ru.shift.utils.Utils.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Calculator.class);

    private static void startCalculateSumSeries(long number, Function function, int numberThreads, Calculator calculator) {
        long startTime = System.currentTimeMillis();
        double resultFunction = calculator.calculate(number, function, numberThreads);
        long resultTime = System.currentTimeMillis() - startTime;

        logger.info("Calculation result - {}, calculation time - {} millisecond \n", resultFunction, resultTime);
    }

    public static void main(String[] args) {
        long number = readNumber();
        int numberThreads = (int) Math.min(number, NUMBER_PROCESSOR);
        logger.info("Number N = {}, count of threads = {} \n", number, numberThreads);

        Calculator calculator = new Calculator();
        Function firstFunction = new FirstFunction();
        Function secondFunction = new SecondFunction();
        Function thirdFunction = new ThirdFunction();

        startCalculateSumSeries(number, firstFunction, numberThreads, calculator);
        startCalculateSumSeries(number, secondFunction, numberThreads, calculator);
        startCalculateSumSeries(number, thirdFunction, numberThreads, calculator);
    }
}