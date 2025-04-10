package com.example.mathmastery_beta.handlers;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.example.mathmastery_beta.R;
import com.example.mathmastery_beta.forms.AchievePushForm;
import com.example.mathmastery_beta.level_status_model.EqualFoundModel;
import com.example.mathmastery_beta.level_status_model.LevelModel;
import com.example.mathmastery_beta.level_status_model.OperandFoundModel;
import com.example.mathmastery_beta.level_status_model.OperationFoundModel;
import com.example.mathmastery_beta.level_status_model.ResultFoundModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class AchieveChecker {

    private final Context context;
    private final HandlerDataSave handlerDataSave;
    private final HandlerJSON handlerJSON;

    public AchieveChecker(Context context) {
        this.context = context;
        this.handlerDataSave = new HandlerDataSave(context);
        this.handlerJSON = new HandlerJSON(context);
    }

    public void checkLevelAchievements() {
        levelUpCheck();
        levelLimitCheck();
    }

    private void levelUpCheck(){
        int userLevel = handlerDataSave.getUserLevel();
        if (userLevel >= 2 && !handlerDataSave.getAchieveStatus("levelUp1")) {
            showAchievement("Повышение", "Достигнуть 2 уровня профиля");
            handlerDataSave.saveAchieveStatus("levelUp1", true);
        }

        if (userLevel >= 5 &&!handlerDataSave.getAchieveStatus("levelUp2")) {
            showAchievement("Шаг за шагом", "Достигнуть 5 уровня профиля");
            handlerDataSave.saveAchieveStatus("levelUp2", true);
        }

        if (userLevel >= 10 && !handlerDataSave.getAchieveStatus("levelUp3")) {
            showAchievement("Жажда знаний", "Достигнуть 10 уровня профиля");
            handlerDataSave.saveAchieveStatus("levelUp3", true);
        }
    }

    private void levelLimitCheck() {
        checkMaxLevel("operand_found.json", OperandFoundModel.class);
        checkMaxLevel("operation_found.json", OperationFoundModel.class);
        checkMaxLevel("result_found.json", ResultFoundModel.class);
        checkMaxLevel("equal_found.json", EqualFoundModel.class);
    }

    private void checkMaxLevel(String jsonFileName, Class<?> model) {
        String jsonContent = handlerJSON.loadJSON(jsonFileName);

        Gson gson = new Gson();
        Type type = TypeToken.getParameterized(List.class, model).getType();
        List<LevelModel> levelList = gson.fromJson(jsonContent, type);

        int maxActiveLevel = 0;
        for (LevelModel level : levelList) {
            if ("active".equals(level.getStatus())) {
                maxActiveLevel = Math.max(maxActiveLevel, level.getLevel());
            }
        }

        if (maxActiveLevel > 1 && !handlerDataSave.getAchieveStatus("startGame")) {
            showAchievement("Начало пути", "Пройти любой уровень");
            handlerDataSave.saveAchieveStatus("startGame", true);
        }

        if (maxActiveLevel >= 10 && !handlerDataSave.getAchieveStatus("levelMode1")) {
            showAchievement("Подавайте следующий!", "Достигнуть 10 уровня в любом из режимов");
            handlerDataSave.saveAchieveStatus("levelMode1", true);
        }

        if (maxActiveLevel >= 20 && !handlerDataSave.getAchieveStatus("levelMode2")) {
            showAchievement("Достигнув конца…", "Достигнуть 20 уровня в любом из режимов");
            handlerDataSave.saveAchieveStatus("levelMode2", true);
        }
    }

    public void customProfileCheck(){
        boolean isNicknameCustom = !handlerDataSave.getNickname().equals("User_db0075");
        boolean isAvatarCustom = handlerDataSave.getAvatar() != null;

        if (isNicknameCustom && isAvatarCustom &&
                !handlerDataSave.getAchieveStatus("customProfile")) {

            showAchievement("Вы прекрасны!", "Сменить аватар и никнейм профиля");
            handlerDataSave.saveAchieveStatus("customProfile", true);
        }
    }

    private void showAchievement(String title, String description) {
        AchievePushForm form = new AchievePushForm((Activity) context);
        form.showAchievementPushForm(title, description);
    }

}
