package com.shenhua.nandagy.widget.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * 知乎效果底部behavior 依赖于 AppBarLayout
 * Created by Shenhua on 10/6/2016.
 * E-mail shenhuanet@126.com
 */
public class FooterBehaviorDependAppBar extends CoordinatorLayout.Behavior<View> {

    public FooterBehaviorDependAppBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //当 dependency instanceof AppBarLayout 返回TRUE，将会调用onDependentViewChanged（）方法
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        //根据dependency top值的变化改变 child 的 translationY
        float translationY = Math.abs(dependency.getTop());
        child.setTranslationY(translationY);
        return true;

    }
}