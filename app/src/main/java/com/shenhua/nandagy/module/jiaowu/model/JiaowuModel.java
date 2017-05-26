package com.shenhua.nandagy.module.jiaowu.model;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.commonlibs.mvp.BasePresenter;

/**
 * 教务处数据接口
 * Created by Shenhua on 3/24/2017.
 * e-mail shenhuanet@126.com
 */
public interface JiaowuModel<T> {

    void getJiaowuDatas(BasePresenter basePresenter, String url, HttpCallback<T> callback);
}
