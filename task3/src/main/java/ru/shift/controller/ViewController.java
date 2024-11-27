package ru.shift.controller;

import ru.shift.controller.interfaces.CellListener;
import ru.shift.controller.interfaces.CreateNewGameListener;
import ru.shift.controller.interfaces.EndGameListener;
import ru.shift.controller.interfaces.TimerListener;
import ru.shift.model.GameModel;
import ru.shift.view.*;

import static ru.shift.view.GameImage.getGameImage;

public class ViewController implements CreateNewGameListener, CellListener, EndGameListener, TimerListener {
    private final MainWindow mainWindow;
    private final SettingsWindow settingsWindow;
    private final RecordController recordController;
    private final GameModel gameModel;
    private boolean winCheckComplete;

    public ViewController(MainWindow mainWindow, SettingsWindow settingsWindow,
                          RecordController recordController, GameModel gameModel) {
        this.mainWindow = mainWindow;
        this.settingsWindow = settingsWindow;
        this.recordController = recordController;
        this.gameModel = gameModel;
        this.winCheckComplete = false;
    }

    @Override
    public void onCreateNewGame(GameType gameType) {
        winCheckComplete = false;

        mainWindow.createGameField(gameType.getNumberRows(), gameType.getNumberColumns());
        mainWindow.setBombsCount(gameType.getNumberBomb());
        mainWindow.setVisible(true);
    }

    @Override
    public void onCellOpen(int x, int y, int countBombAround) {
        mainWindow.setCellImage(x, y, getGameImage(countBombAround));
    }

    @Override
    public void onBombOpen(int x, int y) {
        mainWindow.setCellImage(x, y, GameImage.BOMB);
    }

    @Override
    public void onToggleFlag(int x, int y, GameImage gameImage, int bombsCount) {
        mainWindow.setCellImage(x, y, gameImage);
        mainWindow.setBombsCount(bombsCount);
    }

    @Override
    public void onTimerUpdate(int seconds) {
        mainWindow.setTimerValue(seconds);
    }

    @Override
    public void onGameEnd() {
        if (gameModel.checkWin()) {
            if (!winCheckComplete) {
                winCheckComplete = true;
                checkNewRecord();
            }
            createWinWindow();
        } else {
            createLoseWindow();
        }
    }

    public void checkNewRecord() {
        RecordsWindow recordsWindow = new RecordsWindow(mainWindow);
        recordsWindow.setNameListener(recordController);
        recordsWindow.setVisible(true);
    }

    private void createWinWindow() {
        WinWindow winWindow = new WinWindow(mainWindow);
        winWindow.setNewGameListener(e -> settingsWindow.setVisible(true));
        winWindow.setExitListener(e -> winWindow.setVisible(false));
        winWindow.setVisible(true);
    }

    private void createLoseWindow() {
        LoseWindow loseWindow = new LoseWindow(mainWindow);
        loseWindow.setNewGameListener(e -> settingsWindow.setVisible(true));
        loseWindow.setExitListener(e -> loseWindow.setVisible(false));
        loseWindow.setVisible(true);
    }

}
