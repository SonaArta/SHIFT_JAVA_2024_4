package ru.shift.controller;

import ru.shift.controller.interfaces.CellEventListener;
import ru.shift.controller.interfaces.EndGameListener;
import ru.shift.controller.interfaces.GameTypeListener;
import ru.shift.model.GameModel;
import ru.shift.view.ButtonType;
import ru.shift.view.GameType;

import static ru.shift.view.GameType.NOVICE;

public class Controller implements CellEventListener, GameTypeListener, EndGameListener {
    public GameType gameType;
    private final GameModel gameField;
    boolean isGameOver;

    public Controller(GameModel gameModel) {
        this.gameField = gameModel;
        this.gameType = NOVICE;
        this.isGameOver = false;
    }

    @Override
    public void onMouseClick(int x, int y, ButtonType buttonType) {
        if (!isGameOver) {
            switch (buttonType) {
                case LEFT_BUTTON -> gameField.openCell(x, y);
                case RIGHT_BUTTON -> gameField.toggleFlag(x, y);
                case MIDDLE_BUTTON -> gameField.openCellAround(x, y);
            }
        }
    }

    @Override
    public void onGameTypeChanged(GameType gameType) {
        this.gameType = gameType;
        this.isGameOver = false;
        this.gameField.createNewGame(gameType);
    }

    @Override
    public void onGameEnd() {
        isGameOver = true;
    }
}
