package ru.shift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.calculation.Calculator;
import ru.shift.calculation.function.FirstFunction;
import ru.shift.calculation.function.Function;
import ru.shift.calculation.function.SecondFunction;
import ru.shift.calculation.function.ThirdFunction;

import java.util.List;

public class CalculationStarter {
    private static final Logger logger = LoggerFactory.getLogger(CalculationStarter.class);

    private final long upperLimitMathSeries;
    private final int countThreads;
    private final Calculator calculator;
    private final List<Function> functionList;

    public CalculationStarter(long upperLimitMathSeries, int countThreads) {
        this.upperLimitMathSeries = upperLimitMathSeries;
        this.countThreads = countThreads;
        this.calculator = new Calculator();
        this.functionList = List.of(new FirstFunction(), new SecondFunction(), new ThirdFunction());
    }

    public void startCalculateSumSeries() {
        for (Function function : functionList) {
            long startTime = System.currentTimeMillis();
            double resultSumSeries = calculator.calculate(upperLimitMathSeries, countThreads, function);

            logger.info("Calculation result - {}, calculation time - {} millisecond \n", resultSumSeries, System.currentTimeMillis() - startTime);
        }
    }
}
