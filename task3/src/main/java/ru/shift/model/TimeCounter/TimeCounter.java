package ru.shift.model.TimeCounter;

import ru.shift.controller.interfaces.EndGameListener;
import ru.shift.controller.interfaces.StartGameListener;
import ru.shift.controller.interfaces.TimerListener;

import java.util.Timer;
import java.util.TimerTask;

public class TimeCounter implements StartGameListener, EndGameListener {
    private final TimerListener listener;
    private long startTime;
    private int currentGameTime;
    private Timer timer;
    private boolean isStop;

    public TimeCounter(TimerListener listener) {
        this.listener = listener;
    }

    public int getCurrentGameTime() {
        if (!isStop) {
            currentGameTime = (int) ((System.currentTimeMillis() - startTime) / 1000);
        }
        return currentGameTime;
    }

    @Override
    public void onGameEnd() {
        if (timer != null) {
            this.timer.cancel();
            this.timer.purge();
            this.timer = null;
        }
        this.isStop = true;

        currentGameTime = (int) ((System.currentTimeMillis() - startTime) / 1000);
    }

    @Override
    public void onGameStart() {
        this.timer = new Timer();
        this.startTime = System.currentTimeMillis();
        this.currentGameTime = 0;
        this.isStop = false;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isStop) {
                    listener.onTimerUpdate(getCurrentGameTime());
                }
            }
        }, 0, 1000);
    }
}
