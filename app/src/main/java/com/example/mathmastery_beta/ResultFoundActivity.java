package com.example.mathmastery_beta;

import android.annotation.SuppressLint;
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
import com.example.mathmastery_beta.handlers.HandlerJSON;
import com.example.mathmastery_beta.handlers.HandlerDataSave;
import com.example.mathmastery_beta.handlers.HandlerTimer;
import com.example.mathmastery_beta.level_status_model.ResultFoundModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ResultFoundActivity extends AppCompatActivity {

    private List<Integer> numList1;
    private List<Integer> numList2;
    private List<String> operationList;
    private List<Double> resultList;

    HandlerTimer handlerTimer;
    HandlerCalculate calculator = new HandlerCalculate();
    HandlerJSON handlerJSON = new HandlerJSON(this);
    HandlerDataSave handlerDataSave = new HandlerDataSave(this);
    private ResultFoundModel model = new ResultFoundModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_found);

        setFunctionalHeaderIcon();
        generateComponent();
        showExample(0);
        adaptiveComponent();

        TextView timer = findViewById(R.id.currentTime);
        handlerTimer = new HandlerTimer(timer);
        handlerTimer.startTimer();
    }

    private void setFunctionalHeaderIcon() {
        ImageButton functionalHeaderIcon = findViewById(R.id.functional_header_icon);
        functionalHeaderIcon.setImageResource(R.drawable.icon_homepage);
        functionalHeaderIcon.setOnClickListener(v -> finish());
    }

    private void generateComponent() {
        Intent intent = getIntent();
        TextView levelNumber = findViewById(R.id.levelNumber);
        int level = intent.getIntExtra("levelNumber", 0);
        levelNumber.setText(String.valueOf(level));

        String jsonPath = intent.getStringExtra("json");
        String json = handlerJSON.loadJSON(jsonPath);
        model = HandlerJSON.getJSONote(json, level, ResultFoundModel.class);

        TextView bestTime = findViewById(R.id.bestTime);
        bestTime.setText(model.getRecord());

        TableLayout gameFieldBlock = findViewById(R.id.gameFieldBlock);
        gameFieldBlock.removeAllViews();

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int buttonSize = (screenWidth - 100) / model.getWidth();

        generateExample();
        Collections.shuffle(resultList);

        int index = 0;
        for (int row = 0; row < model.getHeight(); row++) {
            TableRow tableRow = new TableRow(this);

            for (int col = 0; col < model.getWidth(); col++) {
                TextView label = new TextView(this);

                @SuppressLint("DefaultLocale")
                String result = String.format("%.0f", resultList.get(index));
                label.setText(result);

                label.setTextSize(25);
                label.setTypeface(null, Typeface.BOLD);
                label.setGravity(Gravity.CENTER);
                label.setBackgroundResource(R.drawable.cell_border_game);
                label.setTextColor(Color.parseColor("#939272"));

                TableRow.LayoutParams params = new TableRow.LayoutParams(buttonSize, buttonSize);
                params.setMargins(5, 5, 5, 5);
                label.setLayoutParams(params);

                label.setOnClickListener(v -> gameProcessClick(label));
                tableRow.addView(label);
                index++;
            }
            gameFieldBlock.addView(tableRow);
        }
    }

    private int page = 0;
    private int count = 0;
    private void gameProcessClick(TextView clickedLabel){
        double result = Double.parseDouble(clickedLabel.getText().toString());
        double correctResult = calculator.calculateResult(numList1.get(count), numList2.get(count), operationList.get(count));

        if (Math.abs(result - correctResult) < 0.1) {
            count++;
            if (count < model.getWidth() * model.getHeight()) {
                clickedLabel.setEnabled(false);
                clickedLabel.setBackgroundResource(R.drawable.cell_border_game_clicked);
                clickedLabel.setTextColor(Color.parseColor("#b5b5b5"));
                showExample(count);
            }
            else {
                page++;
                if (page < model.getPage()) {
                    generateComponent();
                    count = 0;
                    showExample(count);
                }
                else {
                    gameEnd();
                }
            }
        }
        else {
            gameNotTrueEqual();
        }
    }

    private void showExample(int index){
        TextView num1 = findViewById(R.id.num1);
        TextView operation = findViewById(R.id.operation);
        TextView num2 = findViewById(R.id.num2);

        num1.setText(String.valueOf(numList1.get(index)));
        operation.setText(operationList.get(index));
        num2.setText(String.valueOf(numList2.get(index)));

        HandlerAdaptive handlerAdaptive = new HandlerAdaptive(this);
        handlerAdaptive.setAdaptiveExample();
    }

    private void generateExample() {
        numList1 = generateExampleNum();
        numList2 = generateExampleNum();
        operationList = generateExampleOperations();
        resultList = generateExampleResults(numList1, numList2, operationList);
    }

    private List<Integer> generateExampleNum() {
        int rangeMin = model.getRangeMin();
        int rangeMax = model.getRangeMax();
        Random random = new Random();

        List<Integer> exampleNum = new ArrayList<>();
        for (int i = 0; i < model.getWidth() * model.getHeight(); i++) {
            int randomNum = rangeMin + random.nextInt((rangeMax - rangeMin) + 1);
            exampleNum.add(randomNum);
        }

        return exampleNum;
    }

    private List<String> generateExampleOperations() {
        String[] operations = model.getOperationList();
        Random random = new Random();

        List<String> exampleOperations = new ArrayList<>();
        for (int i = 0; i < model.getWidth() * model.getHeight(); i++) {
            String randomOperation = operations[random.nextInt(operations.length)];
            exampleOperations.add(randomOperation);
        }

        return exampleOperations;
    }

    private List<Double> generateExampleResults(List<Integer> numbers1, List<Integer> numbers2, List<String> operations) {
        HandlerCalculate calculator = new HandlerCalculate();
        List<Double> exampleResults = new ArrayList<>();

        for (int i = 0; i < model.getWidth() * model.getHeight(); i++) {
            int num1 = numbers1.get(i);
            int num2 = numbers2.get(i);
            String operation = operations.get(i);

            double result = calculator.calculateResult(num1, num2, operation);
            exampleResults.add(result);
        }

        return exampleResults;
    }

    private void gameNotTrueEqual() {
        Toast.makeText(this, "Not True Result!", Toast.LENGTH_SHORT).show();
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
            handlerJSON.updateRecord(getIntent().getStringExtra("json"), model, ResultFoundModel.class);
        }

        int currentLevel = model.getLevel();
        handlerJSON.unlockNextLevel(getIntent().getStringExtra("json"), currentLevel, ResultFoundModel.class);

        Toast.makeText(this, "Level Complete!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void adaptiveComponent(){
        HandlerAdaptive handlerAdaptive = new HandlerAdaptive(this);
        handlerAdaptive.setGamesHeaderComponentSize();
        handlerAdaptive.setGamesScoreSize();
    }


}
