package ru.shift;

import java.util.List;

class Rectangle extends Figure {
    private final double sideA;
    private final double sideB;

    Rectangle(FigureName nameFigure, List<Double> listParameter) {
        super(nameFigure);
        this.sideA = listParameter.get(0);
        this.sideB = listParameter.get(1);
        perimeterFigure = calculatePerimeterFigure();
        areaFigure = calculateAreaFigure();
    }

    @Override
    double calculatePerimeterFigure() {
        return 2 * sideA + 2 * sideB;
    }

    @Override
    double calculateAreaFigure() {
        return sideA * sideB;
    }

    double getDiagonal() {
        return Math.sqrt(sideA * sideA + sideB * sideB);
    }

    double getWidth() {
        return Math.min(sideA, sideB);
    }

    double getLength() {
        return Math.max(sideA, sideB);
    }

    @Override
    public String toString() {
        return super.toString() +
                String.format("Длина диагонали: %.2f мм \nДлина: %.2f мм \nШирина: %.2f мм", getDiagonal(), getLength(), getWidth());
    }
}
