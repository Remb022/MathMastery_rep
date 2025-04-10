package com.example.mathmastery_beta;

import android.os.Bundle;
import android.text.Html;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class InformationGameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_game);

        setFunctionalHeaderIcon();
        displayGameRules();
    }

    private void displayGameRules(){
        TextView gameName = findViewById(R.id.game_name);
        TextView rulesText = findViewById(R.id.rules_text);

        String key = getIntent().getStringExtra("content_key");

        switch (key) {
            case "rules_operand_game":
                gameName.setText(R.string.operand_found);
                rulesText.setText(Html.fromHtml(getString(R.string.rules_operand_game)));
                break;
            case "rules_operation_game":
                gameName.setText(R.string.operation_found);
                rulesText.setText(Html.fromHtml(getString(R.string.rules_operation_game)));
                break;
            case "rules_result_game":
                gameName.setText(R.string.result_found);
                rulesText.setText(Html.fromHtml(getString(R.string.rules_result_game)));
                break;
            case "rules_equal_game":
                gameName.setText(R.string.equal_found);
                rulesText.setText(Html.fromHtml(getString(R.string.rules_equal_game)));
                break;
            case "rules_2048_game":
                gameName.setText(R.string.mini_game_2048);
                rulesText.setText(Html.fromHtml(getString(R.string.rules_2048_game)));
                break;
            case "rules_question":
                gameName.setText(R.string.question_mark);
                rulesText.setText(Html.fromHtml(getString(R.string.rules_question)));
                break;
        }
    }

    private void setFunctionalHeaderIcon() {
        ImageButton functionalHeaderIcon = findViewById(R.id.functional_header_icon);
        functionalHeaderIcon.setOnClickListener(v -> finish());
    }
}
