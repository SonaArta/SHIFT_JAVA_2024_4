package ru.shift.app;

import ru.shift.controller.Controller;
import ru.shift.controller.RecordController;
import ru.shift.controller.ViewController;
import ru.shift.model.GameModel;
import ru.shift.model.TimeCounter.TimeCounter;
import ru.shift.view.GameType;
import ru.shift.view.HighScoresWindow;
import ru.shift.view.MainWindow;
import ru.shift.view.SettingsWindow;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        SettingsWindow settingsWindow = new SettingsWindow(mainWindow);
        HighScoresWindow highScoresWindow = new HighScoresWindow(mainWindow);
        ViewController viewController = new ViewController(mainWindow);

        TimeCounter timeCounter = new TimeCounter(viewController);
        GameModel gameModel = new GameModel(GameType.NOVICE, viewController);

        RecordController recordController = new RecordController(highScoresWindow, gameModel, timeCounter);
        Controller controller = new Controller(mainWindow, gameModel, recordController);

        mainWindow.setNewGameMenuAction(e -> settingsWindow.setVisible(true));
        mainWindow.setSettingsMenuAction(e -> settingsWindow.setVisible(true));
        mainWindow.setHighScoresMenuAction(e -> highScoresWindow.setVisible(true));
        mainWindow.setExitMenuAction(e -> {
            recordController.saveAllRecord();
            mainWindow.dispose();
        });
        mainWindow.setExitAction(recordController);
        mainWindow.setCellListener(controller);

        settingsWindow.setGameTypeListener(controller);

        gameModel.setCellListener(viewController);
        gameModel.setStartGameListener(timeCounter);
        gameModel.setEndGameListener(List.of(timeCounter, controller));
    }
}