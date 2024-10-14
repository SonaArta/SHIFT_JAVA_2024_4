package ru.shift;

abstract class Figure {
    FigureName nameFigure;
    double perimeterFigure;
    double areaFigure;

    Figure(FigureName nameFigure) {
        this.nameFigure = nameFigure;
    }

    abstract double calculatePerimeterFigure();

    abstract double calculateAreaFigure();

    @Override
    public String toString() {
        return String.format("Тип фигуры: %s \nПлощадь: %.2f кв. мм \nПериметр: %.2f мм\n", nameFigure, areaFigure, perimeterFigure);
    }
}
