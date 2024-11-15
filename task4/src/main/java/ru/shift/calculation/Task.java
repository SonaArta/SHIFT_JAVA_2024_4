package ru.shift.calculation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.calculation.function.Function;
import ru.shift.utils.Range;

import java.util.concurrent.CountDownLatch;

public class Task implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Calculator.class);

    private final Range range;
    private final CountDownLatch countDownLatch;
    private final Function function;
    private double resultCalculation;

    public Task(Range range, CountDownLatch countDownLatch, Function function) {
        this.range = range;
        this.resultCalculation = 0;
        this.countDownLatch = countDownLatch;
        this.function = function;
    }

    public double getResultCalculation() {
        return resultCalculation;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();

        for (long n = range.getStartCalculation(); n <= range.getEndCalculation(); n++) {
            resultCalculation += function.calculateFunction(n);
        }
        countDownLatch.countDown();
        long resultTime = System.currentTimeMillis() - startTime;
        logger.info("Calculation result in range from {} to {}: {}. Calculation time - {} millisecond",
                range.getStartCalculation(), range.getEndCalculation(), resultCalculation, resultTime);
    }

}
