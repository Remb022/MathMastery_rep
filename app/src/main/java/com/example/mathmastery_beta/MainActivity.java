package com.example.mathmastery_beta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFunctionalHeaderIcon();
        CardView cardFirstGame = findViewById(R.id.card_game_1);

        cardFirstGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        ComponentAdaptive componentAdaptive = new ComponentAdaptive(this);
        componentAdaptive.setHeaderComponentSize();
        componentAdaptive.setMainPageComponentSize();
        componentAdaptive.setFooterTextSize();
    }

    private void setFunctionalHeaderIcon() {
        ImageButton functionalHeaderIcon = findViewById(R.id.functional_header_icon);
        functionalHeaderIcon.setImageResource(R.drawable.icon_settings);
    }

}