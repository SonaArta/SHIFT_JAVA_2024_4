package ru.shift.calculation.function;

public class ThirdFunction extends Function {

    public ThirdFunction() {
        super(1);
    }

    @Override
    public double calculateFunction(long n) {
        return 1.0 / (n * (n + 1));
    }
}
