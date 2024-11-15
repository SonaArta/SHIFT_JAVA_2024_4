package ru.shift.calculation.function;

public abstract class Function {
    int baseFunction;

    public Function(int baseFunction) {
        this.baseFunction = baseFunction;
    }

    public int getBaseFunction() {
        return baseFunction;
    }

    abstract public double calculateFunction(long n);
}
