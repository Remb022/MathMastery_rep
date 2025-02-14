package com.example.mathmastery_beta;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mathmastery_beta.adaptor.LevelPagerAdapter;
import com.example.mathmastery_beta.level_status_model.LevelModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.IOUtils;

public class LevelBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_bar);

        setFunctionalHeaderIcon();
        ViewPager2 viewPager = findViewById(R.id.level_list_pager);

        // json parse
        String jsonFilePath = getIntent().getStringExtra("json");
        String model = getIntent().getStringExtra("model");
        String json = loadJSON(jsonFilePath);

        try {
            Class<?> modelClass = Class.forName(Objects.requireNonNull(model));
            List<LevelModel> modelList = parseJSON(json, modelClass);

            // pages generate
            List<List<LevelModel>> levelPages = createPages(modelList);
            LevelPagerAdapter adapter = new LevelPagerAdapter(levelPages, this, jsonFilePath);
            viewPager.setAdapter(adapter);
        }
        catch (ClassNotFoundException ex) {
            Log.e("NotFoundModel", "Model-Class not found", ex);
        }

        // component adaptive
        ComponentAdaptive componentAdaptive = new ComponentAdaptive(this);
        componentAdaptive.setHeaderComponentSize();
        componentAdaptive.setFooterTextSize();
    }

    private void setFunctionalHeaderIcon() {
        ImageButton functionalHeaderIcon = findViewById(R.id.functional_header_icon);
        functionalHeaderIcon.setImageResource(R.drawable.icon_homepage);
        functionalHeaderIcon.setOnClickListener(v -> finish());
    }

    private String loadJSON(String fileName) {
        try (InputStream is = getAssets().open(fileName)) {
            return IOUtils.toString(is, StandardCharsets.UTF_8);
        }
        catch (IOException ex) {
            Log.e("JsonLoad", "Error loading JSON from asset", ex);
            return null;
        }
    }

    private List<LevelModel> parseJSON(String json, Class<?> model) {
        Gson gson = new Gson();
        Type type = TypeToken.getParameterized(List.class, model).getType();

        return gson.fromJson(json, type);
    }

    private List<List<LevelModel>> createPages(List<LevelModel> modelList) {
        List<List<LevelModel>> pages = new ArrayList<>();
        List<LevelModel> levels = new ArrayList<>();

        for (LevelModel m : modelList) {
            levels.add(m);
            if (levels.size() == 9) {
                pages.add(new ArrayList<>(levels));
                levels.clear();
            }
        }

        if (!levels.isEmpty()) {
            pages.add(levels);
        }

        return pages;
    }

}