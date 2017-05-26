package com.shenhua.nandagy.module.home.model;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.commonlibs.mvp.BasePresenter;

/**
 * 首页数据接口
 * Created by shenhua on 8/29/2016.
 */
public interface HomeModel<T> {

    void toGetHomeData(BasePresenter basePresenter, String url, HttpCallback<T> callback, int type);
}
