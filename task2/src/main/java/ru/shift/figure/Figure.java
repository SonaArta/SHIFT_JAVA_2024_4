package ru.shift.figure;

public abstract class Figure {
    FigureName nameFigure;

    public Figure(FigureName nameFigure) {
        this.nameFigure = nameFigure;
    }

    public abstract double calculatePerimeterFigure();

    public abstract double calculateAreaFigure();

    public String getInfoFigure() {
        return String.format("Тип фигуры: %s \nПлощадь: %.2f кв. мм \nПериметр: %.2f мм\n", nameFigure.getFigureName(), calculateAreaFigure(), calculatePerimeterFigure());
    }
}
