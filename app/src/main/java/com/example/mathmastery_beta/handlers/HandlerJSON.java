package com.example.mathmastery_beta.handlers;

import android.content.Context;
import android.util.Log;

import com.example.mathmastery_beta.level_status_model.LevelModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HandlerJSON {
    private final Context context;

    public HandlerJSON(Context context) {
        this.context = context;
    }

    public static <T extends LevelModel> T getJSONote(String json, int level, Class<T> modelClass) {
        List<T> models = getJSONoteList(json, modelClass);

        return models.stream()
                .filter(model -> model.getLevel() == level)
                .findFirst()
                .orElse(null);
    }

    public static <T extends LevelModel> List<T> getJSONoteList(String json, Class<T> modelClass) {
        Gson gson = new Gson();
        Type type = TypeToken.getParameterized(List.class, modelClass).getType();
        return gson.fromJson(json, type);
    }

    public String loadJSON(String jsonPath) {
        File file = new File(context.getFilesDir(), jsonPath);

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[(int) file.length()];
            fis.read(buffer);

            return new String(buffer, StandardCharsets.UTF_8);
        }
        catch (IOException ex) {
            Log.e("JsonLoad", "Error loading JSON from internal storage", ex);
            return null;
        }
    }

    public <T extends LevelModel> void updateRecord(String jsonPath, T updatedModel, Class<T> modelClass) {
        String json = loadJSON(jsonPath);
        List<T> models = getJSONoteList(json, modelClass);

        for (T model : models) {
            if (model.getLevel() == updatedModel.getLevel()) {
                model.setRecord(updatedModel.getRecord());
                break;
            }
        }

        Gson gson = new Gson();
        String updatedJson = gson.toJson(models);

        saveJson(updatedJson, jsonPath);
    }

    public void saveJson(String json, String fileName) {
        try {
            File file = new File(context.getFilesDir(), fileName);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(json.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException ex) {
            Log.e("JsonSave", "Error saving JSON", ex);
        }
    }

    public <T extends LevelModel> void unlockNextLevel(String jsonPath, int currentLevel, Class<T> modelClass) {
        String json = loadJSON(jsonPath);
        List<T> models = getJSONoteList(json, modelClass);

        T nextLevelModel = null;
        for (T model : models) {
            if (model.getLevel() == currentLevel + 1) {
                nextLevelModel = model;
                break;
            }
        }

        if (nextLevelModel != null) {
            nextLevelModel.setStatus("active");

            Gson gson = new Gson();
            String updatedJson = gson.toJson(models);
            saveJson(updatedJson, jsonPath);
        }
    }

    public <T extends LevelModel> int getMaxActiveLevel(String jsonPath, Class<T> modelClass) {
        String json = loadJSON(jsonPath);
        List<T> models = getJSONoteList(json, modelClass);
        if (models == null || models.isEmpty()) {
            return 0;
        }

        int maxLevel = 0;
        for (T model : models) {
            if ("active".equals(model.getStatus())) {
                maxLevel = Math.max(maxLevel, model.getLevel());
            }
        }

        return maxLevel;
    }

    public void copyFileFromAssets(String assetFileName, String destinationName) {
        File destFile = new File(context.getFilesDir(), destinationName);

        if (!destFile.exists()) {
            try (InputStream is = context.getAssets().open(assetFileName);
                 OutputStream os = new FileOutputStream(destFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
            }
            catch (IOException ex) {
                Log.e("FileCopy", "Error copying file to internal storage", ex);
            }
        }
    }

}
