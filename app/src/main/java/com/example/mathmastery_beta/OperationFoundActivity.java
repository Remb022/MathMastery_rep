package com.example.mathmastery_beta;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mathmastery_beta.level_status_model.HandlerJSON;
import com.example.mathmastery_beta.level_status_model.OperationFoundModel;

public class OperationFoundActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_found);

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
        OperationFoundModel model = HandlerJSON.getJSONote(json, level, OperationFoundModel.class);

        // set record
        TextView bestTime = findViewById(R.id.bestTime);
        bestTime.setText(model.getRecord());

        // generative field
        TableLayout gameFieldBlock = findViewById(R.id.gameFieldBlock);

        int width = 2;
        int height = 2;
        String[] content = {"+", "-", "*", "/"};

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int buttonSize = (screenWidth - 100) / width;

        int contentIndex = 0;
        for (int row = 0; row < height; row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
            ));

            for (int col = 0; col < width; col++) {
                TextView label = new TextView(this);
                Resources resources = getResources();
                int color = resources.getColor(R.color.gray);
                label.setText(content[contentIndex]);
                contentIndex++;

                label.setTextSize(30);
                label.setTextColor(color);
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
