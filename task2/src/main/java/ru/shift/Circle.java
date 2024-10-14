package ru.shift;

import java.util.List;

import static java.lang.Math.PI;


class Circle extends Figure {
    private final double radius;
    private final double diameter;

    Circle(FigureName nameFigure, List<Double> listParameter) {
        super(nameFigure);
        this.radius = listParameter.get(0);
        this.diameter = this.radius * 2;
        perimeterFigure = calculatePerimeterFigure();
        areaFigure = calculateAreaFigure();
    }

    @Override
    double calculatePerimeterFigure() {
        return 2 * PI * radius;
    }

    @Override
    double calculateAreaFigure() {
        return PI * radius * radius;
    }

    @Override
    public String toString() {
        return super.toString() +
                String.format("Радиус: %.2f мм \nДиаметр: %.2f мм", radius, diameter);
    }
}
