package com.example.mathmastery_beta.handlers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HandlerScore {
    private final Context context;

    public HandlerScore(Context context) {
        this.context = context;
    }

    private void saveUserProgress(int level, int exp) {
        SharedPreferences prefs = context.getSharedPreferences("userProgress", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("userLevel", level);
        editor.putInt("userExp", exp);
        editor.apply();
    }

    private int getUserLevel() {
        SharedPreferences prefs = context.getSharedPreferences("userProgress", Context.MODE_PRIVATE);
        return prefs.getInt("userLevel", 1);
    }

    private int getUserExp() {
        SharedPreferences prefs = context.getSharedPreferences("userProgress", Context.MODE_PRIVATE);
        return prefs.getInt("userExp", 0);
    }

    private int calculateExpToNextLevel(int currentLevel) {
        return currentLevel * 100;
    }

    public void userLevelUp(int levelRewardExp) {
        int userExp = getUserExp();
        int userLevel = getUserLevel();
        int newExp = userExp + levelRewardExp;

        int expToNextLevel = calculateExpToNextLevel(userLevel);
        if (newExp >= expToNextLevel) {
            userLevel++;
            newExp -= expToNextLevel;
        }

        saveUserProgress(userLevel, newExp);
    }

    @SuppressLint("SetTextI18n")
    public void updateFooterProgress(TextView levelFooterIndex, ProgressBar progressBar) {
        int userLevel = getUserLevel();
        int userExp = getUserExp();
        int expToNextLevel = calculateExpToNextLevel(userLevel);

        levelFooterIndex.setText("LVL " + userLevel);

        int progress = (int) ((userExp / (float) expToNextLevel) * 100);
        progressBar.setProgress(progress);
    }

    @SuppressLint("SetTextI18n")
    public void updateProgress(TextView levelFooterIndex, TextView levelHeaderInfo, ProgressBar progressBar) {
        int userLevel = getUserLevel();
        int userExp = getUserExp();
        int expToNextLevel = calculateExpToNextLevel(userLevel);

        levelFooterIndex.setText("LVL " + userLevel);
        levelHeaderInfo.setText("Lv: " + userLevel);

        int progress = (int) ((userExp / (float) expToNextLevel) * 100);
        progressBar.setProgress(progress);
    }
}
