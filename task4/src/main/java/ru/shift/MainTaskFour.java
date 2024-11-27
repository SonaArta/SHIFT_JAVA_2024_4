package ru.shift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.calculation.Calculator;

import static ru.shift.utils.ReaderParameter.NUMBER_PROCESSOR;
import static ru.shift.utils.ReaderParameter.readNumber;

public class MainTaskFour {
    private static final Logger logger = LoggerFactory.getLogger(Calculator.class);

    public static void main(String[] args) {
        long upperLimitMathSeries = readNumber();
        int countThreads = (int) Math.min(upperLimitMathSeries, NUMBER_PROCESSOR);
        logger.info("Number N = {}, count of threads = {} \n", upperLimitMathSeries, countThreads);

        new CalculationStarter(upperLimitMathSeries, countThreads).startCalculateSumSeries();
    }

}
