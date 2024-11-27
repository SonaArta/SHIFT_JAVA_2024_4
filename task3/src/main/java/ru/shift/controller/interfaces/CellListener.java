package ru.shift.controller.interfaces;

import ru.shift.view.GameImage;

public interface CellListener {
    void onCellOpen(int x, int y, int countBombAround);
    void onBombOpen(int x, int y);
    void onToggleFlag(int x, int y, GameImage gameImage, int bombsCount);
}
