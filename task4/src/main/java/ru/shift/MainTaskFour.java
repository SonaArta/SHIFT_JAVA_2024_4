package ru.shift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.calculation.Calculator;
import ru.shift.calculation.function.FirstFunction;
import ru.shift.calculation.function.Function;
import ru.shift.calculation.function.SecondFunction;
import ru.shift.calculation.function.ThirdFunction;

import static ru.shift.utils.ReaderParameter.NUMBER_PROCESSOR;
import static ru.shift.utils.ReaderParameter.readNumber;

public class MainTaskFour {
    private static final Logger logger = LoggerFactory.getLogger(Calculator.class);

    public static void main(String[] args) {
        long number = readNumber();
        int countThreads = (int) Math.min(number, NUMBER_PROCESSOR);
        logger.info("Number N = {}, count of threads = {} \n", number, countThreads);

        Calculator calculator = new Calculator();
        Function firstFunction = new FirstFunction();
        Function secondFunction = new SecondFunction();
        Function thirdFunction = new ThirdFunction();

        startCalculateSumSeries(number, countThreads, calculator, firstFunction);
        startCalculateSumSeries(number, countThreads, calculator, secondFunction);
        startCalculateSumSeries(number, countThreads, calculator, thirdFunction);
    }

    private static void startCalculateSumSeries(long number, int countThreads, Calculator calculator, Function function) {
        long startTime = System.currentTimeMillis();
        double resultSumSeries = calculator.calculate(number, countThreads, function);

        logger.info("Calculation result - {}, calculation time - {} millisecond \n", resultSumSeries, System.currentTimeMillis() - startTime);
    }
}
