package ru.shift.model;

import org.apache.commons.math3.random.MersenneTwister;
import ru.shift.controller.interfaces.CellListener;
import ru.shift.controller.interfaces.CreateNewGameListener;
import ru.shift.controller.interfaces.EndGameListener;
import ru.shift.controller.interfaces.StartGameListener;
import ru.shift.view.GameImage;
import ru.shift.view.GameType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameModel {
    private GameType gameType;
    private int numberClosedCell;
    private int numberFlag;
    private Cell[][] gameField;
    private boolean gameOver;
    private boolean isStart;

    private CreateNewGameListener createNewGameListener;
    private StartGameListener startGameListener;
    private List<EndGameListener> endGameListeners = new LinkedList<>();
    private CellListener cellListener;

    public GameModel(GameType gameType, CreateNewGameListener createNewGameListener) {
        setCreateNewGameListener(createNewGameListener);
        createNewGame(gameType);
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setCreateNewGameListener(CreateNewGameListener createNewGameListener) {
        this.createNewGameListener = createNewGameListener;
    }

    public void setStartGameListener(StartGameListener startGameListener) {
        this.startGameListener = startGameListener;
    }

    public void setEndGameListener(List<EndGameListener> endGameListeners) {
        this.endGameListeners = endGameListeners;
    }

    public void setCellListener(CellListener cellListener) {
        this.cellListener = cellListener;
    }

    public void printField() {
        for (int currX = 0; currX < gameType.getNumberRows(); currX++) {
            for (int currY = 0; currY < gameType.getNumberColumns(); currY++) {
                if (gameField[currX][currY].isBomb()) {
                    System.out.print("B");
                } else {
                    System.out.print(gameField[currX][currY].getCountBombAround());
                }
            }
            System.out.println();
        }
    }

    public void createNewGame(GameType gameType) {
        this.gameType = gameType;
        this.numberClosedCell = gameType.getNumberRows() * gameType.getNumberColumns() - gameType.getNumberBomb() - 1;
        this.numberFlag = 0;
        this.gameOver = false;
        this.isStart = false;
        initializeField();
        notifyNewGame(gameType);
    }

    public void toggleFlag(int x, int y) {
        if (!gameOver && !gameField[x][y].isOpen()) {
            numberFlag += gameField[x][y].isFlag() ? -1 : +1;
            gameField[x][y].toggleFlag();
            notifyToggleFlag(x, y);
            if (checkWin()) {
                notifyEndGame();
            }
        }
    }

    public void openCellAround(int x, int y) {
        if (!gameOver && gameField[x][y].getCountBombAround() == calculateFlagAroundCell(x, y) && gameField[x][y].isOpen()) {
            for (Coordinates coordinates : getCoordinatesAround(x, y)) {
                int currX = coordinates.getX();
                int currY = coordinates.getY();

                if (!gameField[currX][currY].isOpen() && !gameField[currX][currY].isFlag()) {
                    gameField[currX][currY].setOpen();
                    notifyOpenCell(currX, currY);
                    if (gameField[currX][currY].isBomb()) {
                        gameOver = true;
                        openAllBomb();
                    }
                }
            }
            if (checkWin() || gameOver) {
                notifyEndGame();
            }
        }
    }

    public void openFirstCell(int x, int y) {
        isStart = true;
        if (!gameField[x][y].isFlag()) {
            gameField[x][y].setOpen();
        }
        placeBomb();
        printField();
        notifyOpenCell(x, y);
        notifyStartGame();
    }

    public void openCell(int x, int y) {
        if (!isStart) {
            openFirstCell(x, y);
        } else {
            if (!gameOver && !gameField[x][y].isOpen() && !gameField[x][y].isFlag()) {
                gameField[x][y].setOpen();
                if (gameField[x][y].isBomb()) {
                    gameOver = true;
                    openAllBomb();
                    notifyEndGame();
                } else {
                    if (gameField[x][y].getCountBombAround() == 0) {
                        openEmptyCellAround(x, y);
                    }
                    numberClosedCell--;
                    notifyOpenCell(x, y);
                }

            }
        }
        if (checkWin()) {
            notifyEndGame();
        }
    }

    public boolean checkWin() {
        int correctFlag = 0;
        for (int currX = 0; currX < gameType.getNumberRows(); currX++) {
            for (int currY = 0; currY < gameType.getNumberColumns(); currY++) {
                if (gameField[currX][currY].isFlag() && gameField[currX][currY].isBomb()) {
                    correctFlag++;
                }
            }
        }
        return (!gameOver && numberClosedCell == 0) | correctFlag == gameType.getNumberBomb();
    }

    public boolean checkGameIsOver() {
        return this.gameOver;
    }

    private void notifyNewGame(GameType gameType) {
        createNewGameListener.onCreateNewGame(gameType);
    }

    private void notifyStartGame() {
        startGameListener.onGameStart();
    }

    private void notifyEndGame() {
        for (EndGameListener endGameListener : endGameListeners) {
            endGameListener.onGameEnd();
        }
    }

    private void notifyToggleFlag(int x, int y) {
        GameImage gameImage = gameField[x][y].isFlag() ? GameImage.MARKED : GameImage.CLOSED;
        cellListener.onToggleFlag(x, y, gameImage, gameType.getNumberBomb() - numberFlag);
    }

    private void notifyOpenCell(int x, int y) {
        if (gameField[x][y].isBomb()) {
            cellListener.onBombOpen(x, y);
        } else {
            if (!gameField[x][y].isFlag()) {
                cellListener.onCellOpen(x, y, gameField[x][y].getCountBombAround());
            }
        }
    }

    private void initializeField() {
        int numberRow = gameType.getNumberRows();
        int numberColumn = gameType.getNumberColumns();
        gameField = new Cell[numberRow][numberColumn];

        for (int row = 0; row < numberRow; row++) {
            for (int column = 0; column < numberColumn; column++) {
                gameField[row][column] = new Cell();
            }
        }
    }

    private void placeBomb() {
        int counterBomb = 0;
        MersenneTwister random = new MersenneTwister();

        while (counterBomb < gameType.getNumberBomb()) {
            int x = random.nextInt(gameType.getNumberRows());
            int y = random.nextInt(gameType.getNumberColumns());
            if (!gameField[x][y].isBomb() && !gameField[x][y].isFlag() && !gameField[x][y].isOpen()) {
                gameField[x][y].setBomb();
                counterBomb++;
                increaseCountBombCellAroundBomb(x, y);
            }
        }
    }

    private boolean isInField(int x, int y) {
        return (x >= 0 && x < gameType.getNumberRows()) && (y >= 0 && y < gameType.getNumberColumns());
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

    private void openEmptyCellAround(int x, int y) {
        for (Coordinates coordinates : getCoordinatesAround(x, y)) {
            int currX = coordinates.getX();
            int currY = coordinates.getY();

            if (!gameField[currX][currY].isBomb()) {
                openCell(currX, currY);
            }
        }
    }

    private void openAllBomb() {
        for (int row = 0; row < gameType.getNumberRows(); row++) {
            for (int column = 0; column < gameType.getNumberColumns(); column++) {
                if (gameField[row][column].isBomb()) {
                    gameField[row][column].setOpen();
                    notifyOpenCell(row, column);
                }
            }
        }
    }
}
