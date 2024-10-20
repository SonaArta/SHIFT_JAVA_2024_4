package ru.shift;

import ru.shift.figure.FigureName;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static ru.shift.figure.FigureName.*;

public class HandlerParameter {
    private final List<String> dataFile;
    private final Map<FigureName, Integer> countParameter = new HashMap<>();

    public HandlerParameter(List<String> dataFile) {
        this.dataFile = dataFile;
        countParameter.put(CIRCLE, 1);
        countParameter.put(RECTANGLE, 2);
        countParameter.put(TRIANGLE, 3);
    }


    public FigureName getNameFigure() {
        String nameFromFile = dataFile.get(0);
        if (nameFromFile.equals(nameFromFile.toUpperCase())) {
            return switch (nameFromFile) {
                case "CIRCLE" -> CIRCLE;
                case "TRIANGLE" -> TRIANGLE;
                case "RECTANGLE" -> RECTANGLE;
                default -> throw new IllegalStateException("Неожидаемое значение: " + nameFromFile);
            };
        } else {
            throw new IllegalArgumentException("Некорректная запись типа фигуры");
        }
    }

    public List<Double> getParameterFigure() {
        List<Double> listParameter = new LinkedList<>();

        String handleString = dataFile.get(1);
        String[] splitParameters = handleString.trim().split(" +");

        for (String parameter : splitParameters) {
            try {
                Double number = Double.parseDouble(parameter);
                listParameter.add(number);
            } catch (NumberFormatException e) {
                throw new NumberFormatException(parameter + " не является числом.");
            }
        }
        return listParameter;
    }

    public boolean checkComplianceNumberParameters() {
        if (countParameter.get(getNameFigure()) == getParameterFigure().size()) {
            return true;
        } else {
            throw new IllegalArgumentException("Некорретное количество параметров для создания " + getNameFigure());
        }
    }

}
