package ru.shift;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static ru.shift.FigureName.*;

public class HandlerParameter {
    private final List<String> dataFile;
    private final Map<FigureName, Integer> countParameter = new HashMap<>();

    HandlerParameter(List<String> dataFile) {
        this.dataFile = dataFile;
        countParameter.put(CIRCLE, 1);
        countParameter.put(RECTANGLE, 2);
        countParameter.put(TRIANGLE, 3);
    }

    FigureName getNameFigure() {
        FigureName nameFigure = switch (dataFile.get(0).toUpperCase()) {
            case "CIRCLE" -> CIRCLE;
            case "TRIANGLE" -> TRIANGLE;
            case "RECTANGLE" -> RECTANGLE;
            default -> throw new IllegalStateException("Unexpected value: " + dataFile.get(0).toUpperCase());
        };
        return nameFigure;
    }

    List<Double> getParameterFigure() {
        List<Double> listParameter = new LinkedList<>();

        String handleString = dataFile.get(1);
        String[] splitParameters = handleString.trim().split(" +");

        for (String parameter : splitParameters) {
            try {
                Double number = Double.parseDouble(parameter);
                listParameter.add(number);
            } catch (NumberFormatException e) {
                System.out.println(parameter + " не является числом.");
            }
        }
        return listParameter;
    }

    boolean checkingSideTriangle() {
        List<Double> paramList = getParameterFigure();
        boolean comparation = true;
        if (getNameFigure().equals(TRIANGLE)) {
            comparation = (paramList.get(0) + paramList.get(1) > paramList.get(2)) &&
                    (paramList.get(0) + paramList.get(2) > paramList.get(1)) &&
                    (paramList.get(1) + paramList.get(2) > paramList.get(0));
        }

        return comparation;
    }

    boolean checkingComplianceNumberParameters() {
        return countParameter.get(getNameFigure()) == getParameterFigure().size();
    }
}
