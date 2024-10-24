package ru.shift.figure.typeFigure;

import ru.shift.figure.Figure;
import ru.shift.figure.FigureName;

import java.util.List;

import static java.lang.Math.PI;


public class Circle extends Figure {
    private final double radius;
    private final double diameter;

    public Circle(FigureName nameFigure, List<Double> listParameter) {
        super(nameFigure);
        this.radius = listParameter.get(0);
        this.diameter = this.radius * 2;
    }

    @Override
    public double calculatePerimeterFigure() {
        return 2 * PI * radius;
    }

    @Override
    public double calculateAreaFigure() {
        return PI * radius * radius;
    }

    @Override
    public String getInfoFigure() {
        return super.getInfoFigure() +
                String.format("Радиус: %.2f мм \nДиаметр: %.2f мм", radius, diameter);
    }
}
