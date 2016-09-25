package com.shenhua.nandagy.model;

import android.text.TextUtils;

import com.shenhua.nandagy.bean.HomeData;
import com.shenhua.nandagy.callback.HttpCallback;
import com.shenhua.nandagy.manager.HttpManager;
import com.shenhua.nandagy.service.HttpService;

import org.jsoup.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 首页数据实现者
 * Created by shenhua on 8/29/2016.
 */
public class HomeModelImpl implements HomeModel<List<HomeData>> {

    private HttpManager httpManager = HttpManager.getInstance();

    @Override
    public void toGetHomeData(final String url, final HttpCallback<List<HomeData>> callback) {
        callback.onPreRequest();
        httpManager.sendRequest(new Runnable() {
            @Override
            public void run() {
                final String sessionCode;
//                Connection.Response response;
//                response = httpManager.buildResponse(httpManager.getConnection(HttpService.HOST_HOME,
//                        url, Connection.Method.GET));
//                System.out.println("shenhua sout:statusCode1=" + response.statusCode());
//                if (response.statusCode() == 302) {// 第一次重定向
//                    String session = response.header("Location");
//                    sessionCode = session.substring((session.lastIndexOf("=") + 1), session.length());
//                    response = httpManager.buildResponse(httpManager.getConnection(HttpService.HOST_HOME,
//                            "/?WebShieldDRSessionVerify=" + sessionCode, Connection.Method.GET));
//                    System.out.println("shenhua sout:statusCode2=" + response.statusCode());
//                    if (response.statusCode() == 302) {// 第二次重定向
//                        response = httpManager.buildResponse(httpManager.getConnection(HttpService.HOST_HOME,
//                                url, Connection.Method.GET));
//                        System.out.println("shenhua sout:statusCode3=" + response.statusCode());
//                        if (response.statusCode() == 200) {
//                            response = httpManager.buildResponse(httpManager.getConnection(HttpService.HOST_HOME,
//                                    "/?WebShieldSessionVerify=" + sessionCode, Connection.Method.GET));
//                            System.out.println("shenhua sout:statusCode4=" + response.statusCode());
//                            onStatusOK(response, url, callback);
//                        } else {
//                            onStatusOK(response, url, callback);
//                        }
//                    }
//                } else {
//                    onStatusOK(response, url, callback);
//                }
                if (TextUtils.isEmpty(url)) {
                    try {
                        Thread.sleep(3000);
                        List<HomeData> datas = new ArrayList<>();
                        for (int i = 0; i < 6; i++) {
                            HomeData data = new HomeData();
                            data.setTitle("titletitle" + i);
                            data.setDetail("结婚是一件神奇的事情，竟然可以提高一个男人的记忆力。");
                            data.setImgUrl("http://www.touxiang.cn/uploads/20130427/27-092032_933.jpg");
                            data.setTime("2016/08/31");
                            datas.add(data);
                        }
                        callback.onSuccess(datas);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        callback.onError("数据解析错误");
                    }
                } else {
                    callback.onError("111111测试");
                }
                callback.onPostRequest();
            }
        });
    }

    private void onStatusOK(Connection.Response response, String url, HttpCallback<List<HomeData>> callback) {
        Map<String, String> cookies;
        if (response.statusCode() == 200) {
            cookies = response.cookies();
            response = httpManager.buildResponse(httpManager.getConnection(HttpService.HOST_HOME, url, Connection.Method.GET).cookies(cookies));
            System.out.println("shenhua sout:------->" + response.body());
            callback.onSuccess(null);
        } else {
            System.out.println("shenhua sout: 特殊错误:" + response.statusCode());
            callback.onError("特殊错误:" + response.statusCode());
        }
    }

    private void parseDada() {
    }

}
