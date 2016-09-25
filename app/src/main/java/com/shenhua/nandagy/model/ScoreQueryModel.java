package com.shenhua.nandagy.model;

import com.shenhua.nandagy.callback.HttpCallback;

/**
 * 查询成绩数据接口
 * Created by shenhua on 9/8/2016.
 */
public interface ScoreQueryModel<T> {

    void doQuery(String url, HttpCallback<T> callback);

}
