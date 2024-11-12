package ru.shift.view;

import javax.swing.*;

public enum GameImage {
    CLOSED("cell.png"),
    MARKED("flag.png"),
    EMPTY("empty.png"),
    NUM_1("1.png"),
    NUM_2("2.png"),
    NUM_3("3.png"),
    NUM_4("4.png"),
    NUM_5("5.png"),
    NUM_6("6.png"),
    NUM_7("7.png"),
    NUM_8("8.png"),
    BOMB("mine.png"),
    TIMER("timer.png"),
    BOMB_ICON("mineImage.png"),
    ;

    private final String fileName;
    private ImageIcon imageIcon;

    GameImage(String fileName) {
        this.fileName = fileName;
    }

    public static GameImage getGameImage(int number) {
        return switch (number) {
            case 0 -> EMPTY;
            case 1 -> NUM_1;
            case 2 -> NUM_2;
            case 3 -> NUM_3;
            case 4 -> NUM_4;
            case 5 -> NUM_5;
            case 6 -> NUM_6;
            case 7 -> NUM_7;
            case 8 -> NUM_8;
            default -> throw new IllegalArgumentException("Number must be between 0 and 8");
        };
    }

    public synchronized ImageIcon getImageIcon() {
        if (imageIcon == null) {
            imageIcon = new ImageIcon(ClassLoader.getSystemResource(fileName));
        }

        return imageIcon;
    }
}
