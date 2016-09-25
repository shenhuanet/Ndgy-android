package com.shenhua.floatingtextview.effect;

import android.graphics.Path;

import com.shenhua.floatingtextview.base.FloatingPath;
import com.shenhua.floatingtextview.callback.FloatingPathEffect;
import com.shenhua.floatingtextview.FloatingTextView;

/**
 * 弧形效果
 * Created by Shenhua on 9/15/2016.
 */
public class CurveFloatingPathEffect implements FloatingPathEffect {

    @Override
    public FloatingPath getFloatingPath(FloatingTextView floatingTextView) {
        Path path = new Path();
        path.moveTo(0, 0);
        path.quadTo(-100, -200, 0, -300);
        path.quadTo(200, -400, 0, -500);
        return FloatingPath.create(path, false);
    }

}
