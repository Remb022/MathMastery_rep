package com.example.mathmastery_beta.aniimation;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;

import com.example.mathmastery_beta.R;

public class MyAnimation {
    //горизонтальная анимация
    public static void shake_horizontal(LinearLayout text) {
        android.view.animation.Animation shake = AnimationUtils.loadAnimation(text.getContext(), R.anim.shake_horizontal);
        text.startAnimation(shake);
    }

    //вертикальная анимация
    public static void shake_vertical(LinearLayout text) {
        Context context = text.getContext();

        android.view.animation.Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake_vertical);
        text.startAnimation(shake);
    }

    public static void changeTextColor(TextView text, int textColorOriginal, int textColorChange, int animationDuration) {
        // Устанавливаем цвет сразу
        text.setTextColor(ContextCompat.getColor(text.getContext(), textColorChange));

        // Задержка перед началом анимации возврата
        new Handler().postDelayed(() -> {
            int colorFrom = ContextCompat.getColor(text.getContext(), textColorChange);
            int colorTo = ContextCompat.getColor(text.getContext(), textColorOriginal);

            ValueAnimator colorAnim = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
            colorAnim.setDuration(200); // Длительность плавного возврата (можно изменить)

            colorAnim.addUpdateListener(animator -> {
                text.setTextColor((int) animator.getAnimatedValue());
            });

            colorAnim.start();
        }, animationDuration); // Задержка до начала возврата
    }

    //анимация свечения по краям
    public static void glowEffect(View view) {

        view.setVisibility(View.VISIBLE);

        AlphaAnimation glowIn = new AlphaAnimation(0f, 1f);
        glowIn.setDuration(200); // появление
        glowIn.setFillAfter(true);

        AlphaAnimation glowOut = new AlphaAnimation(1f, 0f);
        glowOut.setStartOffset(600); // задержка свечения
        glowOut.setDuration(200);
        glowOut.setFillAfter(true);

        AnimationSet glowAnimation = new AnimationSet(true);
        glowAnimation.addAnimation(glowIn);
        glowAnimation.addAnimation(glowOut);

        glowAnimation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {}

            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            public void onAnimationRepeat(Animation animation) {}
        });

        view.startAnimation(glowAnimation);
    }
}
