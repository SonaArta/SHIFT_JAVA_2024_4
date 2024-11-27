package ru.shift.model;

public class Cell {
    private int countBombAround;
    private boolean isOpen, isFlag, isBomb;

    public Cell() {
        this.countBombAround = 0;
        this.isOpen = false;
        this.isFlag = false;
        this.isBomb = false;
    }

    public int getCountBombAround() {
        return countBombAround;
    }

    public void increaseCountBomb() {
        this.countBombAround++;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen() {
        this.isOpen = true;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void toggleFlag() {
        this.isFlag = !this.isFlag;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public void setBomb() {
        this.isBomb = true;
    }

}
