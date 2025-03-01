package com.example.mathmastery_beta.handlers;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mathmastery_beta.R;

public class HandlerAdaptive {
    private final Activity activity;

    public HandlerAdaptive(Activity activity) {
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
    public void setMainHeaderComponentSize() {
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

    // HEADER LEVEL BAR ADAPTIVE
    public void setLevelBarHeaderComponentSize() {
        setGameInfoSize();
        setFunctionalIconSize();
    }

    private void setGameInfoSize() {
        TextView gameInfo = activity.findViewById(R.id.game_info);

        float elementSize = getElementTextSize(35);
        gameInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, elementSize);
    }

    // HEADER GAMES ADAPTIVE
    public void setGamesHeaderComponentSize(){
        setLevelInfoBarSize();
        setFunctionalIconSize();
    }

    private void setLevelInfoBarSize() {
        TextView levelInfo = activity.findViewById(R.id.level_info);
        TextView levelNumber = activity.findViewById(R.id.levelNumber);

        float elementSize = getElementTextSize(35);
        levelInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, elementSize);
        levelNumber.setTextSize(TypedValue.COMPLEX_UNIT_PX, elementSize);
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

    // GAMES BODY ADAPTIVE
    public void setGamesScoreSize() {
        TextView timeText = activity.findViewById(R.id.time_text);
        TextView currentTime = activity.findViewById(R.id.currentTime);

        TextView bestTimeText = activity.findViewById(R.id.bestTime_text);
        TextView currentBestTime = activity.findViewById(R.id.bestTime);

        float elementCurrentSize = getElementTextSize(35);
        timeText.setTextSize(TypedValue.COMPLEX_UNIT_PX, elementCurrentSize);
        currentTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, elementCurrentSize);

        float elementSize = getElementTextSize(17);
        bestTimeText.setTextSize(TypedValue.COMPLEX_UNIT_PX, elementSize);
        currentBestTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, elementSize);
    }


    //FOOTER (MAIN && LEVEL BAR) ADAPTIVE
    public void setFooterTextSize() {
        TextView levelFooterText = activity.findViewById(R.id.level_footer_index);

        float elementSize = getElementTextSize(20);
        levelFooterText.setTextSize(TypedValue.COMPLEX_UNIT_PX, elementSize);
    }



    //ADAPTIVE EXAMPLE IN GAMES
    private int getSPFromRules(int textLength){
        if (textLength <= 6) {
            return 55;
        }
        else if (textLength <= 8) {
            return 50;
        }
        else if (textLength <= 10) {
            return 45;
        }
        else if (textLength <= 12) {
            return 40;
        }
        else if (textLength <= 14) {
            return 35;
        }
        else {
            return 30;
        }
    }

    public void setAdaptiveExample() {
        TextView num1 = activity.findViewById(R.id.num1);
        TextView operation = activity.findViewById(R.id.operation);
        TextView num2 = activity.findViewById(R.id.num2);
        TextView equal = activity.findViewById(R.id.equal);
        TextView result = activity.findViewById(R.id.result);

        String fullText = num1.getText().toString() +
                operation.getText().toString() +
                num2.getText().toString() +
                equal.getText().toString() +
                result.getText().toString();

        int fullTextLength = fullText.length();
        int textSizeSp = getSPFromRules(fullTextLength);

        num1.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);
        operation.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);
        num2.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);
        equal.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);
        result.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);
    }

    public void setAdaptiveEqualExample() {
        TextView num1 = activity.findViewById(R.id.num1);
        TextView operation = activity.findViewById(R.id.operation);
        TextView num2 = activity.findViewById(R.id.num2);

        String fullText = num1.getText().toString() +
                operation.getText().toString() +
                num2.getText().toString();

        int fullTextLength = fullText.length();
        int textSizeSp = getSPFromRules(fullTextLength);

        num1.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);
        operation.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);
        num2.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);
    }
}