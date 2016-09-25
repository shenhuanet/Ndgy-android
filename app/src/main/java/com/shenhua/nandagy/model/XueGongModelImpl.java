package com.shenhua.nandagy.model;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.XueGongData;
import com.shenhua.nandagy.callback.HttpCallback;
import com.shenhua.nandagy.manager.HttpManager;

import java.util.ArrayList;

/**
 * 学工处数据实现者
 * Created by shenhua on 8/31/2016.
 */
public class XueGongModelImpl implements XueGongModel<ArrayList[]> {

    private HttpManager httpManager = HttpManager.getInstance();

    @Override
    public void toGetXueGongData(String url, final HttpCallback<ArrayList[]> callback) {
        callback.onPreRequest();
        httpManager.sendRequest(new Runnable() {
            @Override
            public void run() {
                ArrayList<XueGongData> datas = new ArrayList<>();
                ArrayList<XueGongData.BannerData> banners = new ArrayList<>();
                String[] images = {
                        "http://img1.3lian.com/img013/v4/57/d/111.jpg",
                        "http://pic.58pic.com/58pic/12/31/48/18658PICXxM.jpg",
                        "http://tupian.enterdesk.com/2013/lxy/12/26/1/6.jpg",
                        "http://tupian.enterdesk.com/2013/mxy/12/07/3/9.jpg",
                        "http://img5.imgtn.bdimg.com/it/u=2071825957,3967640546&fm=21&gp=0.jpg"};
                String[] titles = {
                        "支持SPDY",
                        "可以合并多个到同一个主机的请求",
                        "使用GZIP压缩减少传输的数据量",
                        "缓存响应避免重复的网络请求、拦截器",
                        "第一缺点是消息回来需要切到主线程，主线程要自己去写"};
                for (int i = 0; i < images.length; i++) {
                    XueGongData data = new XueGongData();
                    XueGongData.BannerData bannerData = data.new BannerData();
                    bannerData.setbImage(images[i]);
                    bannerData.setbTitle(titles[i]);
                    bannerData.setbHref(images[i]);
                    banners.add(bannerData);
                }

                for (int i = 0; i < 20; i++) {
                    XueGongData data = new XueGongData();
                    data.setTitle("title" + i);
                    data.setTime("2016/08/31");
                    data.setHref(Integer.toString(i));
                    if (i % 3 == 0)
                        data.setNewsType(R.drawable.ic_xuegong_tz);
                    else
                        data.setNewsType(R.drawable.ic_xuegong_xl);
                    datas.add(data);
                }
                ArrayList[] lists = new ArrayList[2];
                lists[0] = banners;
                lists[1] = datas;
                callback.onSuccess(lists);
                callback.onPostRequest();
            }
        });

    }
}
