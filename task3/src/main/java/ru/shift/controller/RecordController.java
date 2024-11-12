package ru.shift.controller;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static ru.shift.utils.Constant.*;
import static ru.shift.utils.FileUtils.*;

import ru.shift.model.Record;
import ru.shift.view.GameType;
import ru.shift.view.HighScoresWindow;

public class RecordController implements RecordNameListener {
    private final HighScoresWindow highScoresWindow;
    private List<Record> winnersNoviceMode;
    private List<Record> winnersMediumMode;
    private List<Record> winnersExpertMode;
    private GameType currentGameType;
    private int currentTimeRecord;

    public RecordController(HighScoresWindow highScoresWindow) {
        this.highScoresWindow = highScoresWindow;
        initializeListWinners();
        setAllRecord();
    }

    @Override
    public void onRecordNameEntered(String name) {
        name = name.isEmpty() ? DEFAULT_NAME : name;
        List<Record> currentList = getWinnerListGameType(currentGameType);
        Record newRecord = new Record(name, currentTimeRecord);
        boolean isRepeatRecord = isRepeatPersonRecord(name, currentList);

        if (currentList.isEmpty() || (currentTimeRecord < currentList.get(0).getGameTime() && !isRepeatRecord)) {
            currentList.add(0, newRecord);
        } else {
            if (isRepeatRecord) {
                editExistWinner(name, currentList);
            } else {
                currentList.add(newRecord);
            }
            currentList.sort(Comparator.comparingInt(Record::getGameTime));
        }
        setAllRecord();
    }

    public void setCurrentGameType(GameType currentGameType) {
        this.currentGameType = currentGameType;
    }

    public void setCurrentTimeRecord(int currentTimeRecord) {
        this.currentTimeRecord = currentTimeRecord;
    }

    public void saveAllRecord() {
        if (!winnersNoviceMode.isEmpty()) {
            writeObjectListToFile(winnersNoviceMode, NOVICE_FILE);
        }
        if (!winnersMediumMode.isEmpty()) {
            writeObjectListToFile(winnersMediumMode, MEDIUM_FILE);
        }
        if (!winnersExpertMode.isEmpty()) {
            writeObjectListToFile(winnersExpertMode, EXPERT_FILE);
        }
    }

    public void setAllRecord() {
        if (!winnersNoviceMode.isEmpty()) {
            highScoresWindow.setNoviceRecord(createTextForRecord(winnersNoviceMode));
        }
        if (!winnersMediumMode.isEmpty()) {
            highScoresWindow.setMediumRecord(createTextForRecord(winnersMediumMode));
        }
        if (!winnersExpertMode.isEmpty()) {
            highScoresWindow.setExpertRecord(createTextForRecord(winnersExpertMode));
        }
    }

    private List<Record> getWinnerListGameType(GameType gameType) {
        return switch (gameType) {
            case NOVICE -> winnersNoviceMode;
            case MEDIUM -> winnersMediumMode;
            case EXPERT -> winnersExpertMode;
        };
    }

    private void initializeListWinners() {
        this.winnersNoviceMode = new LinkedList<>();
        this.winnersMediumMode = new LinkedList<>();
        this.winnersExpertMode = new LinkedList<>();

        winnersNoviceMode = readObjectListFromFile(NOVICE_FILE);
        winnersMediumMode = readObjectListFromFile(MEDIUM_FILE);
        winnersExpertMode = readObjectListFromFile(EXPERT_FILE);
    }

    private void editExistWinner(String name, List<Record> currentList) {
        for (Record currentRecord : currentList) {
            if (currentRecord.getPlayerName().equals(name) && currentRecord.getGameTime() > currentTimeRecord) {
                currentRecord.setGameTime(currentTimeRecord);
                break;
            }
        }
    }

    private boolean isRepeatPersonRecord(String name, List<Record> currentList) {
        return currentList.stream()
                .anyMatch(currentRecord -> currentRecord.getPlayerName().equals(name));
    }

    private String createTextForRecord(List<Record> currentList) {
        StringBuilder currentString = new StringBuilder();
        int i = 1;
        for (Record currentRecord : currentList) {
            String tempString = i + POINT + currentRecord.getPlayerName()
                    + DELIMITER + currentRecord.getGameTime()
                    + MEASURE + "\n";
            currentString.append(tempString);
            i++;
        }
        return currentString.toString();
    }
}
