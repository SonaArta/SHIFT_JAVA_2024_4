package ru.shift.calculation.function;

public class FirstFunction extends Function {
    public FirstFunction() {
        super(0);
    }

    @Override
    public double calculateFunction(long n) {
        return 1.0 / Math.pow(2, n);
    }
}

