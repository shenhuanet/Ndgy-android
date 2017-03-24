package com.shenhua.nandagy.presenter;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.commonlibs.mvp.BasePresenter;
import com.shenhua.nandagy.bean.JiaowuData;
import com.shenhua.nandagy.model.JiaowuModel;
import com.shenhua.nandagy.model.JiaowuModelImpl;
import com.shenhua.nandagy.view.JiaowuView;

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
