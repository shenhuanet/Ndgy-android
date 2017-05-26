package com.shenhua.nandagy.module.jiaowu.presenter;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.commonlibs.mvp.BasePresenter;
import com.shenhua.nandagy.bean.module.JiaowuData;
import com.shenhua.nandagy.module.jiaowu.model.JiaowuModel;
import com.shenhua.nandagy.module.jiaowu.model.JiaowuModelImpl;
import com.shenhua.nandagy.module.jiaowu.view.JiaowuView;

/**
 * Created by Shenhua on 3/24/2017.
 * e-mail shenhuanet@126.com
 */
public class JiaowuPresenter extends BasePresenter<JiaowuView> implements HttpCallback<JiaowuData> {

    private JiaowuModel<JiaowuData> model;
    private String url;

    public JiaowuPresenter(JiaowuView jiaowuView, String url) {
        attachView(jiaowuView);
        this.url = url;
        model = new JiaowuModelImpl();
    }

    public void execute() {
        model.getJiaowuDatas(this, url, this);
    }

    @Override
    public void onPreRequest() {
        mvpView.showProgress();
    }

    @Override
    public void onSuccess(JiaowuData data) {
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
