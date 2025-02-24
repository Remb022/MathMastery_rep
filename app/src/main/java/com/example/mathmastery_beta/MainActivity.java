package com.example.mathmastery_beta;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.mathmastery_beta.level_status_model.EqualFoundModel;
import com.example.mathmastery_beta.level_status_model.OperandFoundModel;
import com.example.mathmastery_beta.level_status_model.OperationFoundModel;
import com.example.mathmastery_beta.level_status_model.ResultFoundModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        setFunctionalHeaderIcon();

    }

    private void startLevelBarActivity(String jsonFileName, Class<?> model) {
        Intent intent = new Intent(MainActivity.this, LevelBarActivity.class);
        intent.putExtra("json", jsonFileName);
        intent.putExtra("model", model.getName());
        startActivity(intent);
    }

    private void setFunctionalHeaderIcon() {
        ImageButton functionalHeaderIcon = findViewById(R.id.functional_header_icon);
        functionalHeaderIcon.setImageResource(R.drawable.icon_settings);
    }

}