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

    @Override
    public void toGetHomeData(BasePresenter basePresenter, final String url, final HttpCallback<List<HomeData>> callback) {
        callback.onPreRequest();
        System.out.println("shenhua sout:" + url);
        basePresenter.addSubscription(com.shenhua.commonlibs.mvp.HttpManager.getInstance()
                .createHtmlGetObservable(App.getContext(), url), new ApiCallback<String>() {
            @Override
            public void onPreExecute() {
                System.out.println("shenhua sout:" + "开始");
            }

            @Override
            public void onSuccess(String o) {
                System.out.println("shenhua sout:" + o);
            }

            @Override
            public void onFailure(String s) {
                System.out.println("shenhua sout:" + "失败");
            }

            @Override
            public void onFinish() {
                System.out.println("shenhua sout:" + "结束");
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
