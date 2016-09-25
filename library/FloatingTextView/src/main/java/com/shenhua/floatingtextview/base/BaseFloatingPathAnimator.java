package com.shenhua.floatingtextview.base;

import android.graphics.PathMeasure;

import com.shenhua.floatingtextview.Animator.ReboundFloatingAnimator;
import com.shenhua.floatingtextview.FloatingTextView;
import com.shenhua.floatingtextview.callback.FloatingPathAnimator;

/**
 * 路径动画器基类
 * Created by Shenhua on 9/15/2016.
 */
public abstract class BaseFloatingPathAnimator extends ReboundFloatingAnimator implements FloatingPathAnimator {

    private PathMeasure pathMeasure;
    private float pos[];

    public float[] getFloatingPosition(float progress) {
        pathMeasure.getPosTan(progress, pos, null);
        return pos;
    }

    @Override
    public void applyFloatingAnimation(FloatingTextView view) {
        pathMeasure = view.getPathMeasure();
        if (pathMeasure == null) {
            return;
        }
        pos = new float[2];
        applyFloatingPathAnimation(view, 0, pathMeasure.getLength());
    }

}
