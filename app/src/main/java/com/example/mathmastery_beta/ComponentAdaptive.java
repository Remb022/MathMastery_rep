package com.example.mathmastery_beta;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class ComponentAdaptive {
    private final Activity activity;

    public ComponentAdaptive(Activity activity) {
        this.activity = activity;
    }

    private int getScreenWidthPixels() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    // HEADER ADAPTIVE
    public void setHeaderComponentSize() {
        setAvatarIconSize();
        setNicknameInfoSize();
        setLevelInfoSize();
        setFunctionalIconSize();
    }

    private void setAvatarIconSize() {
        ImageButton avatarHeaderIcon = activity.findViewById(R.id.avatar_header_icon);

        int screenWidth = getScreenWidthPixels();
        int elementSize = screenWidth / 7;

        ViewGroup.LayoutParams params = avatarHeaderIcon.getLayoutParams();
        params.width = elementSize;
        params.height = elementSize;
        avatarHeaderIcon.setLayoutParams(params);
    }

    private void setNicknameInfoSize() {
        TextView nicknameHeaderInfo = activity.findViewById(R.id.nickname_header_info);

        int screenWidth = getScreenWidthPixels();
        int elementSize = screenWidth / 45;
        nicknameHeaderInfo.setTextSize(TypedValue.COMPLEX_UNIT_SP, elementSize);
    }

    private void setLevelInfoSize() {
        TextView levelHeaderInfo = activity.findViewById(R.id.level_header_info);

        int screenWidth = getScreenWidthPixels();
        int elementSize = screenWidth / 55;
        levelHeaderInfo.setTextSize(TypedValue.COMPLEX_UNIT_SP, elementSize);
    }

    private void setFunctionalIconSize() {
        ImageButton functionalHeaderIcon = activity.findViewById(R.id.functional_header_icon);

        int screenWidth = getScreenWidthPixels();
        int elementSize = screenWidth / 10;

        ViewGroup.LayoutParams params = functionalHeaderIcon.getLayoutParams();
        params.width = elementSize;
        params.height = elementSize;
        functionalHeaderIcon.setLayoutParams(params);
    }

    // MAIN PAGE BODY ADAPTIVE
    public void setMainPageComponentSize(){
        setLevelCardInfoSize();
        setLevelCardScoreSize();
    }

    private void setLevelCardInfoSize(){
        int[] levelCardInfoId = {
                R.id.level_card_info_1,
                R.id.level_card_info_2,
                R.id.level_card_info_3,
                R.id.level_card_info_4,
                R.id.level_card_info_5,
                R.id.level_card_info_6
        };

        int screenWidth = getScreenWidthPixels();
        int elementSize = screenWidth / 24;

        for (int id : levelCardInfoId) {
            TextView levelCardInfo = activity.findViewById(id);
            levelCardInfo.setTextSize(TypedValue.COMPLEX_UNIT_SP, elementSize);
        }
    }

    private void setLevelCardScoreSize(){
        int[] levelCardScoreId = {
                R.id.level_card_score_1,
                R.id.level_card_score_2,
                R.id.level_card_score_3,
                R.id.level_card_score_4,
                R.id.level_card_score_5,
                R.id.level_card_score_6
        };

        int screenWidth = getScreenWidthPixels();
        int elementSize = screenWidth / 48;

        for (int id : levelCardScoreId) {
            TextView levelCardInfo = activity.findViewById(id);
            levelCardInfo.setTextSize(TypedValue.COMPLEX_UNIT_SP, elementSize);
        }
    }

    //FOOTER ADAPTIVE
    public void setFooterTextSize() {
        TextView levelFooterText = activity.findViewById(R.id.level_footer_text);
        TextView levelFooterScore = activity.findViewById(R.id.level_footer_score);

        int screenWidth = getScreenWidthPixels();
        int elementSize = screenWidth / 36;
        levelFooterText.setTextSize(TypedValue.COMPLEX_UNIT_SP, elementSize);
        levelFooterScore.setTextSize(TypedValue.COMPLEX_UNIT_SP, elementSize);
    }
}