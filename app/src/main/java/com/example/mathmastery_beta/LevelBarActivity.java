package com.example.mathmastery_beta;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mathmastery_beta.adaptor.LevelPagerAdapter;
import com.example.mathmastery_beta.handlers.HandlerAdaptive;
import com.example.mathmastery_beta.handlers.HandlerScore;
import com.example.mathmastery_beta.handlers.HandlerJSON;
import com.example.mathmastery_beta.level_status_model.LevelModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LevelBarActivity extends AppCompatActivity {

    private final HandlerScore handlerScore = new HandlerScore(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_bar);

        setFunctionalHeaderIcon();
        setGameInfo();
        setLevelList();
        adaptiveComponent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setLevelList();
        updateFooterProgress();
    }

    private void setFunctionalHeaderIcon() {
        ImageButton functionalHeaderIcon = findViewById(R.id.functional_header_icon);
        functionalHeaderIcon.setImageResource(R.drawable.icon_homepage);
        functionalHeaderIcon.setOnClickListener(v -> finish());
    }

    private void setGameInfo(){
        TextView gameInfo = findViewById(R.id.game_info);

        String gameType = getIntent().getStringExtra("json");
        String type = "";
        switch (Objects.requireNonNull(gameType)) {
            case "operand_found.json":
                type = "X + ? = C";
                break;
            case "operation_found.json":
                type = "X ? Y = C";
                break;
            case "result_found.json":
                type = "X + Y = ?";
                break;
            case "equal_found.json":
                type = "> < =";
                break;
            default:
                type = "EMPTY";
                break;
        }
        gameInfo.setText(type);
    }

    private void setLevelList(){
        ViewPager2 viewPager = findViewById(R.id.level_list_pager);

        String jsonFilePath = getIntent().getStringExtra("json");
        String model = getIntent().getStringExtra("model");
        String json = loadJSON(jsonFilePath);

        try {
            Class<?> modelClass = Class.forName(Objects.requireNonNull(model));
            List<LevelModel> modelList = parseJSON(json, modelClass);

            List<List<LevelModel>> levelPages = createPages(modelList);
            LevelPagerAdapter adapter = new LevelPagerAdapter(levelPages, this, jsonFilePath);
            viewPager.setAdapter(adapter);
        }
        catch (ClassNotFoundException ex) {
            Log.e("NotFoundModel", "Model-Class not found", ex);
        }
    }

    private String loadJSON(String fileName) {
        HandlerJSON handlerJSON = new HandlerJSON(this);
        return handlerJSON.loadJSON(fileName);
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

    private void updateFooterProgress() {
        TextView levelFooterIndex = findViewById(R.id.level_footer_index);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        handlerScore.updateFooterProgress(levelFooterIndex, progressBar);
    }

    private void adaptiveComponent(){
        HandlerAdaptive handlerAdaptive = new HandlerAdaptive(this);
        handlerAdaptive.setLevelBarHeaderComponentSize();
        handlerAdaptive.setFooterTextSize();
    }

}