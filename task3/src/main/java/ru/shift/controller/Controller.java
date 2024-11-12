package ru.shift.controller;

import ru.shift.model.GameModel;
import ru.shift.model.TimeCounter.TimeCounter;
import ru.shift.view.*;

import static ru.shift.view.GameImage.getGameImage;
import static ru.shift.view.GameType.NOVICE;

public class Controller implements CellEventListener, GameTypeListener {
    private final MainWindow mainWindow;
    private final TimeCounter timer;
    RecordController recordController;

    private GameType gameType;
    private GameModel gameField;
    private boolean firstClick;

    private boolean isCheckNewRecord;

    public Controller(MainWindow mainWindow, RecordController recordController) {
        this.mainWindow = mainWindow;
        this.timer = new TimeCounter();
        this.recordController = recordController;


        this.gameType = NOVICE;
        this.firstClick = false;
        this.isCheckNewRecord = false;
        createNewGame(gameType);
    }

    @Override
    public void onMouseClick(int x, int y, ButtonType buttonType) {
        switch (buttonType) {
            case LEFT_BUTTON:

                if (!firstClick) {
                    createFirstClick(x, y);
                    timer.startTimer(e -> mainWindow.setTimerValue(timer.getCurrentGameTime()));
                }

                gameField.openCell(x, y);
                updateOpenCell();
                break;

            case RIGHT_BUTTON:
                if (!gameField.getCell(x, y).isOpen()) {
                    gameField.toggleFlag(x, y);

                    GameImage gameImage = gameField.isFlag(x, y) ? GameImage.MARKED : GameImage.CLOSED;
                    mainWindow.setCellImage(x, y, gameImage);
                    mainWindow.setBombsCount(gameField.getNumberBomb() - gameField.getNumberFlag());
                }
                break;

            case MIDDLE_BUTTON:
                gameField.openCellAround(x, y);
                updateOpenCell();
                break;
            default:
        }

        if (gameField.checkWin()) {
            timer.stopTimer();
            mainWindow.setTimerValue(0);
            if (!isCheckNewRecord) {
                checkNewRecord();
            }
            createWinWindow();
        } else {
            if (gameField.checkGameIsOver()) {
                timer.stopTimer();
                mainWindow.setTimerValue(0);
                createLoseWindow();
            }
        }
    }

    @Override
    public void onGameTypeChanged(GameType gameType) {
        this.gameType = gameType;
        createNewGame(gameType);
    }

    public void createNewGame(GameType gameType) {
        if (firstClick) {
            timer.stopTimer();
            firstClick = false;
        }
        isCheckNewRecord = false;

        int numberBomb = gameType.getNumberBomb();
        int numberRows = gameType.getNumberRows();
        int numberColumns = gameType.getNumberColumns();

        this.gameField = new GameModel(numberBomb, numberRows, numberColumns);
        mainWindow.createGameField(numberRows, numberColumns);
        mainWindow.setBombsCount(numberBomb);
        mainWindow.setVisible(true);
    }

    public void createFirstClick(int x, int y) {
        firstClick = true;
        gameField.openFirstCell(x, y);
        gameField.placeBomb();
        gameField.printField();
    }

    public void updateOpenCell() {
        for (int currX = 0; currX < gameType.getNumberRows(); currX++) {
            for (int currY = 0; currY < gameType.getNumberColumns(); currY++) {
                if (gameField.getCell(currX, currY).isOpen()) {
                    if (gameField.getCell(currX, currY).isBomb()) {
                        mainWindow.setCellImage(currX, currY, GameImage.BOMB);
                    } else {
                        int numberCellImage = gameField.getCell(currX, currY).getCountBombAround();
                        mainWindow.setCellImage(currX, currY, getGameImage(numberCellImage));
                    }
                }
            }
        }
    }

    public void checkNewRecord() {
        isCheckNewRecord = true;

        recordController.setCurrentGameType(gameType);
        recordController.setCurrentTimeRecord(timer.getCurrentGameTime());

        RecordsWindow recordsWindow = new RecordsWindow(mainWindow);
        recordsWindow.setNameListener(recordController);
        recordsWindow.setVisible(true);

    }

    private void createWinWindow() {
        WinWindow winWindow = new WinWindow(mainWindow);
        winWindow.setNewGameListener(e -> createNewGame(gameType));
        winWindow.setExitListener(e -> winWindow.setVisible(false));
        winWindow.setVisible(true);
    }

    private void createLoseWindow() {
        LoseWindow loseWindow = new LoseWindow(mainWindow);
        loseWindow.setNewGameListener(e -> createNewGame(gameType));
        loseWindow.setExitListener(e -> loseWindow.setVisible(false));
        loseWindow.setVisible(true);
    }
}
