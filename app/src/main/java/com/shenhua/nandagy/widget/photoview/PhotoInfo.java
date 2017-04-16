package com.shenhua.nandagy.widget.photoview;

import android.graphics.PointF;
import android.graphics.RectF;
import android.widget.ImageView;

/**
 * Created by shenhua on 4/13/2017.
 * Email shenhuanet@126.com
 */
public class PhotoInfo {

    // 内部图片在整个手机界面的位置
    RectF mRect = new RectF();
    // 控件在窗口的位置
    RectF mImgRect = new RectF();

    RectF mWidgetRect = new RectF();

    private RectF mBaseRect = new RectF();

    private PointF mScreenCenter = new PointF();

    private float mScale;

    float mDegrees;

    ImageView.ScaleType mScaleType;

    public PhotoInfo(RectF rect, RectF img, RectF widget, RectF base, PointF screenCenter, float scale, float degrees, ImageView.ScaleType scaleType) {
        mRect.set(rect);
        mImgRect.set(img);
        mWidgetRect.set(widget);
        mScale = scale;
        mScaleType = scaleType;
        mDegrees = degrees;
        mBaseRect.set(base);
        mScreenCenter.set(screenCenter);
    }
}
