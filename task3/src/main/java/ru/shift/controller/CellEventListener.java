package ru.shift.controller;

import ru.shift.view.ButtonType;

public interface CellEventListener {
    void onMouseClick(int x, int y, ButtonType buttonType);
}
