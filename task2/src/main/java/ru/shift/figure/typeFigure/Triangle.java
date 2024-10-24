package ru.shift.figure.typeFigure;

import ru.shift.figure.Figure;
import ru.shift.figure.FigureName;

import java.util.List;

import static java.lang.Math.PI;

public class Triangle extends Figure {
    private final double sideA;
    private final double sideB;
    private final double sideC;

    public Triangle(FigureName nameFigure, List<Double> listParameter) {
        super(nameFigure);
        if (checkingSideTriangle(listParameter)) {
            this.sideA = listParameter.get(0);
            this.sideB = listParameter.get(1);
            this.sideC = listParameter.get(2);
        } else {
            throw new IllegalArgumentException("Не выполнено условие существование треугольника: сумма двух сторон больше третьей стороны.");
        }
    }

    static public boolean checkingSideTriangle(List<Double> paramList) {
        return (paramList.get(0) + paramList.get(1) > paramList.get(2)) &&
                (paramList.get(0) + paramList.get(2) > paramList.get(1)) &&
                (paramList.get(1) + paramList.get(2) > paramList.get(0));
    }

    @Override
    public double calculatePerimeterFigure() {
        return sideA + sideB + sideC;
    }

    @Override
    public double calculateAreaFigure() {
        double halfPerimeterFigure = calculatePerimeterFigure() / 2;
        double sqrArea = halfPerimeterFigure * (halfPerimeterFigure - sideA) *
                (halfPerimeterFigure - sideB) * (halfPerimeterFigure - sideC);
        return Math.sqrt(sqrArea);
    }

    public double getAngleSideA() {
        double numeratorFormula = sideC * sideC + sideB * sideB - sideA * sideA;
        double denominatorFormula = 2 * sideB * sideC;
        double cosAlpha = numeratorFormula / denominatorFormula;

        return Math.acos(cosAlpha);
    }

    public double getAngleSideBOrSideC(double alpha, double side) {
        double multiplierFormula = Math.sin(alpha) / sideA;
        return Math.asin(side * multiplierFormula);
    }

    public double getSideB() {
        return sideB;
    }

    @Override
    public String getInfoFigure() {
        double multiplyForGrad = 180 / PI;
        double alpha = getAngleSideA();
        double beta = getAngleSideBOrSideC(alpha, sideB) * multiplyForGrad;
        double gama = getAngleSideBOrSideC(alpha, sideC) * multiplyForGrad;

        return super.getInfoFigure() +
                String.format("Длина стороны A: %.2f мм \nПротиволежащий угол: %.2f° " +
                        "\nДлина стороны A: %.2f мм \nПротиволежащий угол: %.2f° " +
                        "\nДлина стороны A: %.2f мм \nПротиволежащий угол: %.2f° ", sideA, alpha * multiplyForGrad, sideB, beta, sideC, gama);
    }
}
