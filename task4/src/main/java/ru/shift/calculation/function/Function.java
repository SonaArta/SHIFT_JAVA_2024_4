package ru.shift.calculation.function;

public abstract class Function {
    int baseFunction;

    protected Function(int baseFunction) {
        this.baseFunction = baseFunction;
    }

    public int getBaseFunction() {
        return baseFunction;
    }

    public abstract double calculateFunction(long n);
}
