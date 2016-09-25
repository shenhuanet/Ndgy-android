package com.shenhua.nandagy.model;

import com.shenhua.nandagy.callback.HttpCallback;

/**
 * 学工处数据接口
 * Created by shenhua on 8/31/2016.
 */
public interface XueGongModel<T> {

    void toGetXueGongData(String url, HttpCallback<T> callback);
}
