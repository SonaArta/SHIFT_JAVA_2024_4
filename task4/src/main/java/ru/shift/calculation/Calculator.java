package ru.shift.calculation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.calculation.function.Function;
import ru.shift.utils.Range;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static ru.shift.utils.Utils.prepareRange;

public class Calculator {
    private static final Logger logger = LoggerFactory.getLogger(Calculator.class);

    public Calculator() {
    }

    public double calculate(long number, Function function, int numberThreads) {
        CountDownLatch countDownLatch = new CountDownLatch(numberThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(numberThreads);
        List<Task> tasks = new LinkedList<>();
        List<Range> rangeList = prepareRange(numberThreads, function.getBaseFunction(), number);

        try {
            for (Range range : rangeList) {
                Task task = new Task(range, countDownLatch, function);
                tasks.add(task);
                executorService.submit(task);
            }
            countDownLatch.await();
        } catch (InterruptedException e) {
            logger.error("Error waiting for thread to complete", e);
        } finally {
            executorService.shutdown();
        }

        double resultThreadSum = 0.0;
        for (Task currentTask : tasks) {
            resultThreadSum += currentTask.getResultCalculation();
        }
        return resultThreadSum;
    }
}
