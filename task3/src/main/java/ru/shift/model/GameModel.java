package ru.shift.model;

import org.apache.commons.math3.random.MersenneTwister;
import ru.shift.controller.Notifiable;
import ru.shift.controller.interfaces.CellListener;
import ru.shift.controller.interfaces.CreateNewGameListener;
import ru.shift.controller.interfaces.EndGameListener;
import ru.shift.controller.interfaces.StartGameListener;
import ru.shift.model.consumer.CoordinatesConsumer;
import ru.shift.view.GameImage;
import ru.shift.view.GameType;

import java.util.List;

public class GameModel {
    private GameType gameType;
    private int numberClosedCell;
    private int numberFlag;
    private Cell[][] gameField;
    private boolean gameOver;
    private boolean isStart;

    private final Notifiable<CreateNewGameListener> createNewGameListener = new Notifiable<>();
    private final Notifiable<StartGameListener> startGameListener = new Notifiable<>();
    private final Notifiable<EndGameListener> endGameListeners = new Notifiable<>();
    private final Notifiable<CellListener> cellListener = new Notifiable<>();

    public GameModel(GameType gameType) {
        createNewGame(gameType);
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setCreateNewGameListener(CreateNewGameListener createNewGameListener) {
        this.createNewGameListener.add(createNewGameListener);
    }

    public void setStartGameListener(StartGameListener startGameListener) {
        this.startGameListener.add(startGameListener);
    }

    public void setEndGameListener(List<EndGameListener> endGameListeners) {
        this.endGameListeners.add(endGameListeners);
    }

    public void setCellListener(CellListener cellListener) {
        this.cellListener.add(cellListener);
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
            applyAround(x, y, (currX, currY) -> {
                if (!gameField[currX][currY].isOpen() && !gameField[currX][currY].isFlag()) {
                    gameField[currX][currY].setOpen();
                    notifyOpenCell(currX, currY);
                    if (gameField[currX][currY].isBomb()) {
                        gameOver = true;
                        openAllBomb();
                    }
                }
            });

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

    private void notifyNewGame(GameType gameType) {
        createNewGameListener.notify(l -> l.onCreateNewGame(gameType));
    }

    private void notifyStartGame() {
        startGameListener.notify(StartGameListener::onGameStart);
    }

    private void notifyEndGame() {
        endGameListeners.notify(EndGameListener::onGameEnd);
    }

    private void notifyToggleFlag(int x, int y) {
        GameImage gameImage = gameField[x][y].isFlag() ? GameImage.MARKED : GameImage.CLOSED;
        cellListener.notify(l -> l.onToggleFlag(x, y, gameImage, gameType.getNumberBomb() - numberFlag));
    }

    private void notifyOpenCell(int x, int y) {
        if (gameField[x][y].isBomb()) {
            cellListener.notify(l -> l.onBombOpen(x, y));
        } else {
            if (!gameField[x][y].isFlag()) {
                cellListener.notify(l -> l.onCellOpen(x, y, gameField[x][y].getCountBombAround()));
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

    private void applyAround(int x, int y, CoordinatesConsumer consumer) {
        for (int countX = x - 1; countX <= x + 1; countX++) {
            for (int countY = y - 1; countY <= y + 1; countY++) {
                if (isNotEqualsCoordinates(x, countX, y, countY) && isInField(countX, countY)) {
                    consumer.apply(countX, countY);
                }
            }
        }
    }

    private int calculateFlagAroundCell(int x, int y) {
        final int[] countFlag = {0};
        applyAround(x, y, (currX, currY) -> {
            if (gameField[currX][currY].isFlag()) {
                countFlag[0]++;
            }
        });
        return countFlag[0];
    }

    private void increaseCountBombCellAroundBomb(int x, int y) {
        applyAround(x, y, (currX, currY) -> {
            if (!gameField[currX][currY].isBomb()) {
                gameField[currX][currY].increaseCountBomb();
            }
        });
    }

    private void openEmptyCellAround(int x, int y) {
        applyAround(x, y, (currX, currY) -> {
            if (!gameField[currX][currY].isBomb()) {
                openCell(currX, currY);
            }
        });
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
