package ru.shift.figure.typeFigure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static java.lang.Math.PI;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.shift.figure.FigureName.TRIANGLE;
import static ru.shift.figure.typeFigure.Triangle.checkingSideTriangle;

class TriangleTest {
    @ParameterizedTest(name = "��� �������� {0} �������� �� ������������ ���������� {1}")
    @MethodSource("whenParametersCheckInequalityProvider")
    @DisplayName("�������� �� ������������ ������ ������������")
    void whenParametersCheckInequality(List<Double> paramList, boolean expected) {
        Assertions.assertEquals(expected, checkingSideTriangle(paramList));
    }

    static Stream<Arguments> whenParametersCheckInequalityProvider() {
        return Stream.of(
                Arguments.arguments(List.of(1.0, 2.0, 3.0), false),
                Arguments.arguments(List.of(1.0, 1.0, 1.0), true),
                Arguments.arguments(List.of(1.0, 2.0, 2.0), true),
                Arguments.arguments(List.of(2.0, 4.0, 5.0), true)
        );
    }

    @Test
    @DisplayName("�������� ����������� ���������� ��� ������������ ������� ������������� ������������")
    void whenParameterNotMatchesException() {
        assertThatThrownBy(() -> new Triangle(TRIANGLE, List.of(1.0, 2.0, 3.0)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest(name = "�������� ������������ {0} ����� {1}")
    @MethodSource("whenTriangleThenPerimeterProvider")
    @DisplayName("������ ��������� ������������")
    void whenTriangleThenPerimeter(Triangle triangle, double expected) {
        Assertions.assertEquals(expected, triangle.calculatePerimeterFigure());
    }

    static Stream<Arguments> whenTriangleThenPerimeterProvider() {
        return Stream.of(
                Arguments.arguments(new Triangle(TRIANGLE, List.of(1.0, 1.0, 1.0)), 3.0),
                Arguments.arguments(new Triangle(TRIANGLE, List.of(1.0, 2.0, 2.0)), 5.0),
                Arguments.arguments(new Triangle(TRIANGLE, List.of(2.0, 4.0, 5.0)), 11.0)
        );
    }

    @ParameterizedTest(name = "������� ������������ {0} ����� {1}")
    @MethodSource("whenTriangleThenAreaProvider")
    @DisplayName("������ ������� ������������")
    void whenTriangleThenArea(Triangle triangle, double expected) {
        Assertions.assertEquals(expected, triangle.calculateAreaFigure());
    }

    static Stream<Arguments> whenTriangleThenAreaProvider() {
        return Stream.of(
                Arguments.arguments(new Triangle(TRIANGLE, List.of(1.0, 1.0, 1.0)), 0.4330127018922193),
                Arguments.arguments(new Triangle(TRIANGLE, List.of(1.0, 2.0, 2.0)), 0.9682458365518543),
                Arguments.arguments(new Triangle(TRIANGLE, List.of(2.0, 4.0, 5.0)), 3.799671038392666)
        );
    }

    @ParameterizedTest(name = "�������������� ������� � ���� ������������ {0} ����� {1}")
    @MethodSource("whenTriangleThenAngleSideAProvider")
    @DisplayName("������ ��������������� ������� � ����")
    void whenTriangleThenAngleSideA(Triangle triangle, String expected) {
        Assertions.assertEquals(expected, String.format("%.2f", triangle.getAngleSideA() * 180 / PI));
    }

    static Stream<Arguments> whenTriangleThenAngleSideAProvider() {
        return Stream.of(
                Arguments.arguments(new Triangle(TRIANGLE, List.of(1.0, 1.0, 1.0)), "60,00"),
                Arguments.arguments(new Triangle(TRIANGLE, List.of(1.0, 2.0, 2.0)), "28,96"),
                Arguments.arguments(new Triangle(TRIANGLE, List.of(2.0, 4.0, 5.0)), "22,33")
        );
    }

    @ParameterizedTest(name = "�������������� ������� B ���� ������������ {0} ����� {1}")
    @MethodSource("whenTriangleThenAngleSideBProvider")
    @DisplayName("������ ��������������� ������� B ����")
    void whenTriangleThenAngleSideB(Triangle triangle, String expected) {
        Assertions.assertEquals(expected, String.format("%.2f", triangle.getAngleSideBOrSideC(triangle.getAngleSideA(), triangle.getSideB()) * 180 / PI));
    }

    static Stream<Arguments> whenTriangleThenAngleSideBProvider() {
        return Stream.of(
                Arguments.arguments(new Triangle(TRIANGLE, List.of(1.0, 1.0, 1.0)), "60,00"),
                Arguments.arguments(new Triangle(TRIANGLE, List.of(1.0, 2.0, 2.0)), "75,52"),
                Arguments.arguments(new Triangle(TRIANGLE, List.of(2.0, 4.0, 5.0)), "49,46")
        );
    }
}