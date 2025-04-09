package com.example.mathmastery_beta;

import android.os.Bundle;
import android.widget.ImageButton;

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAvatar();
        loadNickname();
        updateProgress();
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
