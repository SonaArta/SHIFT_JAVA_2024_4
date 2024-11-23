package ru.shift.calculation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.calculation.function.Function;
import ru.shift.utils.Range;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Calculator {
    private static final Logger logger = LoggerFactory.getLogger(Calculator.class);

    private static List<Range> prepareRange(int countThreads, long baseSeries, long upperLimitMathSeries) {
        List<Range> rangeList = new ArrayList<>(countThreads);

        long step = upperLimitMathSeries / countThreads;
        long startCalculation = baseSeries;
        for (int i = 0; i < countThreads; i++) {
            long endCalculation = (i == countThreads - 1) ? upperLimitMathSeries : startCalculation + step - 1;
            Range range = new Range(startCalculation, endCalculation);
            rangeList.add(range);

            startCalculation += step;
        }
        return rangeList;
    }

    public double calculate(long upperLimitMathSeries, int countThreads, Function function) {
        ExecutorService executorService = Executors.newFixedThreadPool(countThreads);
        List<Range> rangeList = prepareRange(countThreads, function.getBaseFunction(), upperLimitMathSeries);
        List<Future<Double>> futuresTask = new LinkedList<>();
        double resultSumThread = 0;

        try {
            for (Range range : rangeList) {
                Future<Double> future = executorService.submit(new Task(range, function));
                futuresTask.add(future);
            }

            for (Future<Double> future : futuresTask) {
                resultSumThread += future.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            logger.error("Error waiting for thread to complete", e);
        } finally {
            executorService.shutdown();
        }
        return resultSumThread;
    }
}
