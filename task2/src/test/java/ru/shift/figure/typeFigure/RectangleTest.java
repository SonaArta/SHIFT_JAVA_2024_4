package ru.shift.figure.typeFigure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static ru.shift.figure.FigureName.RECTANGLE;

class RectangleTest {
    @ParameterizedTest(name = "�������� ����������� {0} ����� {1}")
    @MethodSource("whenRectangleThenPerimeterProvider")
    @DisplayName("������ ��������� �����������")
    void whenRectangleThenPerimeter(Rectangle rectangle, double expected) {
        Assertions.assertEquals(expected, rectangle.calculatePerimeterFigure());
    }

    static Stream<Arguments> whenRectangleThenPerimeterProvider() {
        return Stream.of(
                Arguments.arguments(new Rectangle(RECTANGLE, List.of(1.0, 1.0)), 4.0),
                Arguments.arguments(new Rectangle(RECTANGLE, List.of(1.0, 2.0)), 6.0)
        );
    }

    @ParameterizedTest(name = "������� ����������� {0} ����� {1}")
    @MethodSource("whenRectangleThenAreaProvider")
    @DisplayName("������ ������� �����������")
    void whenRectangleThenArea(Rectangle rectangle, double expected) {
        Assertions.assertEquals(expected, rectangle.calculateAreaFigure());
    }

    static Stream<Arguments> whenRectangleThenAreaProvider() {
        return Stream.of(
                Arguments.arguments(new Rectangle(RECTANGLE, List.of(1.0, 1.0)), 1.0),
                Arguments.arguments(new Rectangle(RECTANGLE, List.of(1.0, 2.0)), 2.0)
        );
    }

    @ParameterizedTest(name = "��������� ����������� {0} ����� {1}")
    @MethodSource("whenRectangleThenDiagonalProvider")
    @DisplayName("����������� ������ �����������")
    void whenRectangleThenDiagonal(Rectangle rectangle, String expected) {
        Assertions.assertEquals(expected, String.format("%.2f", rectangle.getDiagonal()));
    }

    static Stream<Arguments> whenRectangleThenDiagonalProvider() {
        return Stream.of(
                Arguments.arguments(new Rectangle(RECTANGLE, List.of(1.0, 1.0)), "1,41"),
                Arguments.arguments(new Rectangle(RECTANGLE, List.of(1.0, 2.0)), "2,24")
        );
    }

    @ParameterizedTest(name = "������ ����������� {0} ����� {1}")
    @MethodSource("whenRectangleThenWidthProvider")
    @DisplayName("����������� ������ �����������")
    void whenRectangleThenWidth(Rectangle rectangle, double expected) {
        Assertions.assertEquals(expected, rectangle.getWidth());
    }

    static Stream<Arguments> whenRectangleThenWidthProvider() {
        return Stream.of(
                Arguments.arguments(new Rectangle(RECTANGLE, List.of(1.0, 1.0)), 1.0),
                Arguments.arguments(new Rectangle(RECTANGLE, List.of(1.0, 2.0)), 1.0)
        );
    }

    @ParameterizedTest(name = "����� ����������� {0} ����� {1}")
    @MethodSource("whenRectangleThenLengthProvider")
    @DisplayName("����������� ����� �����������")
    void whenRectangleThenLength(Rectangle rectangle, double expected) {
        Assertions.assertEquals(expected, rectangle.getLength());
    }

    static Stream<Arguments> whenRectangleThenLengthProvider() {
        return Stream.of(
                Arguments.arguments(new Rectangle(RECTANGLE, List.of(1.0, 1.0)), 1.0),
                Arguments.arguments(new Rectangle(RECTANGLE, List.of(1.0, 2.0)), 2.0)
        );
    }

}