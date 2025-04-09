package com.example.mathmastery_beta.aniimation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

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

    public void setPushFormAnimation(PopupWindow popupWindow, View popupView, Activity activity){
        new Handler().postDelayed(() -> {
            popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.TOP, 0, 0);
            popupView.setTranslationX(activity.getResources().getDisplayMetrics().widthPixels);

            popupView.animate()
                    .translationX(0)
                    .setDuration(500)
                    .withEndAction(() -> {
                        popupView.postDelayed(() -> {
                            popupView.animate()
                                    .translationY(-popupView.getHeight())
                                    .alpha(0f)
                                    .setDuration(500)
                                    .withEndAction(popupWindow::dismiss)
                                    .start();
                        }, 1500);
                    })
                    .start();

        }, 100);
    }

}