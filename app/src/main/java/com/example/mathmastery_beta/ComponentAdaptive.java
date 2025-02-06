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

    private int getScreenXDPI() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return (int)displayMetrics.xdpi;
    }

    private float getElementTextSize(float baseSize){
        float xdpi = getScreenXDPI();
        return baseSize * (xdpi / 160f);
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

        int baseSizeDp = 60;
        int elementSize = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                baseSizeDp,
                activity.getResources().getDisplayMetrics()
        );

        ViewGroup.LayoutParams params = avatarHeaderIcon.getLayoutParams();
        params.width = elementSize;
        params.height = elementSize;
        avatarHeaderIcon.setLayoutParams(params);
    }

    private void setNicknameInfoSize() {
        TextView nicknameHeaderInfo = activity.findViewById(R.id.nickname_header_info);

        float elementSize = getElementTextSize(18);
        nicknameHeaderInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, elementSize);
    }

    private void setLevelInfoSize() {
        TextView levelHeaderInfo = activity.findViewById(R.id.level_header_info);

        float elementSize = getElementTextSize(15);
        levelHeaderInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, elementSize);
    }

    private void setFunctionalIconSize() {
        ImageButton functionalHeaderIcon = activity.findViewById(R.id.functional_header_icon);

        int baseSizeDp = 35;
        int elementSize = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                baseSizeDp,
                activity.getResources().getDisplayMetrics()
        );

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

        float elementSize = getElementTextSize(30);
        for (int id : levelCardInfoId) {
            TextView levelCardInfo = activity.findViewById(id);
            levelCardInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, elementSize);
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

        float elementSize = getElementTextSize(18);
        for (int id : levelCardScoreId) {
            TextView levelCardInfo = activity.findViewById(id);
            levelCardInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, elementSize);
        }
    }

    //FOOTER ADAPTIVE
    public void setFooterTextSize() {
        TextView levelFooterText = activity.findViewById(R.id.level_footer_text);
        TextView levelFooterScore = activity.findViewById(R.id.level_footer_score);

        float elementSize = getElementTextSize(20);
        levelFooterText.setTextSize(TypedValue.COMPLEX_UNIT_PX, elementSize);
        levelFooterScore.setTextSize(TypedValue.COMPLEX_UNIT_PX, elementSize);
    }
}