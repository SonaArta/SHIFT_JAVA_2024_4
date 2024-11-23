package ru.shift.controller;

import ru.shift.controller.interfaces.CellListener;
import ru.shift.controller.interfaces.CreateNewGameListener;
import ru.shift.controller.interfaces.TimerListener;
import ru.shift.view.GameImage;
import ru.shift.view.GameType;
import ru.shift.view.MainWindow;

import static ru.shift.view.GameImage.getGameImage;

public class ViewController implements CreateNewGameListener, CellListener, TimerListener {
    MainWindow mainWindow;

    public ViewController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    @Override
    public void onCreateNewGame(GameType gameType) {
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
}
