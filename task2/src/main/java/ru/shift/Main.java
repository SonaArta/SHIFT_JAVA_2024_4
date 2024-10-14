package ru.shift;

import java.util.List;

import static ru.shift.ReaderParameterUtils.*;
import static ru.shift.OutputResultUtils.*;

public class Main {
    public static void main(String[] args) {
        HandlerParameter handlerParameter = loopingGettingParameters();

        FigureName figureName = handlerParameter.getNameFigure();
        List<Double> listParameter = handlerParameter.getParameterFigure();
        Figure figure = switch (figureName) {
            case CIRCLE -> new Circle(figureName, listParameter);
            case TRIANGLE -> new Triangle(figureName, listParameter);
            case RECTANGLE -> new Rectangle(figureName, listParameter);
        };

        writeData(figure);
    }
}