package com.shenhua.nandagy.model;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.commonlibs.mvp.BasePresenter;

/**
 * 正文详情模型
 * Created by Shenhua on 3/23/2017.
 * e-mail shenhuanet@126.com
 */
public interface ContentDetailModel<T> {

    void getDetail(BasePresenter basePresenter, String url, HttpCallback<T> callback);
}
