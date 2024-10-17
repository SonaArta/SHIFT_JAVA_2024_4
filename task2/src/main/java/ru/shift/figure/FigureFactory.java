package ru.shift.figure;

import ru.shift.HandlerParameter;
import ru.shift.figure.typeFigure.Circle;
import ru.shift.figure.typeFigure.Rectangle;
import ru.shift.figure.typeFigure.Triangle;

import java.util.List;

public class FigureFactory {
    private FigureFactory() {
    }

    public static Figure createFigure(HandlerParameter handlerParameter) {
        FigureName figureName = handlerParameter.getNameFigure();
        List<Double> listParameter = handlerParameter.getParameterFigure();

        return switch (figureName) {
            case CIRCLE -> new Circle(figureName, listParameter);
            case TRIANGLE -> new Triangle(figureName, listParameter);
            case RECTANGLE -> new Rectangle(figureName, listParameter);
        };
    }
}
