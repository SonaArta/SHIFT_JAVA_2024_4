package ru.shift.model;

import java.io.Serializable;

public class Record implements Serializable {
    private final String playerName;
    private int gameTime;

    public Record(String playerName, int gameTime) {
        this.playerName = playerName;
        this.gameTime = gameTime;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getGameTime() {
        return gameTime;
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }
}
