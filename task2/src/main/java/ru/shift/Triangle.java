package ru.shift;

import java.util.List;

import static java.lang.Math.PI;

public class Triangle extends Figure {
    private final double sideA;
    private final double sideB;
    private final double sideC;

    public Triangle(FigureName nameFigure, List<Double> listParameter) {
        super(nameFigure);
        this.sideA = listParameter.get(0);
        this.sideC = listParameter.get(1);
        this.sideB = listParameter.get(2);
        perimeterFigure = calculatePerimeterFigure();
        areaFigure = calculateAreaFigure();
    }

    @Override
    double calculatePerimeterFigure() {
        return sideA + sideB + sideC;
    }

    @Override
    double calculateAreaFigure() {
        double sqrArea = perimeterFigure * (perimeterFigure - sideA) *
                (perimeterFigure - sideB) * (perimeterFigure - sideC);
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

    @Override
    public String toString() {
        double multiplyForGrad = 180 / PI;
        double alpha = getAngleSideA();
        double beta = getAngleSideBOrSideC(alpha, sideB) * multiplyForGrad;
        double gama = getAngleSideBOrSideC(alpha, sideC) * multiplyForGrad;

        return super.toString() +
                String.format("Длина стороны A: %.2f мм \nПротиволежащий угол: %.2f° " +
                        "\nДлина стороны A: %.2f мм \nПротиволежащий угол: %.2f° " +
                        "\nДлина стороны A: %.2f мм \nПротиволежащий угол: %.2f° ", sideA, alpha * multiplyForGrad, sideB, beta, sideC, gama);
    }
}
