package com.example.mathmastery_beta;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.mathmastery_beta.level_status_model.EqualFoundModel;
import com.example.mathmastery_beta.level_status_model.OperandFoundModel;
import com.example.mathmastery_beta.level_status_model.OperationFoundModel;
import com.example.mathmastery_beta.level_status_model.ResultFoundModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardView operandFoundGame = findViewById(R.id.operand_found_game);
        CardView operationFoundGame = findViewById(R.id.operation_found_game);
        CardView resultFoundGame = findViewById(R.id.result_found_game);
        CardView equalFoundGame = findViewById(R.id.equal_found_game);
        CardView miniGame2048 = findViewById(R.id.mini_game_2048);

        operandFoundGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLevelBarActivity("level_progress_folder/operand_found.json",
                        OperandFoundModel.class);
            }
        });

        operationFoundGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLevelBarActivity("level_progress_folder/operation_found.json",
                        OperationFoundModel.class);
            }
        });

        resultFoundGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLevelBarActivity("level_progress_folder/result_found.json",
                        ResultFoundModel.class);
            }
        });

        equalFoundGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLevelBarActivity("level_progress_folder/equal_found.json",
                        EqualFoundModel.class);
            }
        });
        miniGame2048.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), com.kynzai.game2048.MainActivity.class);

                //Проверяется есть ли активность или нет
                PackageManager packageManager = v.getContext().getPackageManager();
                List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);

                if (!activities.isEmpty()) {
                    v.getContext().startActivity(intent);

                    Toast.makeText(v.getContext(), "Активность найдена", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(v.getContext(), "Активность не найдена", Toast.LENGTH_SHORT).show();
                }
            }
        });



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