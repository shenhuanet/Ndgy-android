package com.shenhua.nandagy.view;

import com.shenhua.commonlibs.mvp.BaseView;
import com.shenhua.nandagy.bean.JiaowuData;

/**
 * Created by Shenhua on 3/24/2017.
 * e-mail shenhuanet@126.com
 */
public interface JiaowuView extends BaseView {

    void updateList(JiaowuData data);
}
