package com.example.mathmastery_beta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.example.mathmastery_beta.adaptor.LevelPagerAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);

        setFunctionalHeaderIcon();
        ViewPager2 viewPager = findViewById(R.id.level_list_pager);

        // Разделение уровней на страницы
        List<List<Integer>> levelPages = new ArrayList<>();
        levelPages.add(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)); // Первая страница
        levelPages.add(Arrays.asList(10, 11, 12, 13, 14, 15, 16, 17, 18)); // Вторая страница
        levelPages.add(Arrays.asList(19, 20, 21, 22, 23, 24, 25, 26, 27)); // Третья страница

        // Установка адаптера
        LevelPagerAdapter adapter = new LevelPagerAdapter(levelPages, this);
        viewPager.setAdapter(adapter);
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

}