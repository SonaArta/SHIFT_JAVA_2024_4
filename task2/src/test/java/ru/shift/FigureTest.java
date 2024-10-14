package ru.shift;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static java.lang.Math.PI;
import static ru.shift.FigureName.*;

class FigureTest {
    static double radius = 3.0;
    static double sideA = 1.0;
    static double sideB = 2.0;
    static double sideC = 3.0;
    static Circle circle = new Circle(CIRCLE, List.of(radius));
    static Triangle triangle = new Triangle(TRIANGLE, List.of(sideA, sideB, sideC));
    static Rectangle rectangle = new Rectangle(RECTANGLE, List.of(sideA, sideB));

    @ParameterizedTest(name = "Тесты для проверки периметра с параметрами {0} и {1}")
    @MethodSource("whenFigureThenPerimeterProvider")
    @DisplayName("Поле периметр фигуры - {0} сравнивается с точным значением периметра - {1}")
    void whenFigureThenPerimeter(Figure figure, double perimeter) {
        Assertions.assertEquals(perimeter, figure.perimeterFigure);
    }

    static Stream<Arguments> whenFigureThenPerimeterProvider() {
        return Stream.of(
                Arguments.arguments(circle, 2 * PI * radius),
                Arguments.arguments(triangle, sideA + sideB + sideC),
                Arguments.arguments(rectangle, 2 * sideA + 2 * sideB)
        );
    }

    @ParameterizedTest(name = "Тесты для проверки площади с параметрами {0} и {1}")
    @MethodSource("whenFigureThenAreaProvider")
    @DisplayName("Поле площадь фигуры - {0} сравнивается с точным значением площади - {1}")
    void whenFigureThenArea(Figure figure, double area) {
        Assertions.assertEquals(area, figure.areaFigure);
    }

    static Stream<Arguments> whenFigureThenAreaProvider() {
        return Stream.of(
                Arguments.arguments(circle, PI * radius * radius),
                Arguments.arguments(triangle, Math.sqrt((sideA + sideB + sideC)
                        * (sideB + sideC)
                        * (sideA + sideC)
                        * (sideA + sideB))),
                Arguments.arguments(rectangle, sideA * sideB)
        );
    }

    @Test
    @DisplayName("Когда вызывается метод прямоугольника getDiagonal, возвращается диагональ")
    void whenRectangleGetDiagonalReturnDiagonal() {
        Assertions.assertEquals(Math.sqrt(sideA * sideA + sideB * sideB), rectangle.getDiagonal());
    }

    @Test
    @DisplayName("Когда вызывается метод прямоугольника getWidth, возвращается ширина")
    void whenRectangleGetWidthReturnDiagonal() {
        Assertions.assertEquals(sideA, rectangle.getWidth());
    }

    @Test
    @DisplayName("Когда вызывается метод прямоугольника getLength, возвращается ширина")
    void whenRectangleGetLengthReturnDiagonal() {
        Assertions.assertEquals(sideB, rectangle.getLength());
    }

}