package ru.shift.calculation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.calculation.function.Function;
import ru.shift.utils.Range;

import java.util.concurrent.Callable;

public class Task implements Callable<Double> {
    private static final Logger logger = LoggerFactory.getLogger(Calculator.class);

    private final Range range;
    private final Function function;
    private double resultCalculation;

    public Task(Range range, Function function) {
        this.range = range;
        this.resultCalculation = 0;
        this.function = function;
    }

    @Override
    public Double call() {
        long startTime = System.currentTimeMillis();

        for (long n = range.getStartCalculation(); n <= range.getEndCalculation(); n++) {
            resultCalculation += function.calculateFunction(n);
        }

        logger.info("Calculation result in range from {} to {}: {}. Calculation time - {} millisecond",
                range.getStartCalculation(), range.getEndCalculation(),
                resultCalculation, System.currentTimeMillis() - startTime);

        return resultCalculation;
    }

}
