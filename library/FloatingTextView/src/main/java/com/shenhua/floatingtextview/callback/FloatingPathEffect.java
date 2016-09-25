package com.shenhua.floatingtextview.callback;

import com.shenhua.floatingtextview.base.FloatingPath;
import com.shenhua.floatingtextview.FloatingTextView;

/**
 * 路径效果接口
 * Created by Shenhua on 9/15/2016.
 */
public interface FloatingPathEffect {

    FloatingPath getFloatingPath(FloatingTextView floatingTextView);

}
