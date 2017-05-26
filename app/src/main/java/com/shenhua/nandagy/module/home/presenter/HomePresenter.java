package com.shenhua.nandagy.module.home.presenter;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.commonlibs.mvp.BasePresenter;
import com.shenhua.nandagy.bean.module.HomeData;
import com.shenhua.nandagy.module.home.model.HomeModel;
import com.shenhua.nandagy.module.home.model.HomeModelImpl;
import com.shenhua.nandagy.module.home.view.HomeView;

import java.util.List;

/**
 * 主页数据代理
 * Created by shenhua on 8/29/2016.
 */
public class HomePresenter extends BasePresenter<HomeView> implements HttpCallback<List<HomeData>> {

    private HomeModel<List<HomeData>> homeModel;
    private String url;

    public HomePresenter(HomeView homeView, String url) {
        attachView(homeView);
        this.url = url;
        homeModel = new HomeModelImpl();
    }

    public void execute(int type) {
        homeModel.toGetHomeData(this, url, this, type);
    }

    @Override
    public void onPreRequest() {
        mvpView.showProgress();
    }

    @Override
    public void onSuccess(List<HomeData> data) {
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
