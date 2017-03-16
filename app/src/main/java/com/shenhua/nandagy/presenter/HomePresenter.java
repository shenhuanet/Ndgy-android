package com.shenhua.nandagy.presenter;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.commonlibs.mvp.BasePresenter;
import com.shenhua.nandagy.bean.HomeData;
import com.shenhua.nandagy.model.HomeModel;
import com.shenhua.nandagy.model.HomeModelImpl;
import com.shenhua.nandagy.view.HomeView;

import java.util.List;

/**
 * 主页数据代理
 * Created by shenhua on 8/29/2016.
 */
public class HomePresenter extends BasePresenter<HomeView> implements HttpCallback<List<HomeData>> {

    private HomeModel<List<HomeData>> homeModel;
    private String url;
    private boolean mHasInit;

    public HomePresenter(HomeView homeView, String url) {
        attachView(homeView);
        this.url = url;
        homeModel = new HomeModelImpl();
    }

    public void execute() {
        homeModel.toGetHomeData(this, url, this);
    }

    @Override
    public void onPreRequest() {
        mvpView.showProgress();
    }

    @Override
    public void onSuccess(List<HomeData> data) {
        mHasInit = true;
        mvpView.updateList(null);
    }

    @Override
    public void onError(String errorInfo) {
        mHasInit = false;
        mvpView.showToast(errorInfo);
    }

    @Override
    public void onPostRequest() {
        mvpView.hideProgress();
    }

}
