package com.example.mathmastery_beta;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mathmastery_beta.level_status_model.HandlerJSON;
import com.example.mathmastery_beta.level_status_model.OperandFoundModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

public class OperandFoundActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operand_found);

        setFunctionalHeaderIcon();
        generateComponent();
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

    private void generateComponent() {
        HandlerJSON handlerJSON = new HandlerJSON(this);

        // update level header
        Intent intent = getIntent();
        TextView levelNumber = findViewById(R.id.levelNumber);
        int level = intent.getIntExtra("levelNumber", 0);
        levelNumber.setText(String.valueOf(level));

        // get level info from json
        String jsonPath = intent.getStringExtra("json");
        String json = handlerJSON.loadJSON(jsonPath);
        OperandFoundModel model = HandlerJSON.getJSONote(json, level, OperandFoundModel.class);

        // set record
        TextView bestTime = findViewById(R.id.bestTime);
        bestTime.setText(model.getRecord());

        // generate field
        TableLayout gameFieldBlock = findViewById(R.id.gameFieldBlock);

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int buttonSize = (screenWidth - 100) / model.getWidth();

        for (int row = 0; row < model.getHeight(); row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
            ));

            for (int col = 0; col < model.getWidth(); col++) {
                TextView label = new TextView(this);

                int randomValue = new Random().nextInt(model.getRangeMax() - model.getRangeMin() + 1) + model.getRangeMin();
                label.setText(String.valueOf(randomValue));

                label.setTextSize(30);
                label.setTypeface(null, Typeface.BOLD);
                label.setGravity(Gravity.CENTER);
                label.setBackgroundResource(R.drawable.cell_border_game);

                TableRow.LayoutParams params = new TableRow.LayoutParams(buttonSize, buttonSize);
                params.setMargins(5, 5, 5, 5);
                label.setLayoutParams(params);

                tableRow.addView(label);
            }

            gameFieldBlock.addView(tableRow);
        }
    }
}
