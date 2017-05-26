package com.shenhua.nandagy.module.xuegong.model;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.commonlibs.mvp.BasePresenter;

/**
 * 学工处数据接口
 * Created by shenhua on 8/31/2016.
 */
public interface XueGongModel<T> {

    void toGetXueGongData(BasePresenter basePresenter, String url, HttpCallback<T> callback);
}
