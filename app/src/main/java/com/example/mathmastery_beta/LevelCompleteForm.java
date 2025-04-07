package com.example.mathmastery_beta;

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

public class LevelCompleteForm {
    private final Context context;
    HandlerTimer handlerTimer;

    public LevelCompleteForm(Context context) {
        this.context = context;
    }

    public void showLevelCompleteDialog(int level, String currentTime, String mode) {
        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setGravity(Gravity.CENTER);
        container.setPadding(32, 32, 32, 32);

        TextView congratulation = new TextView(context);
        congratulation.setText("Поздравляем! Уровень " + level + " режима\n" + mode + " был пройден!");
        congratulation.setTextSize(18);
        congratulation.setTextColor(ContextCompat.getColor(context, R.color.yellow_gray));
        congratulation.setGravity(Gravity.CENTER);
        congratulation.setPadding(0, 0, 0, 24);
        container.addView(congratulation);

        TextView time = new TextView(context);
        time.setText(currentTime);
        time.setTextSize(35);
        time.setTextColor(ContextCompat.getColor(context, R.color.black));
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
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.round_dialog);
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

                TextView timer = ((Activity) context).findViewById(R.id.currentTime);
                handlerTimer = new HandlerTimer(timer);
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