package com.example.mathmastery_beta.handlers;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;

public class HandlerTimer {
    private final TextView timer;
    private long elapsedTimeInMillis = 0;
    private final long interval = 1000;

    public HandlerTimer(TextView timer) {
        this.timer = timer;
    }

    private final Handler handler = new Handler();
    private final Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            elapsedTimeInMillis += interval;
            updateTimerText();
            handler.postDelayed(timerRunnable, interval);
        }
    };

    public void startTimer() {
        handler.postDelayed(timerRunnable, interval);
    }

    @SuppressLint("DefaultLocale")
    private void updateTimerText() {
        long seconds = elapsedTimeInMillis / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        timer.setText(String.format("%02d:%02d", minutes, seconds));
    }

    public void addFineTime() {
        elapsedTimeInMillis += 15000;
        updateTimerText();
    }

    public boolean isBetterRecord(String currentRecord, String record) {
        if ("00:00".equals(record)) {
            return true;
        }

        int currentTime = convertStringToTime(currentRecord);
        int time = convertStringToTime(record);

        return currentTime < time;
    }

    private int convertStringToTime(String time) {
        String[] separate = time.split(":");
        int minutes = Integer.parseInt(separate[0]);
        int seconds = Integer.parseInt(separate[1]);
        return minutes * 60 + seconds;
    }
}
