package ru.shift.app;

import ru.shift.controller.Controller;
import ru.shift.controller.RecordController;
import ru.shift.view.HighScoresWindow;
import ru.shift.view.MainWindow;
import ru.shift.view.SettingsWindow;

public class Application {

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        SettingsWindow settingsWindow = new SettingsWindow(mainWindow);
        HighScoresWindow highScoresWindow = new HighScoresWindow(mainWindow);

        RecordController recordController = new RecordController(highScoresWindow);
        Controller controller = new Controller(mainWindow, recordController);


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
    }
}