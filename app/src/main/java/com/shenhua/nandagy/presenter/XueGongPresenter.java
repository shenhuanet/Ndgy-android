package com.shenhua.nandagy.presenter;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.nandagy.model.XueGongModel;
import com.shenhua.nandagy.model.XueGongModelImpl;
import com.shenhua.nandagy.view.XueGongView;

import java.util.ArrayList;

/**
 * 学工数据代理
 * Created by shenhua on 8/31/2016.
 */
public class XueGongPresenter implements HttpCallback<ArrayList[]> {

    private XueGongModel<ArrayList[]> model;
    private XueGongView xueGongView;
    private String url;

    /**
     * 构造方法
     *
     * @param xueGongView view
     * @param url         url，非host
     */
    public XueGongPresenter(XueGongView xueGongView, String url) {
        this.xueGongView = xueGongView;
        this.url = url;
        model = new XueGongModelImpl();
    }

    public void execute() {
        model.toGetXueGongData(url, this);
    }

    @Override
    public void onPreRequest() {
        xueGongView.showProgress();
    }

    @Override
    public void onSuccess(ArrayList[] data) {
        xueGongView.updateList(data);
    }

    @Override
    public void onError(String errorInfo) {
        xueGongView.showToast(errorInfo);
    }

    @Override
    public void onPostRequest() {
        xueGongView.hideProgress();
    }
}
