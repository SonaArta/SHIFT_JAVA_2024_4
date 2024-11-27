package ru.shift.calculation.function;

public class SecondFunction extends Function {
    public SecondFunction() {
        super(1);
    }

    @Override
    public double calculateFunction(long n) {
        return 1.0 / (n * n);
    }
}
