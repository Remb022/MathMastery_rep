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
import com.example.mathmastery_beta.handlers.HandlerJSON;
import com.example.mathmastery_beta.handlers.HandlerDataSave;
import com.example.mathmastery_beta.handlers.HandlerTimer;
import com.example.mathmastery_beta.level_status_model.OperandFoundModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class OperandFoundActivity extends AppCompatActivity {

    private List<Integer> operandList;

    HandlerTimer handlerTimer;
    HandlerCalculate calculator = new HandlerCalculate();
    HandlerJSON handlerJSON = new HandlerJSON(this);
    HandlerDataSave handlerDataSave = new HandlerDataSave(this);
    private OperandFoundModel model = new OperandFoundModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operand_found);

        setFunctionalHeaderIcon();
        generateComponent();
        generateExample(0);
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
        model = HandlerJSON.getJSONote(json, level, OperandFoundModel.class);

        TextView bestTime = findViewById(R.id.bestTime);
        bestTime.setText(model.getRecord());

        TableLayout gameFieldBlock = findViewById(R.id.gameFieldBlock);
        gameFieldBlock.removeAllViews();

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int buttonSize = (screenWidth - 100) / model.getWidth();

        for (int row = 0; row < model.getHeight(); row++) {
            TableRow tableRow = new TableRow(this);

            for (int col = 0; col < model.getWidth(); col++) {
                TextView label = new TextView(this);

                int randomValue = new Random().nextInt(model.getRangeMax() - model.getRangeMin() + 1) + model.getRangeMin();
                label.setText(String.valueOf(randomValue));

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
            }

            gameFieldBlock.addView(tableRow);
        }
        operandList = getOperandList();
    }

    private int page = 0;
    private int count = 0;
    private void gameProcessClick(TextView clickedLabel){
        TextView num1 = findViewById(R.id.num1);
        TextView op = findViewById(R.id.operation);
        TextView res = findViewById(R.id.result);

        int numToInt1 = Integer.parseInt(num1.getText().toString());
        int numToInt2 = Integer.parseInt(clickedLabel.getText().toString());
        String result = res.getText().toString();
        String operation = op.getText().toString();

        double resultValue = calculator.calculateResult(numToInt1, numToInt2, operation);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String decFormatResult = decimalFormat.format(resultValue);

        if (decFormatResult.equals(result)) {
            count++;
            if (count < model.getWidth() * model.getHeight()) {
                clickedLabel.setEnabled(false);
                clickedLabel.setBackgroundResource(R.drawable.cell_border_game_clicked);
                clickedLabel.setTextColor(Color.parseColor("#b5b5b5"));
                generateExample(count);
            }
            else {
                page++;
                if (page < model.getPage()) {
                    generateComponent();
                    count = 0;
                    generateExample(count);
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

    private void generateExample(int index){
        int rangeMin = model.getRangeMin();
        int rangeMax = model.getRangeMax();
        String[] operations = model.getOperationList();

        Random random = new Random();
        int randomNum1 = rangeMin + random.nextInt((rangeMax - rangeMin) + 1);
        String randomOperation = operations[random.nextInt(operations.length)];

        double resultValue = calculator.calculateResult(randomNum1, operandList.get(index), randomOperation);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String decFormatResult = decimalFormat.format(resultValue);

        TextView num1 = findViewById(R.id.num1);
        TextView operation = findViewById(R.id.operation);
        TextView result = findViewById(R.id.result);

        num1.setText(String.valueOf(randomNum1));
        operation.setText(randomOperation);
        result.setText(decFormatResult);

        HandlerAdaptive handlerAdaptive = new HandlerAdaptive(this);
        handlerAdaptive.setAdaptiveExample();
    }

    private List<Integer> getOperandList(){
        TableLayout gameFieldBlock = findViewById(R.id.gameFieldBlock);
        int height = model.getHeight();
        int width = model.getWidth();

        List<Integer> operandList = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            TableRow tableRow = (TableRow) gameFieldBlock.getChildAt(row);
            for (int col = 0; col < width; col++) {
                TextView cell = (TextView) tableRow.getChildAt(col);
                int value = Integer.parseInt(cell.getText().toString());
                operandList.add(value);
            }
        }
        Collections.shuffle(operandList);

        return operandList;
    }

    private void gameNotTrueEqual() {
        Toast.makeText(this, "Not True Operand!", Toast.LENGTH_SHORT).show();
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
            handlerJSON.updateRecord(getIntent().getStringExtra("json"), model, OperandFoundModel.class);
        }

        int currentLevel = model.getLevel();
        handlerJSON.unlockNextLevel(getIntent().getStringExtra("json"), currentLevel, OperandFoundModel.class);

        Toast.makeText(this, "Level Complete!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void adaptiveComponent(){
        HandlerAdaptive handlerAdaptive = new HandlerAdaptive(this);
        handlerAdaptive.setGamesHeaderComponentSize();
        handlerAdaptive.setGamesScoreSize();
    }

}
