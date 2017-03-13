package com.shenhua.nandagy.model;

import android.content.Context;

import com.shenhua.commonlibs.callback.HttpCallback;

/**
 * 首页数据接口
 * Created by shenhua on 8/29/2016.
 */
public interface HomeModel<T> {

    void toGetHomeData(Context context, String url, HttpCallback<T> callback);
}
