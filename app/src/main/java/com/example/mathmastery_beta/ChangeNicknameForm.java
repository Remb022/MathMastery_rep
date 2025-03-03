package com.example.mathmastery_beta;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.example.mathmastery_beta.handlers.HandlerDataSave;

public class ChangeNicknameForm {
    private final Context context;
    private final HandlerDataSave handlerDataSave;

    public ChangeNicknameForm(Context context, HandlerDataSave handlerDataSave) {
        this.context = context;
        this.handlerDataSave = handlerDataSave;
    }

    public void showEditNicknameDialog(TextView nicknameTextView) {
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(nicknameTextView.getText());

        input.setBackgroundResource(R.drawable.edit_form_border);
        input.setPadding(16, 16, 16, 16);

        input.setTextColor(ContextCompat.getColor(context,R.color.light_gray));
        input.setTextColor(ContextCompat.getColor(context,R.color.light_gray));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            input.setTextCursorDrawable(R.drawable.edit_form_selected_cursor);
        }

        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(45, 32, 45, 0); // Отступы по бокам
        input.setLayoutParams(params);
        container.addView(input);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Изменить никнейм");
        builder.setView(container);
        builder.setPositiveButton("Сохранить", (dialog, which) -> {
            String newNickname = input.getText().toString().trim();
            if(newNickname.length() >= 4 && newNickname.length() <= 15) {
                nicknameTextView.setText(newNickname);
                handlerDataSave.saveNickname(newNickname);
            }
            else {
                Toast.makeText(context, "Nick Not in Range 4-15", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.round_dialog); //Скругление углов диалогового окна
        dialog.show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        if (positiveButton != null) {
            positiveButton.setTextColor(ContextCompat.getColor(context, R.color.yellow));//Поменял цвет
        }

        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        if (negativeButton != null) {
            negativeButton.setTextColor(ContextCompat.getColor(context, R.color.yellow));//Поменял цвет
        }
    }

}
