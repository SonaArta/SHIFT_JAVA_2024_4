package ru.shift.utils;

public class Range {
    private final long startCalculation;
    private final long endCalculation;

    public Range(long startCalculation, long endCalculation) {
        this.startCalculation = startCalculation;
        this.endCalculation = endCalculation;
    }

    public long getStartCalculation() {
        return startCalculation;
    }

    public long getEndCalculation() {
        return endCalculation;
    }

}
