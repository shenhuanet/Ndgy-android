package com.shenhua.nandagy.model;

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
                .createHtmlGetObservable(App.getContext(), url), new ApiCallback<String>() {
            @Override
            public void onPreExecute() {
                callback.onPreRequest();
            }

            @Override
            public void onSuccess(String o) {
//                Log.d(TAG, "onSuccess: " + o);
                // TODO: 3/16/2017  parseHomeDada();  有乱码
//                parseDada();
                callback.onSuccess(null);

//                Response response;
//                response.body().bytes();
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

//    private void onStatusOK(Connection.Response response, String url, HttpCallback<List<HomeData>> callback) {
//        Map<String, String> cookies;
//        if (response.statusCode() == 200) {
//            cookies = response.cookies();
//            response = httpManager.buildResponse(httpManager.getConnection(HttpService.HOST_HOME, url, Connection.Method.GET).cookies(cookies));
//            System.out.println("shenhua sout:------->" + response.body());
//            callback.onSuccess(null);
//        } else {
//            System.out.println("shenhua sout: 特殊错误:" + response.statusCode());
//            callback.onError("特殊错误:" + response.statusCode());
//        }
//    }

    private void parseDada() {
    }

}
