package ru.shift.figure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.shift.HandlerParameter;
import ru.shift.figure.typeFigure.Circle;
import ru.shift.figure.typeFigure.Rectangle;
import ru.shift.figure.typeFigure.Triangle;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.shift.figure.FigureFactory.createFigure;
import static ru.shift.figure.FigureName.*;

class FigureFactoryTest {

    @ParameterizedTest(name = "При параметрах {0} создается фигура {1}")
    @MethodSource("whenDataFileThenFigureProvider")
    @DisplayName("Создание фигуры")
    void whenDataFileThenFigure(HandlerParameter handlerParameter, Figure expected) {
        assertThat(expected).isEqualToComparingFieldByFieldRecursively(createFigure(handlerParameter));
    }

    static Stream<Arguments> whenDataFileThenFigureProvider() {
        return Stream.of(
                Arguments.arguments(new HandlerParameter(List.of("CIRCLE", "1.0")), new Circle(CIRCLE, List.of(1.0))),
                Arguments.arguments(new HandlerParameter(List.of("TRIANGLE", "1.0 2.0 2.0")), new Triangle(TRIANGLE, List.of(1.0, 2.0, 2.0))),
                Arguments.arguments(new HandlerParameter(List.of("RECTANGLE", "1.0 2.0")), new Rectangle(RECTANGLE, List.of(1.0, 2.0)))
        );
    }

}