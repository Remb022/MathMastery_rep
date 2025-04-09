package com.example.mathmastery_beta;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.mathmastery_beta.handlers.HandlerAdaptive;
import com.example.mathmastery_beta.handlers.HandlerHeaderEvents;
import com.example.mathmastery_beta.utils.DeepLinkHandler;

public class AchieveActivity extends HandlerHeaderEvents {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achieve);

        DeepLinkHandler.handleDeepLink(this);

        setNicknameFormListener();
        setChangeAvatarListener();

        setFunctionalHeaderIcon();
        loadNickname();
        loadAvatar();
        adaptiveComponent();

        checkAchieveList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAvatar();
        loadNickname();
        updateProgress();
    }

    private void checkAchieveList() {
        updateIcon(R.id.achievement_start_icon, "startGame");

        updateIcon(R.id.achievement_levelMode1_icon, "levelMode1");
        updateIcon(R.id.achievement_levelMode2_icon, "levelMode2");

        updateIcon(R.id.achievement_levelUp1_icon, "levelUp1");
        updateIcon(R.id.achievement_levelUp2_icon, "levelUp2");
        updateIcon(R.id.achievement_levelUp3_icon, "levelUp3");

        updateIcon(R.id.achievement_custom_icon, "customProfile");
        updateIcon(R.id.achievement_secret_icon, "secret");
    }

    private void updateIcon(int id, String key) {
        boolean isCompleted = handlerDataSave.getAchieveStatus(key);
        ImageView icon = findViewById(id);
        icon.setImageResource(isCompleted ? R.drawable.icon_achieve_complete : R.drawable.icon_achieve_uncomplete);
    }

    private void setFunctionalHeaderIcon() {
        ImageButton functionalHeaderIcon = findViewById(R.id.functional_header_icon);
        functionalHeaderIcon.setOnClickListener(v -> finish());
    }

    private void adaptiveComponent() {
        HandlerAdaptive handlerAdaptive = new HandlerAdaptive(this);
        handlerAdaptive.setMainHeaderComponentSize();
        handlerAdaptive.setFooterTextSize();
    }

}
