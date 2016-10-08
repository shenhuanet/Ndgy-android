package com.shenhua.nandagy.model;

import com.shenhua.nandagy.callback.HttpCallback;

/**
 * Created by MVPHelper on 2016/10/06
 */
public interface CircleModel<T> {

    void toGetCircleData(HttpCallback<T> callback);
}