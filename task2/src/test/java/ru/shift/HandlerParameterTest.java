package ru.shift;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.shift.figure.FigureName;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.shift.figure.FigureName.*;

class HandlerParameterTest {
    @ParameterizedTest(name = "��� ������ {0} ������������� {1}")
    @MethodSource("whenDataFileThenFigureProvider")
    @DisplayName("����������� ���� ������")
    void whenDataFileThenFigure(HandlerParameter handlerParameter, FigureName expected) {
        Assertions.assertEquals(expected, handlerParameter.getNameFigure());
    }

    static Stream<Arguments> whenDataFileThenFigureProvider() {
        return Stream.of(
                Arguments.arguments(new HandlerParameter(List.of("CIRCLE", "1.0")), CIRCLE),
                Arguments.arguments(new HandlerParameter(List.of("TRIANGLE", "1.0 2.0 3.0")), TRIANGLE),
                Arguments.arguments(new HandlerParameter(List.of("RECTANGLE", "1.0 2.0")), RECTANGLE)
        );
    }

    @Test
    @DisplayName("�������� ����������� ���������� ��� ������������ ������ ���� ������")
    void whenTypeFigureIsLowerCaseThenException() {
        HandlerParameter handlerParameter = new HandlerParameter(List.of("circle", "1.0"));

        assertThatThrownBy(handlerParameter::getNameFigure)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("�������� ����������� ���������� ��� ��������� ������������ ���� ������")
    void whenTypeFigureIsUnexpectedThenException() {
        HandlerParameter handlerParameter = new HandlerParameter(List.of("LUNTIK", "1.0"));

        assertThatThrownBy(handlerParameter::getNameFigure)
                .isInstanceOf(IllegalStateException.class);
    }

    @ParameterizedTest(name = "��� ������ {0} ������������� {1}")
    @MethodSource("whenDataFileThenParameterFigureProvider")
    @DisplayName("��������� ���������� ������")
    void whenDataFileThenParameterFigure(HandlerParameter handlerParameter, List<Double> expected) {
        Assertions.assertEquals(expected, handlerParameter.getParameterFigure());
    }

    static Stream<Arguments> whenDataFileThenParameterFigureProvider() {
        return Stream.of(
                Arguments.arguments(new HandlerParameter(List.of("CIRCLE", "1.0")), List.of(1.0)),
                Arguments.arguments(new HandlerParameter(List.of("TRIANGLE", "1.0 2.0 3.0")), List.of(1.0, 2.0, 3.0)),
                Arguments.arguments(new HandlerParameter(List.of("RECTANGLE", "1.0 2.0")), List.of(1.0, 2.0))
        );
    }

    @Test
    @DisplayName("�������� ����������� ����������, ���� ��������� ������ �� �������� ������")
    void whenDataFileNotNumberParameterThenException() {
        HandlerParameter handlerParameter = new HandlerParameter(List.of("RECTANGLE", "1.0ssd2.0"));

        assertThatThrownBy(handlerParameter::getParameterFigure)
                .isInstanceOf(NumberFormatException.class);
    }

    @ParameterizedTest(name = "��� ������ {0}")
    @MethodSource("whenNumberParameterComplianceThenTrueProvider")
    @DisplayName("�������� ������������ ����� ���������� ������������ ���������� ���������� ������")
    void whenNumberParameterComplianceThenTrue(HandlerParameter handlerParameter) {
        Assertions.assertTrue(handlerParameter.checkComplianceNumberParameters());
    }

    static Stream<Arguments> whenNumberParameterComplianceThenTrueProvider() {
        return Stream.of(
                Arguments.arguments(new HandlerParameter(List.of("CIRCLE", "1.0"))),
                Arguments.arguments(new HandlerParameter(List.of("TRIANGLE", "1.0 2.0 3.0"))),
                Arguments.arguments(new HandlerParameter(List.of("RECTANGLE", "1.0 2.0")))
        );
    }

    @Test
    @DisplayName("�������� ������������� ���������� ��� ������������� ����� ���������� � ����������� ����������� ���������� ��� ������")
    void whenNumberParameterNotComplianceThenTrueThenException() {
        HandlerParameter handlerParameter = new HandlerParameter(List.of("CIRCLE", "1.0 2.0 3.0"));

        assertThatThrownBy(handlerParameter::checkComplianceNumberParameters)
                .isInstanceOf(IllegalArgumentException.class);
    }
}