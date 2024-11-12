package ru.shift.model;

import org.apache.commons.math3.random.MersenneTwister;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private final int numberRow;
    private final int numberColumn;
    private final int numberBomb;
    private int numberClosedCell;
    private int numberFlag;
    private Cell[][] gameField;
    private boolean gameOver;


    public GameModel(int numberBomb, int numberRow, int numberColumn) {
        this.numberRow = numberRow;
        this.numberColumn = numberColumn;
        this.numberBomb = numberBomb;
        this.numberClosedCell = numberRow * numberColumn - numberBomb - 1;
        this.numberFlag = 0;
        this.gameOver = false;
        initializeField();
    }

    public void printField() {
        for (int currX = 0; currX < numberRow; currX++) {
            for (int currY = 0; currY < numberColumn; currY++) {
                if (gameField[currX][currY].isBomb()) {
                    System.out.print("B");
                } else {
                    System.out.print(gameField[currX][currY].getCountBombAround());
                }
            }
            System.out.println();
        }
    }

    public int getNumberBomb() {
        return numberBomb;
    }

    public int getNumberFlag() {
        return numberFlag;
    }

    public Cell getCell(int x, int y) {
        return gameField[x][y];
    }

    public boolean isFlag(int x, int y) {
        return gameField[x][y].isFlag();
    }

    private void initializeField() {
        gameField = new Cell[numberRow][numberColumn];

        for (int row = 0; row < numberRow; row++) {
            for (int column = 0; column < numberColumn; column++) {
                gameField[row][column] = new Cell();
            }
        }
    }

    public void placeBomb() {
        int counterBomb = 0;
        MersenneTwister random = new MersenneTwister();

        while (counterBomb < numberBomb) {
            int x = random.nextInt(numberRow);
            int y = random.nextInt(numberColumn);
            if (!gameField[x][y].isBomb() && !gameField[x][y].isFlag() && !gameField[x][y].isOpen()) {
                gameField[x][y].setBomb();
                counterBomb++;
                increaseCountBombCellAroundBomb(x, y);
            }
        }
    }

    public void toggleFlag(int x, int y) {
        if (!gameOver && !gameField[x][y].isOpen()) {
            numberFlag += gameField[x][y].isFlag() ? -1 : +1;
            gameField[x][y].toggleFlag();
        }
    }

    private boolean isInField(int x, int y) {
        return (x >= 0 && x < numberRow) && (y >= 0 && y < numberColumn);
    }

    private boolean isNotEqualsCoordinates(int x, int currentX, int y, int currentY) {
        return x != currentX || y != currentY;
    }

    private List<Coordinates> getCoordinatesAround(int x, int y) {
        List<Coordinates> coordinatesList = new ArrayList<>();
        for (int countX = x - 1; countX <= x + 1; countX++) {
            for (int countY = y - 1; countY <= y + 1; countY++) {
                if (isNotEqualsCoordinates(x, countX, y, countY) && isInField(countX, countY)) {
                    coordinatesList.add(new Coordinates(countX, countY));
                }
            }
        }
        return coordinatesList;
    }

    private int calculateFlagAroundCell(int x, int y) {
        int countFlag = 0;
        for (Coordinates coordinates : getCoordinatesAround(x, y)) {
            countFlag += gameField[coordinates.getX()][coordinates.getY()].isFlag() ? 1 : 0;
        }
        return countFlag;
    }

    private void increaseCountBombCellAroundBomb(int x, int y) {
        for (Coordinates coordinates : getCoordinatesAround(x, y)) {
            int currX = coordinates.getX();
            int currY = coordinates.getY();
            if (!gameField[currX][currY].isBomb()) {
                gameField[currX][currY].increaseCountBomb();
            }
        }
    }

    public void openFirstCell(int x, int y) {
        gameField[x][y].setOpen();
    }

    private void openEmptyCellAround(int x, int y) {
        for (Coordinates coordinates : getCoordinatesAround(x, y)) {
            int currX = coordinates.getX();
            int currY = coordinates.getY();

            if (!gameField[currX][currY].isBomb()) {
                openCell(currX, currY);
            }
        }
    }

    public void openCellAround(int x, int y) {
        if (!gameOver && gameField[x][y].getCountBombAround() == calculateFlagAroundCell(x, y) &&  gameField[x][y].isOpen()) {
            for (Coordinates coordinates : getCoordinatesAround(x, y)) {
                int currX = coordinates.getX();
                int currY = coordinates.getY();

                if (!gameField[currX][currY].isOpen() && !gameField[currX][currY].isFlag()) {
                    gameField[currX][currY].setOpen();
                    if (gameField[currX][currY].isBomb()) {
                        gameOver = true;
                        openAllBomb();
                    }
                }
            }
        }
    }

    public void openCell(int x, int y) {
        if (!gameOver && !gameField[x][y].isOpen() && !gameField[x][y].isFlag()) {
            gameField[x][y].setOpen();
            if (gameField[x][y].isBomb()) {
                gameOver = true;
                openAllBomb();
            } else {
                if (gameField[x][y].getCountBombAround() == 0) {
                    openEmptyCellAround(x, y);
                }
                numberClosedCell--;
            }
        }
    }

    public void openAllBomb() {
        for (int row = 0; row < numberRow; row++) {
            for (int column = 0; column < numberColumn; column++) {
                if (gameField[row][column].isBomb()) {
                    gameField[row][column].setOpen();
                }
            }
        }
    }

    public boolean checkWin() {
        int correctFlag = 0;
        for (int currX = 0; currX < numberRow; currX++) {
            for (int currY = 0; currY < numberColumn; currY++) {
                if (gameField[currX][currY].isFlag() && gameField[currX][currY].isBomb()) {
                    correctFlag++;
                }
            }
        }
        return !gameOver && numberClosedCell == 0 && correctFlag == numberBomb;
    }

    public boolean checkGameIsOver() {
        return this.gameOver;
    }

}
