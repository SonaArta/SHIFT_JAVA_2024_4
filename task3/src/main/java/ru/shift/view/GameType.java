package ru.shift.view;

public enum GameType {
    NOVICE(10, 9, 9),
    MEDIUM(40, 16, 16),
    EXPERT(99, 16, 30);

    private final int numberBomb;
    private final int numberRows;
    private final int numberColumns;

    GameType(int numberBomb, int numberRows, int numberColumns) {
        this.numberBomb = numberBomb;
        this.numberRows = numberRows;
        this.numberColumns = numberColumns;
    }

    public int getNumberBomb() {
        return numberBomb;
    }

    public int getNumberRows() {
        return numberRows;
    }

    public int getNumberColumns() {
        return numberColumns;
    }

}
