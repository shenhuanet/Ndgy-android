package com.shenhua.nandagy.model;

import android.content.Context;

import com.shenhua.commonlibs.callback.HttpCallback;

/**
 * Created by Shenhua on 2/9/2017.
 * e-mail shenhuanet@126.com
 */
public interface StudyModel<T> {

    void toGetList(Context context, int type, HttpCallback<T> callback);

    void toGetDetail(Context context, int type, int position, String url, HttpCallback<T> callback);
}
