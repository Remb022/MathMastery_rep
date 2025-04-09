package com.example.mathmastery_beta.handlers;

import android.app.Activity;
import android.content.Context;

import com.example.mathmastery_beta.forms.AchievePushForm;

public class AchieveChecker {

    private final Context context;
    private final HandlerDataSave handlerDataSave;

    public AchieveChecker(Context context) {
        this.context = context;
        this.handlerDataSave = new HandlerDataSave(context);
    }

    public void checkLevelAchievements() {
        int userLevel = handlerDataSave.getUserLevel();

        switch (userLevel) {
            case 2:
                showAchievement("Повышение", "Достигнуть 2 уровня профиля");
                break;
            case 5:
                showAchievement("Шаг за шагом", "Достигнуть 5 уровня профиля");
                break;
            case 10:
                showAchievement("Жажда знаний", "Достигнуть 10 уровня профиля");
                break;
        }
    }

    private void showAchievement(String title, String description) {
        AchievePushForm form = new AchievePushForm((Activity) context);
        form.showAchievementPushForm(title, description);
    }

}
