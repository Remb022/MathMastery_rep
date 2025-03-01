package com.example.mathmastery_beta.handlers;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.widget.TextView;

public class HandlerTimer {
    private final TextView timer;
    private final OnTimerFinishListener listener;

    public HandlerTimer(TextView timer, OnTimerFinishListener listener) {
        this.timer = timer;
        this.listener = listener;
    }

    public void startCountDownTimer() {
        CountDownTimer countDownTimer = new CountDownTimer(3600000, 1000) {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = (3600000 - millisUntilFinished) / 1000;
                long minutes = seconds / 60;
                seconds = seconds % 60;
                timer.setText(String.format("%02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                if (listener != null) {
                    listener.onTimerFinish();
                }
            }
        };
        countDownTimer.start();
    }

    public interface OnTimerFinishListener {
        void onTimerFinish();
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
