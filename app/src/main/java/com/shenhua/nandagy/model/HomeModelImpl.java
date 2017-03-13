package com.shenhua.nandagy.model;

import android.content.Context;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.commonlibs.mvp.ApiCallback;
import com.shenhua.commonlibs.mvp.HttpManager;
import com.shenhua.nandagy.bean.HomeData;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 首页数据实现者
 * Created by shenhua on 8/29/2016.
 */
public class HomeModelImpl implements HomeModel<List<HomeData>> {

    @Override
    public void toGetHomeData(Context context, String url, HttpCallback<List<HomeData>> callback) {
        new CompositeSubscription().add(HttpManager.getInstance(context).createHtmlGetObservable(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<String>() {
                    @Override
                    public void onPreExecute() {
                        System.out.println("shenhua sout:" + "kaishi");
                    }

                    @Override
                    public void onSuccess(String model) {
                        System.out.println("shenhua sout:" + model);
                    }

                    @Override
                    public void onFailure(String msg) {
                        System.out.println("shenhua sout:" + "shibai");
                    }

                    @Override
                    public void onFinish() {
                        System.out.println("shenhua sout:" + "wancheng");
                    }
                }));
//        presenter.addSubscription(HttpManager.getInstance(context).createHtmlGetObservable(url), new ApiCallback<String>() {
//            @Override
//            public void onPreExecute() {
//
//            }
//
//            @Override
//            public void onSuccess(String model) {
//
//            }
//
//            @Override
//            public void onFailure(String msg) {
//
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        });
    }

//    private HttpManager httpManager = HttpManager.getInstance();
//
//    @Override
//    public void toGetHomeData(final String url, final HttpCallback<List<HomeData>> callback) {
//        callback.onPreRequest();
//        httpManager.sendRequest(new Runnable() {
//            @Override
//            public void run() {
//                final String sessionCode;
////                Connection.Response response;
////                response = httpManager.buildResponse(httpManager.getConnection(HttpService.HOST_HOME,
////                        url, Connection.Method.GET));
////                System.out.println("shenhua sout:statusCode1=" + response.statusCode());
////                if (response.statusCode() == 302) {// 第一次重定向
////                    String session = response.header("Location");
////                    sessionCode = session.substring((session.lastIndexOf("=") + 1), session.length());
////                    response = httpManager.buildResponse(httpManager.getConnection(HttpService.HOST_HOME,
////                            "/?WebShieldDRSessionVerify=" + sessionCode, Connection.Method.GET));
////                    System.out.println("shenhua sout:statusCode2=" + response.statusCode());
////                    if (response.statusCode() == 302) {// 第二次重定向
////                        response = httpManager.buildResponse(httpManager.getConnection(HttpService.HOST_HOME,
////                                url, Connection.Method.GET));
////                        System.out.println("shenhua sout:statusCode3=" + response.statusCode());
////                        if (response.statusCode() == 200) {
////                            response = httpManager.buildResponse(httpManager.getConnection(HttpService.HOST_HOME,
////                                    "/?WebShieldSessionVerify=" + sessionCode, Connection.Method.GET));
////                            System.out.println("shenhua sout:statusCode4=" + response.statusCode());
////                            onStatusOK(response, url, callback);
////                        } else {
////                            onStatusOK(response, url, callback);
////                        }
////                    }
////                } else {
////                    onStatusOK(response, url, callback);
////                }
//                if (TextUtils.isEmpty(url)) {
//                    try {
//                        Thread.sleep(3000);
//                        List<HomeData> datas = new ArrayList<>();
//                        for (int i = 0; i < 6; i++) {
//                            HomeData data = new HomeData();
//                            data.setTitle("titletitle" + i);
//                            data.setDetail("结婚是一件神奇的事情，竟然可以提高一个男人的记忆力。");
//                            data.setImgUrl("http://www.touxiang.cn/uploads/20130427/27-092032_933.jpg");
//                            data.setTime("2016/08/31");
//                            datas.add(data);
//                        }
//                        callback.onSuccess(datas);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                        callback.onError("数据解析错误");
//                    }
//                } else {
//                    callback.onError("111111测试");
//                }
//                callback.onPostRequest();
//            }
//        });
//    }
//
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
//
//    private void parseDada() {
//    }


}
