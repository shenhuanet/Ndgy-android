package com.shenhua.nandagy.callback;

import com.shenhua.commonlibs.callback.HttpCallback;

/**
 * Created by shenhua on 5/15/2017.
 * Email shenhuanet@126.com
 */
public interface LostFoundLoaderCallback<T> extends HttpCallback {

    void onSuccess(T t, int type);
}
