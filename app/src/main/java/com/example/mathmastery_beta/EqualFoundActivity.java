package com.example.mathmastery_beta;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import android.view.Gravity;

import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mathmastery_beta.aniimation.Animator;
import com.example.mathmastery_beta.forms.LevelCompleteForm;
import com.example.mathmastery_beta.handlers.HandlerAdaptive;
import com.example.mathmastery_beta.handlers.HandlerDataSave;
import com.example.mathmastery_beta.handlers.HandlerTimer;
import com.example.mathmastery_beta.level_status_model.EqualFoundModel;
import com.example.mathmastery_beta.handlers.HandlerJSON;

import java.util.Random;

public class EqualFoundActivity extends AppCompatActivity {

    private TextView num1;
    private TextView operation;
    private TextView num2;
    private TextView levelNumber;
    private TextView bestTime;
    private TableLayout gameFieldBlock;
    private TextView timer;
    private ImageButton functionalHeaderIcon;

    HandlerTimer handlerTimer;
    HandlerJSON handlerJSON = new HandlerJSON(this);
    HandlerDataSave handlerDataSave = new HandlerDataSave(this);
    Animator animator = new Animator();
    EqualFoundModel model = new EqualFoundModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equal_found);

        initializeComponent();

        setFunctionalHeaderIcon();
        generateComponent();
        generateExample();
        adaptiveComponent();

        handlerTimer = new HandlerTimer(timer);
        handlerTimer.startTimer();
    }

    private void initializeComponent() {
        num1 = findViewById(R.id.num1);
        operation = findViewById(R.id.operation);
        num2 = findViewById(R.id.num2);
        levelNumber = findViewById(R.id.levelNumber);
        bestTime = findViewById(R.id.bestTime);
        gameFieldBlock = findViewById(R.id.gameFieldBlock);
        timer = findViewById(R.id.currentTime);
        functionalHeaderIcon = findViewById(R.id.functional_header_icon);
    }

    private void setFunctionalHeaderIcon() {functionalHeaderIcon.setOnClickListener(v -> finish());}

    private void generateComponent() {
        Intent intent = getIntent();
        int level = intent.getIntExtra("levelNumber", 0);
        levelNumber.setText(String.valueOf(level));

        String jsonPath = intent.getStringExtra("json");
        String json = handlerJSON.loadJSON(jsonPath);
        model = HandlerJSON.getJSONote(json, level, EqualFoundModel.class);

        bestTime.setText(model.getRecord());

        int columns = 2;
        int rows = 2;
        String[] content = {">", "<", "="};

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int buttonSize = (screenWidth - (rows * 100)) / columns;
        int fullButtonSize = screenWidth - (rows * 100);

        int contentIndex = 0;
        for (int row = 0; row < rows; row++) {
            TableRow tableRow = new TableRow(this);

            for (int col = 0; col < (row == 1 ? 1 : columns); col++) {
                TextView label = new TextView(this);

                label.setText(content[contentIndex]);
                contentIndex++;

                label.setTextSize(45);
                label.setTypeface(null, Typeface.BOLD);
                label.setGravity(Gravity.CENTER);
                label.setBackgroundResource(R.drawable.cell_border_game);
                label.setTextColor(Color.parseColor("#939272"));

                TableRow.LayoutParams params = new TableRow.LayoutParams(
                        row == 1 ? fullButtonSize : buttonSize, buttonSize
                );
                if (row == 1) {
                    params.span = 2;
                }

                params.setMargins(5, 5, 5, 5);
                label.setLayoutParams(params);

                label.setOnClickListener(v -> gameProcessClick(label));
                tableRow.addView(label);
            }

            gameFieldBlock.addView(tableRow);
        }
    }

    private int count = 0;
    private void gameProcessClick(TextView clickedLabel){
        int numToInt1 = Integer.parseInt(num1.getText().toString());
        int numToInt2 = Integer.parseInt(num2.getText().toString());

        boolean isCorrect = false;
        switch (clickedLabel.getText().toString()) {
            case ">":
                isCorrect = numToInt1 > numToInt2;
                break;
            case "<":
                isCorrect = numToInt1 < numToInt2;
                break;
            case "=":
                isCorrect = numToInt1 == numToInt2;
                break;
        }

        if (isCorrect) {
            count++;
            if (count < model.getCount()) {
                generateExample();
            }
            else {
                gameEnd();
            }
        }
        else {
            gameNotTrueEqual();
        }
    }

    private void generateExample(){
        int rangeMin = model.getRangeMin();
        int rangeMax = model.getRangeMax();

        Random random = new Random();
        int randomNum1 = rangeMin + random.nextInt((rangeMax - rangeMin) + 1);
        int randomNum2 = rangeMin + random.nextInt((rangeMax - rangeMin) + 1);

        num1.setText(String.valueOf(randomNum1));
        num2.setText(String.valueOf(randomNum2));

        setAnimation();

        HandlerAdaptive componentAdaptive = new HandlerAdaptive(this);
        componentAdaptive.setAdaptiveEqualExample();
    }

    private void setAnimation(){
        animator.textUpDownAnimation(num1, 0);
        animator.textUpDownAnimation(operation, 100);
        animator.textUpDownAnimation(num2, 200);
    }

    private void gameNotTrueEqual() {
        // penalty time animation
        handlerTimer.addFineTime();
    }

    private void gameEnd() {
        String currentRecord = timer.getText().toString();

        String record = model.getRecord();
        if ("00:00".equals(record)) {
            handlerDataSave.userLevelUp(model.getExp());
        }

        if (handlerTimer.isBetterRecord(currentRecord, record)) {
            model.setRecord(currentRecord);
            handlerJSON.updateRecord(getIntent().getStringExtra("json"), model, EqualFoundModel.class);
        }

        int currentLevel = model.getLevel();
        handlerJSON.unlockNextLevel(getIntent().getStringExtra("json"), currentLevel, EqualFoundModel.class);

        LevelCompleteForm levelCompleteForm = new LevelCompleteForm(this, handlerTimer);
        levelCompleteForm.showLevelCompleteDialog(model.getLevel(), currentRecord);
    }

    private void adaptiveComponent(){
        HandlerAdaptive handlerAdaptive = new HandlerAdaptive(this);
        handlerAdaptive.setGamesHeaderComponentSize();
        handlerAdaptive.setGamesScoreSize();
    }

}