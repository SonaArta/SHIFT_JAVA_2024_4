package ru.shift.controller;

import ru.shift.controller.interfaces.CellEventListener;
import ru.shift.controller.interfaces.EndGameListener;
import ru.shift.controller.interfaces.GameTypeListener;
import ru.shift.model.GameModel;
import ru.shift.view.*;

import static ru.shift.view.GameType.NOVICE;

public class Controller implements CellEventListener, GameTypeListener, EndGameListener {
    private final MainWindow mainWindow;
    RecordController recordController;

    private GameType gameType;
    private final GameModel gameField;
    private boolean isCheckNewRecord;

    public Controller(MainWindow mainWindow, GameModel gameModel, RecordController recordController) {
        this.mainWindow = mainWindow;
        this.gameField = gameModel;
        this.recordController = recordController;

        this.gameType = NOVICE;
        this.isCheckNewRecord = false;
    }

    @Override
    public void onMouseClick(int x, int y, ButtonType buttonType) {
        switch (buttonType) {
            case LEFT_BUTTON -> gameField.openCell(x, y);
            case RIGHT_BUTTON -> gameField.toggleFlag(x, y);
            case MIDDLE_BUTTON -> gameField.openCellAround(x, y);
        }
    }

    @Override
    public void onGameTypeChanged(GameType gameType) {
        this.gameType = gameType;
        this.isCheckNewRecord = false;
        this.gameField.createNewGame(gameType);
    }

    @Override
    public void onGameEnd() {
        if (gameField.checkWin()) {
            if (!isCheckNewRecord) {
                checkNewRecord();
            }
            createWinWindow();
        } else {
            if (gameField.checkGameIsOver()) {
                createLoseWindow();
            }
        }
    }

    public void checkNewRecord() {
        isCheckNewRecord = true;

        RecordsWindow recordsWindow = new RecordsWindow(mainWindow);
        recordsWindow.setNameListener(recordController);
        recordsWindow.setVisible(true);
    }

    private void createWinWindow() {
        WinWindow winWindow = new WinWindow(mainWindow);
        winWindow.setNewGameListener(e -> onGameTypeChanged(gameType));
        winWindow.setExitListener(e -> winWindow.setVisible(false));
        winWindow.setVisible(true);
    }

    private void createLoseWindow() {
        LoseWindow loseWindow = new LoseWindow(mainWindow);
        loseWindow.setNewGameListener(e -> onGameTypeChanged(gameType));
        loseWindow.setExitListener(e -> loseWindow.setVisible(false));
        loseWindow.setVisible(true);
    }
}
