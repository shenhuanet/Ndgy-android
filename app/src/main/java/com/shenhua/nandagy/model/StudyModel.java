package com.shenhua.nandagy.model;

import com.shenhua.nandagy.callback.HttpCallback;

/**
 * Created by Shenhua on 2/9/2017.
 * e-mail shenhuanet@126.com
 */
public interface StudyModel<T> {

    void toGetList(int type, HttpCallback<T> callback);

    void toGetDetail(String url, HttpCallback<T> callback);
}
