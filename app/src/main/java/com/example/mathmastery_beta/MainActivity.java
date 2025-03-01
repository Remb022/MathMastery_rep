package com.example.mathmastery_beta;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.mathmastery_beta.handlers.HandlerAdaptive;
import com.example.mathmastery_beta.handlers.HandlerScore;
import com.example.mathmastery_beta.level_status_model.EqualFoundModel;
import com.example.mathmastery_beta.handlers.HandlerJSON;
import com.example.mathmastery_beta.level_status_model.LevelModel;
import com.example.mathmastery_beta.level_status_model.OperandFoundModel;
import com.example.mathmastery_beta.level_status_model.OperationFoundModel;
import com.example.mathmastery_beta.level_status_model.ResultFoundModel;

public class MainActivity extends AppCompatActivity {

    private final HandlerJSON handlerJSON = new HandlerJSON(this);
    private final HandlerScore handlerScore = new HandlerScore(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFunctionalHeaderIcon();
        downloadJsonFiles();
        setClickListener();
        adaptiveComponent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCardLevels();
        updateProgress();
    }

    private void setFunctionalHeaderIcon() {
        ImageButton functionalHeaderIcon = findViewById(R.id.functional_header_icon);
        functionalHeaderIcon.setImageResource(R.drawable.icon_settings);
    }

    private void downloadJsonFiles(){
        HandlerJSON handlerJSON = new HandlerJSON(this);
        handlerJSON.copyFileFromAssets("operation_found.json", "operation_found.json");
        handlerJSON.copyFileFromAssets("operand_found.json", "operand_found.json");
        handlerJSON.copyFileFromAssets("result_found.json", "result_found.json");
        handlerJSON.copyFileFromAssets("equal_found.json", "equal_found.json");
    }

    private void setClickListener(){
        CardView operandFoundGame = findViewById(R.id.operand_found_game);
        CardView operationFoundGame = findViewById(R.id.operation_found_game);
        CardView resultFoundGame = findViewById(R.id.result_found_game);
        CardView equalFoundGame = findViewById(R.id.equal_found_game);

        operandFoundGame.setOnClickListener(v -> startLevelBarActivity("operand_found.json",
                OperandFoundModel.class));

        operationFoundGame.setOnClickListener(v -> startLevelBarActivity("operation_found.json",
                OperationFoundModel.class));

        resultFoundGame.setOnClickListener(v -> startLevelBarActivity("result_found.json",
                ResultFoundModel.class));

        equalFoundGame.setOnClickListener(v -> startLevelBarActivity("equal_found.json",
                EqualFoundModel.class));
    }

    private void startLevelBarActivity(String jsonFileName, Class<?> model) {
        Intent intent = new Intent(MainActivity.this, LevelBarActivity.class);
        intent.putExtra("json", jsonFileName);
        intent.putExtra("model", model.getName());
        startActivity(intent);
    }

    @SuppressLint("SetTextI18n")
    private <T extends LevelModel> void setMaxLevelForCard(int id, String jsonFileName, Class<T> modelClass) {
        TextView cardTextView = findViewById(id);
        int maxLevel = handlerJSON.getMaxActiveLevel(jsonFileName, modelClass);
        cardTextView.setText("Level: " + maxLevel);
    }

    private void updateCardLevels() {
        setMaxLevelForCard(R.id.level_card_score_1, "operand_found.json", OperandFoundModel.class);
        setMaxLevelForCard(R.id.level_card_score_2, "operation_found.json", OperationFoundModel.class);
        setMaxLevelForCard(R.id.level_card_score_3, "result_found.json", ResultFoundModel.class);
        setMaxLevelForCard(R.id.level_card_score_4, "equal_found.json", EqualFoundModel.class);
    }

    private void updateProgress() {
        TextView levelFooterIndex = findViewById(R.id.level_footer_index);
        TextView levelHeaderInfo = findViewById(R.id.level_header_info);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        handlerScore.updateProgress(levelFooterIndex, levelHeaderInfo, progressBar);
    }

    private void adaptiveComponent(){
        HandlerAdaptive handlerAdaptive = new HandlerAdaptive(this);
        handlerAdaptive.setMainHeaderComponentSize();
        handlerAdaptive.setMainPageComponentSize();
        handlerAdaptive.setFooterTextSize();
    }

}