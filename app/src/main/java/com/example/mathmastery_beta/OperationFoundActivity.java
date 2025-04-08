package com.example.mathmastery_beta;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mathmastery_beta.aniimation.Animator;
import com.example.mathmastery_beta.forms.LevelCompleteForm;
import com.example.mathmastery_beta.handlers.HandlerAdaptive;
import com.example.mathmastery_beta.handlers.HandlerCalculate;
import com.example.mathmastery_beta.handlers.HandlerDataSave;
import com.example.mathmastery_beta.handlers.HandlerTimer;
import com.example.mathmastery_beta.handlers.HandlerJSON;
import com.example.mathmastery_beta.level_status_model.OperationFoundModel;

import java.text.DecimalFormat;
import java.util.Random;

public class OperationFoundActivity extends AppCompatActivity {

    private TextView num1;
    private TextView operation;
    private TextView num2;
    private TextView equal;
    private TextView result;
    private TextView levelNumber;
    private TextView bestTime;
    private TableLayout gameFieldBlock;
    private TextView timer;
    private ImageButton functionalHeaderIcon;

    HandlerTimer handlerTimer;
    HandlerCalculate calculator = new HandlerCalculate();
    HandlerJSON handlerJSON = new HandlerJSON(this);
    HandlerDataSave handlerDataSave = new HandlerDataSave(this);
    Animator animator = new Animator();
    OperationFoundModel model = new OperationFoundModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_found);

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
        equal = findViewById(R.id.equal);
        result = findViewById(R.id.result);
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
        model = HandlerJSON.getJSONote(json, level, OperationFoundModel.class);

        bestTime.setText(model.getRecord());

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

                label.setOnClickListener(v -> {
                    operation.setText(label.getText().toString());
                    new Handler(Looper.getMainLooper()).postDelayed(() -> operation.setText("?"), 500);
                    gameProcessClick(label);
                });

                tableRow.addView(label);
            }

            gameFieldBlock.addView(tableRow);
        }
    }

    private int count = 0;
    private void gameProcessClick(TextView clickedLabel){
        View gradientBottomGreen = findViewById(R.id.gradient_bottom_green);
        View gradientTopGreen = findViewById(R.id.gradient_top_green);

        LinearLayout linearLayout = findViewById(R.id.descriptionLinearLayout);

        int numToInt1 = Integer.parseInt(num1.getText().toString());
        int numToInt2 = Integer.parseInt(num2.getText().toString());
        String res = result.getText().toString();
        String op = clickedLabel.getText().toString();

        double resultValue = calculator.calculateResult(numToInt1, numToInt2, op);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String decFormatResult = decimalFormat.format(resultValue);

        if (decFormatResult.equals(res)) {
            count++;
            if (count < model.getCount()) {
                new Handler(Looper.getMainLooper()).postDelayed(this::generateExample, 500);
                //вертикальная тряска
                MyAnimation.shake_vertical(linearLayout);
                //подсветка градиента
                MyAnimation.glowEffect(gradientBottomGreen);
                MyAnimation.glowEffect(gradientTopGreen);
                //изменение цвета каждого числа
                MyAnimation.changeTextColor(num1, R.color.yellow_gray, R.color.green, 600);
                MyAnimation.changeTextColor(operation, R.color.yellow_gray, R.color.green, 600);
                MyAnimation.changeTextColor(num2, R.color.yellow_gray, R.color.green, 600);
                MyAnimation.changeTextColor(equal, R.color.yellow_gray, R.color.green, 600);
                MyAnimation.changeTextColor(result, R.color.yellow_gray, R.color.green, 600);

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

        num1.setText(String.valueOf(randomNum1));
        num2.setText(String.valueOf(randomNum2));
        result.setText(decFormatResult);

        HandlerAdaptive handlerAdaptive = new HandlerAdaptive(this);
        handlerAdaptive.setAdaptiveExample();
    }

    private void gameNotTrueEqual() {
        // penalty time animation
        handlerTimer.addFineTime();
        View gradientBottomRed = findViewById(R.id.gradient_bottom_red);
        View gradientTopRed = findViewById(R.id.gradient_top_red);

        TextView num1 = findViewById(R.id.num1);
        TextView operation = findViewById(R.id.operation);
        TextView num2 = findViewById(R.id.num2);
        TextView equal = findViewById(R.id.equal);
        TextView result = findViewById(R.id.result);

        LinearLayout linearLayout = findViewById(R.id.descriptionLinearLayout);

        MyAnimation.glowEffect(gradientBottomRed);
        MyAnimation.glowEffect(gradientTopRed);

        MyAnimation.changeTextColor(num1, R.color.yellow_gray, R.color.red, 600);
        MyAnimation.changeTextColor(operation, R.color.yellow_gray, R.color.red, 600);
        MyAnimation.changeTextColor(num2, R.color.yellow_gray, R.color.red, 600);
        MyAnimation.changeTextColor(equal, R.color.yellow_gray, R.color.red, 600);
        MyAnimation.changeTextColor(result, R.color.yellow_gray, R.color.red, 600);

        MyAnimation.shake_horizontal(linearLayout);
    }

    private void gameEnd() {
        String currentRecord = timer.getText().toString();

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

        LevelCompleteForm levelCompleteForm = new LevelCompleteForm(this, handlerTimer);
        levelCompleteForm.showLevelCompleteDialog(model.getLevel(), currentRecord);
    }

    private void adaptiveComponent(){
        HandlerAdaptive handlerAdaptive = new HandlerAdaptive(this);
        handlerAdaptive.setGamesHeaderComponentSize();
        handlerAdaptive.setGamesScoreSize();
    }

}