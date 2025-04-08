package com.example.mathmastery_beta.aniimation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

public class Animator {

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
