package com.example.mathmastery_beta.level_status_model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HandlerJSON {
    private final Context context;

    public HandlerJSON(Context context) {
        this.context = context;
    }

    public static <T extends LevelModel> T getJSONote(String json, int level, Class<T> modelClass) {
        Gson gson = new Gson();
        Type type = TypeToken.getParameterized(List.class, modelClass).getType();
        List<T> models = gson.fromJson(json, type);

        return models.stream()
                .filter(model -> model.getLevel() == level)
                .findFirst()
                .orElse(null);
    }

    public String loadJSON(String jsonPath) {
        try (InputStream is = context.getAssets().open(jsonPath)) {
            return IOUtils.toString(is, StandardCharsets.UTF_8);
        }
        catch (IOException ex) {
            Log.e("JsonLoad", "Error loading JSON from asset", ex);
            return null;
        }
    }

}
