package ru.shift.figure;

public enum FigureName {
    CIRCLE("Круг"),
    TRIANGLE("Треугольник"),
    RECTANGLE("Прямоугольник");

    private final String figureName;

    FigureName(String name) {
        this.figureName = name;
    }

    public String getFigureName() {
        return figureName;
    }
}
