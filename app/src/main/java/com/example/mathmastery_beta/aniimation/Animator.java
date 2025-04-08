package com.example.mathmastery_beta.aniimation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.TextView;

public class Animator {

    public void textUpDownAnimation(TextView textView, long delay) {
        textView.animate().cancel();
        textView.setTranslationY(-100);
        textView.setAlpha(0);

        ObjectAnimator translationY = ObjectAnimator.ofFloat(textView, "translationY", -100, 0);
        translationY.setDuration(500);
        translationY.setStartDelay(delay);

        ObjectAnimator alpha = ObjectAnimator.ofFloat(textView, "alpha", 0, 1);
        alpha.setDuration(500);
        alpha.setStartDelay(delay);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translationY, alpha);
        animatorSet.start();
    }

    public void setUnlockLevelAnimation(View view) {
        ObjectAnimator rotateLeft = ObjectAnimator.ofFloat(view, "rotation", 0f, -4f);
        rotateLeft.setDuration(100);

        ObjectAnimator rotateRight = ObjectAnimator.ofFloat(view, "rotation", 0f, 4f);
        rotateRight.setDuration(100);

        ObjectAnimator rotateBackLeft = ObjectAnimator.ofFloat(view, "rotation", -4f, 0f);
        rotateBackLeft.setDuration(100);

        ObjectAnimator rotateBackRight = ObjectAnimator.ofFloat(view, "rotation", 4f, 0f);
        rotateBackRight.setDuration(100);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(rotateLeft, rotateBackLeft, rotateRight, rotateBackRight);
        animatorSet.start();
    }

}
