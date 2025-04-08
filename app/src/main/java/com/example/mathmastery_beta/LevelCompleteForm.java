package com.example.mathmastery_beta;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.example.mathmastery_beta.handlers.HandlerTimer;

import java.util.Objects;

public class LevelCompleteForm {
    private final Context context;
    HandlerTimer handlerTimer;

    public LevelCompleteForm(Context context, HandlerTimer handlerTimer) {
        this.context = context;
        this.handlerTimer = handlerTimer;
    }

    @SuppressLint("SetTextI18n")
    public void showLevelCompleteDialog(int level, String currentTime) {
        handlerTimer.stopTimer();

        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setGravity(Gravity.CENTER);
        container.setPadding(32, 32, 32, 32);

        TextView congratulation = new TextView(context);
        congratulation.setText("Уровень " + level);
        congratulation.setTextSize(35);
        congratulation.setTextColor(ContextCompat.getColor(context, R.color.black));
        congratulation.setGravity(Gravity.CENTER);
        container.addView(congratulation);

        TextView congratulationD = new TextView(context);
        congratulationD.setText("пройден!");
        congratulationD.setTextSize(20);
        congratulationD.setTextColor(ContextCompat.getColor(context, R.color.black));
        congratulationD.setGravity(Gravity.CENTER);
        congratulationD.setPadding(0, 24, 0, 0);
        container.addView(congratulationD);

        TextView time = new TextView(context);
        time.setText(currentTime);
        time.setTextSize(35);
        time.setTextColor(ContextCompat.getColor(context, R.color.yellow_gray));
        time.setGravity(Gravity.CENTER);
        time.setPadding(0, 64, 0, 64);
        container.addView(time);

        LayoutInflater inflater = LayoutInflater.from(context);
        View buttonsView = inflater.inflate(R.layout.level_complete_form_button_layout, null);
        container.addView(buttonsView);

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setView(container)
                .setCancelable(false);

        AlertDialog dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.drawable.round_dialog);
        dialog.show();

        // events
        ImageButton retry = buttonsView.findViewById(R.id.retry_button);
        ImageButton confirm = buttonsView.findViewById(R.id.confirm_button);

        retry.setOnClickListener(v -> {
            dialog.dismiss();
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                Intent intent = activity.getIntent();
                activity.finish();
                activity.startActivity(intent);
                activity.overridePendingTransition(0, 0);

                handlerTimer.restartTimer();
            }
        });

        confirm.setOnClickListener(v -> {
            dialog.dismiss();
            if (context instanceof Activity) {
                ((Activity) context).finish();
            }
        });

    }

}