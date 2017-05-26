package com.shenhua.nandagy.module.xuegong.presenter;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.commonlibs.mvp.BasePresenter;
import com.shenhua.nandagy.bean.module.XueGongData;
import com.shenhua.nandagy.module.xuegong.model.XueGongModel;
import com.shenhua.nandagy.module.xuegong.model.XueGongModelImpl;
import com.shenhua.nandagy.module.xuegong.view.XueGongView;

/**
 * 学工数据代理
 * Created by shenhua on 8/31/2016.
 */
public class XueGongPresenter extends BasePresenter<XueGongView> implements HttpCallback<XueGongData> {

    private XueGongModel<XueGongData> model;
    private String url;

    /**
     * 构造方法
     *
     * @param xueGongView view
     * @param url         url，非host
     */
    public XueGongPresenter(XueGongView xueGongView, String url) {
        attachView(xueGongView);
        this.url = url;
        model = new XueGongModelImpl();
    }

    public void execute() {
        model.toGetXueGongData(this, url, this);
    }

    @Override
    public void onPreRequest() {
        mvpView.showProgress();
    }

    @Override
    public void onSuccess(XueGongData data) {
        mvpView.updateList(data);
    }

    @Override
    public void onError(String errorInfo) {
        mvpView.showToast(errorInfo);
    }

    @Override
    public void onPostRequest() {
        mvpView.hideProgress();
    }
}
