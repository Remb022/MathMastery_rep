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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mathmastery_beta.handlers.HandlerAdaptive;
import com.example.mathmastery_beta.handlers.HandlerCalculate;
import com.example.mathmastery_beta.handlers.HandlerDataSave;
import com.example.mathmastery_beta.handlers.HandlerTimer;
import com.example.mathmastery_beta.handlers.HandlerJSON;
import com.example.mathmastery_beta.level_status_model.OperationFoundModel;

import java.text.DecimalFormat;
import java.util.Random;

public class OperationFoundActivity extends AppCompatActivity {

    HandlerTimer handlerTimer;
    HandlerCalculate calculator = new HandlerCalculate();
    HandlerJSON handlerJSON = new HandlerJSON(this);
    HandlerDataSave handlerDataSave = new HandlerDataSave(this);
    private OperationFoundModel model = new OperationFoundModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_found);

        setFunctionalHeaderIcon();
        generateComponent();
        generateExample();
        adaptiveComponent();

        TextView timer = findViewById(R.id.currentTime);
        handlerTimer = new HandlerTimer(timer);
        handlerTimer.startTimer();
    }

    private void setFunctionalHeaderIcon() {
        ImageButton functionalHeaderIcon = findViewById(R.id.functional_header_icon);
        functionalHeaderIcon.setImageResource(R.drawable.icon_list);
        functionalHeaderIcon.setOnClickListener(v -> finish());
    }

    private void generateComponent() {
        Intent intent = getIntent();
        TextView levelNumber = findViewById(R.id.levelNumber);
        int level = intent.getIntExtra("levelNumber", 0);
        levelNumber.setText(String.valueOf(level));

        String jsonPath = intent.getStringExtra("json");
        String json = handlerJSON.loadJSON(jsonPath);
        model = HandlerJSON.getJSONote(json, level, OperationFoundModel.class);

        TextView bestTime = findViewById(R.id.bestTime);
        bestTime.setText(model.getRecord());

        TableLayout gameFieldBlock = findViewById(R.id.gameFieldBlock);

        String[] operations = model.getOperationList();
        int operationCount = operations.length;
        int columns = 2;
        int rows = operationCount / columns;

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int buttonSize = (screenWidth - (rows * 100)) / columns;

        int contentIndex = 0;
        for (int row = 0; row < rows; row++) {
            TableRow tableRow = new TableRow(this);

            for (int col = 0; col < columns; col++) {
                TextView label = new TextView(this);

                label.setText(operations[contentIndex]);
                contentIndex++;

                label.setTextSize(45);
                label.setTypeface(null, Typeface.BOLD);
                label.setGravity(Gravity.CENTER);
                label.setBackgroundResource(R.drawable.cell_border_game);
                label.setTextColor(Color.parseColor("#939272"));

                TableRow.LayoutParams params = new TableRow.LayoutParams(buttonSize, buttonSize);
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
        TextView num1 = findViewById(R.id.num1);
        TextView num2 = findViewById(R.id.num2);
        TextView res = findViewById(R.id.result);

        int numToInt1 = Integer.parseInt(num1.getText().toString());
        int numToInt2 = Integer.parseInt(num2.getText().toString());
        String result = res.getText().toString();
        String operation = clickedLabel.getText().toString();

        double resultValue = calculator.calculateResult(numToInt1, numToInt2, operation);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String decFormatResult = decimalFormat.format(resultValue);

        if (decFormatResult.equals(result)) {
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
        String[] operations = model.getOperationList();

        Random random = new Random();
        int randomNum1 = rangeMin + random.nextInt((rangeMax - rangeMin) + 1);
        int randomNum2 = rangeMin + random.nextInt((rangeMax - rangeMin) + 1);
        String randomOperation = operations[random.nextInt(operations.length)];

        double resultValue = calculator.calculateResult(randomNum1, randomNum2, randomOperation);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String decFormatResult = decimalFormat.format(resultValue);

        TextView num1 = findViewById(R.id.num1);
        TextView num2 = findViewById(R.id.num2);
        TextView result = findViewById(R.id.result);

        num1.setText(String.valueOf(randomNum1));
        num2.setText(String.valueOf(randomNum2));
        result.setText(decFormatResult);

        HandlerAdaptive handlerAdaptive = new HandlerAdaptive(this);
        handlerAdaptive.setAdaptiveExample();
    }

    private void gameNotTrueEqual() {
        Toast.makeText(this, "Not True Operation!", Toast.LENGTH_SHORT).show();
        handlerTimer.addFineTime();
    }

    private void gameEnd() {
        TextView currentRecordTextView = findViewById(R.id.currentTime);
        String currentRecord = currentRecordTextView.getText().toString();

        String record = model.getRecord();
        if ("00:00".equals(record)) {
            handlerDataSave.userLevelUp(model.getExp());
        }

        if (handlerTimer.isBetterRecord(currentRecord, record)) {
            model.setRecord(currentRecord);
            handlerJSON.updateRecord(getIntent().getStringExtra("json"), model, OperationFoundModel.class);
        }

        int currentLevel = model.getLevel();
        handlerJSON.unlockNextLevel(getIntent().getStringExtra("json"), currentLevel, OperationFoundModel.class);

        Toast.makeText(this, "Level Complete!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void adaptiveComponent(){
        HandlerAdaptive handlerAdaptive = new HandlerAdaptive(this);
        handlerAdaptive.setGamesHeaderComponentSize();
        handlerAdaptive.setGamesScoreSize();
    }

}