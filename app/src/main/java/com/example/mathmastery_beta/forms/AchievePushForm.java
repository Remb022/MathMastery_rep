package com.example.mathmastery_beta.forms;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.mathmastery_beta.R;
import com.example.mathmastery_beta.aniimation.Animator;

public class AchievePushForm {

    private final Activity activity;

    public AchievePushForm(Activity activity) {
        this.activity = activity;
    }

    public void showAchievementPushForm(String title, String description) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.achievement_push_form, null);

        ImageView icon = popupView.findViewById(R.id.achievement_icon);
        TextView titleTextView = popupView.findViewById(R.id.achievement_title);
        TextView descriptionTextView = popupView.findViewById(R.id.achievement_description);

        @SuppressLint("UseCompatLoadingForDrawables")
        Drawable iconDrawable = activity.getResources().getDrawable(R.drawable.icon_achieve_complete);
        icon.setImageDrawable(iconDrawable);

        titleTextView.setText(title);
        descriptionTextView.setText(description);

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        Animator animator = new Animator();
        animator.setPushFormAnimation(popupWindow, popupView, activity);
    }

}
