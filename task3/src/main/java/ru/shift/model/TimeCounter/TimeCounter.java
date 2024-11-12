package ru.shift.model.TimeCounter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class TimeCounter {
    private long startTime;
    private int currentGameTime;
    private Timer timer;
    boolean isStop;


    public void startTimer(ActionListener listener) {
        this.startTime = System.currentTimeMillis();
        this.currentGameTime = 0;
        this.timer = new Timer();
        this.isStop = false;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isStop) {
                    listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                }
            }
        }, 0, 1000);

    }

    public void stopTimer() {
        if (timer != null) {
            this.timer.cancel();
            this.timer.purge();
            this.timer = null;
        }
        this.isStop = true;

        currentGameTime = (int) ((System.currentTimeMillis() - startTime) / 1000);
    }

    public int getCurrentGameTime() {
        if (!isStop) {
            currentGameTime = (int) ((System.currentTimeMillis() - startTime) / 1000);
        }
        return currentGameTime;
    }

}
