package com.example.wct.util;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;

import static com.example.wct.util.ScreenUtils.convertDpToPixel;
import static com.example.wct.util.ScreenUtils.getScreenHeight;
import static com.example.wct.util.ScreenUtils.getScreenWidth;

public class AnimationUtil {

    public void animate(final CardView cv, int from, int to)
    {
        System.out.println("expandFilter height = " + to);
        ValueAnimator anim = ValueAnimator.ofInt(from, to);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = cv.getLayoutParams();
                layoutParams.height = val;
                cv.setLayoutParams(layoutParams);
            }
        });
        anim.setDuration(250);
        anim.start();
    }

    public void expandFilter(final CardView v, Context context)
    {
        ValueAnimator anim = ValueAnimator.ofInt((int) convertDpToPixel(50, context), getScreenWidth(context));
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.width = val;
                v.setLayoutParams(layoutParams);
            }
        });
        anim.setDuration(250);
        anim.start();

        anim = ValueAnimator.ofInt((int) convertDpToPixel(50, context), getScreenHeight(context));
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = val;
                v.setLayoutParams(layoutParams);
            }
        });
        anim.setDuration(250);
        anim.start();

        anim = ValueAnimator.ofInt((int) convertDpToPixel(25, context), 0);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                v.setRadius(val);
            }
        });
        anim.setDuration(500);
        anim.start();
    }

    public void shrinkFilter(final CardView v, Context context)
    {
        ValueAnimator anim = ValueAnimator.ofInt(getScreenWidth(context), (int) convertDpToPixel(50, context));
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.width = val;
                v.setLayoutParams(layoutParams);
            }
        });
        anim.setDuration(250);
        anim.start();

        anim = ValueAnimator.ofInt(getScreenHeight(context), (int) convertDpToPixel(50, context));
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = val;
                v.setLayoutParams(layoutParams);
            }
        });
        anim.setDuration(250);
        anim.start();

        anim = ValueAnimator.ofInt(0, (int) convertDpToPixel(25, context));
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                v.setRadius(val);
            }
        });
        anim.setDuration(250);
        anim.start();
    }
}
