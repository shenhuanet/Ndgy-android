package com.shenhua.nandagy.model;

import android.util.Log;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.commonlibs.mvp.ApiCallback;
import com.shenhua.commonlibs.mvp.BasePresenter;
import com.shenhua.nandagy.App;
import com.shenhua.nandagy.bean.HomeData;

import java.util.List;

/**
 * 首页数据实现者
 * Created by shenhua on 8/29/2016.
 */
public class HomeModelImpl implements HomeModel<List<HomeData>> {

    private static final String TAG = "HomeModelImpl";

    @Override
    public void toGetHomeData(BasePresenter basePresenter, final String url, final HttpCallback<List<HomeData>> callback) {
        basePresenter.addSubscription(com.shenhua.commonlibs.mvp.HttpManager.getInstance()
                .createHtmlGetObservable(App.getContext(), url, "gb2312"), new ApiCallback<String>() {
            @Override
            public void onPreExecute() {
                callback.onPreRequest();
            }

            @Override
            public void onSuccess(String o) {
                Log.d(TAG, "onSuccess: " + o);
                callback.onSuccess(null);
            }

            @Override
            public void onFailure(String s) {
                callback.onError(s);
            }

            @Override
            public void onFinish() {
                callback.onPostRequest();
            }
        });
    }

    /**
     * 解析数据
     */
    private void parseDada() {

    }

}
