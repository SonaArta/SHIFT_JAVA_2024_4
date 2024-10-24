package ru.shift.figure.typeFigure;

import ru.shift.figure.Figure;
import ru.shift.figure.FigureName;

import java.util.List;

public class Rectangle extends Figure {
    private final double sideA;
    private final double sideB;

    public Rectangle(FigureName nameFigure, List<Double> listParameter) {
        super(nameFigure);
        this.sideA = listParameter.get(0);
        this.sideB = listParameter.get(1);
    }

    @Override
    public double calculatePerimeterFigure() {
        return 2 * sideA + 2 * sideB;
    }

    @Override
    public double calculateAreaFigure() {
        return sideA * sideB;
    }

    public double getDiagonal() {
        return Math.sqrt(sideA * sideA + sideB * sideB);
    }

    public double getWidth() {
        return Math.min(sideA, sideB);
    }

    public double getLength() {
        return Math.max(sideA, sideB);
    }

    @Override
    public String getInfoFigure() {
        return super.getInfoFigure() +
                String.format("Длина диагонали: %.2f мм \nДлина: %.2f мм \nШирина: %.2f мм", getDiagonal(), getLength(), getWidth());
    }
}
