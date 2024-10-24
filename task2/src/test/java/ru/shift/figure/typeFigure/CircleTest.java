package ru.shift.figure.typeFigure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.lang.Math.PI;
import static ru.shift.figure.FigureName.CIRCLE;

class CircleTest {
    static double radius;
    static Circle circle;

    static double exactPerimeter;
    static double exactArea;
    static String exactInfo;

    @BeforeAll
    static void setUp() {
        radius = 5.0;
        circle = new Circle(CIRCLE, List.of(radius));
        exactPerimeter = 2 * PI * radius;
        exactArea = PI * radius * radius;
        exactInfo = String.format("Тип фигуры: %s \nПлощадь: %.2f кв. мм \nПериметр: %.2f мм\nРадиус: %.2f мм \nДиаметр: %.2f мм",
                "Круг", exactArea, exactPerimeter, radius, radius * 2);

    }

    @Test
    void calculatePerimeterFigure() {
        Assertions.assertEquals(exactPerimeter, circle.calculatePerimeterFigure());
    }

    @Test
    void calculateAreaFigure() {
        Assertions.assertEquals(exactArea, circle.calculateAreaFigure());
    }

    @Test
    void getInfoFigure() {
        Assertions.assertEquals(exactInfo, circle.getInfoFigure());
    }
}