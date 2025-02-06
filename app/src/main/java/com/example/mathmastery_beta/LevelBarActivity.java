package com.example.mathmastery_beta;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.example.mathmastery_beta.adaptor.LevelPagerAdapter;
import com.example.mathmastery_beta.level_status_model.LevelModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            List<? extends LevelModel> modelList = parseJSON(json,
                    (Class<? extends LevelModel>) modelClass.asSubclass(LevelModel.class));

            // pages generate
            List<List<Integer>> levelPages = createPages(modelList);
            LevelPagerAdapter adapter = new LevelPagerAdapter(levelPages, this);
            viewPager.setAdapter(adapter);
        }
        catch (ClassNotFoundException ex) {
            Log.e("NotFoundModel", "Model-Class not found", ex);
        }
    }

    private void setFunctionalHeaderIcon() {
        ImageButton functionalHeaderIcon = findViewById(R.id.functional_header_icon);
        functionalHeaderIcon.setImageResource(R.drawable.icon_homepage);
        functionalHeaderIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    private <T> List<T> parseJSON(String json, Class<T> model) {
        Gson gson = new Gson();
        Type type = TypeToken.getParameterized(List.class, model).getType();

        return gson.fromJson(json, type);
    }

    private <T extends LevelModel> List<List<Integer>> createPages(List<T> model) {
        List<List<Integer>> pages = new ArrayList<>();
        List<Integer> levels = new ArrayList<>();

        for (T m : model) {
            levels.add(m.getLevel());
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