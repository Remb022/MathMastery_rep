package com.example.mathmastery_beta.handlers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HandlerDataSave {
    private final Context context;

    public HandlerDataSave(Context context) {
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

    public void saveNickname(String nickname) {
        SharedPreferences prefs = context.getSharedPreferences("userProgress", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("nickname", nickname);
        editor.apply();
    }

    public String getNickname() {
        SharedPreferences prefs = context.getSharedPreferences("userProgress", Context.MODE_PRIVATE);
        return prefs.getString("nickname", "User_db0075");
    }

    public void saveAvatar(String filePath) {
        SharedPreferences prefs = context.getSharedPreferences("userProgress", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("avatarFilePath", filePath);
        editor.apply();
    }

    public String getAvatar() {
        SharedPreferences prefs = context.getSharedPreferences("userProgress", Context.MODE_PRIVATE);
        return prefs.getString("avatarFilePath", null);
    }

    public String copyImageFromUri(Uri uri) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            if (inputStream == null) {
                return null;
            }

            File oldFile = new File(context.getFilesDir(), "avatar.jpg");
            if (oldFile.exists()) {
                boolean isDeleted = oldFile.delete();
                if (!isDeleted) {
                    Log.e("Copy File", "Failed to Delete Old Avatar File");
                }
            }


            File file = new File(context.getFilesDir(), "avatar.jpg");
            FileOutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            inputStream.close();
            outputStream.close();

            return file.getAbsolutePath();
        }
        catch (IOException e) {
            Log.e("Copy File", "Error Copy, File Not Download");
            return null;
        }
    }

}
