package com.shenhua.nandagy.presenter;

import com.shenhua.nandagy.bean.HomeData;
import com.shenhua.nandagy.callback.HttpCallback;
import com.shenhua.nandagy.model.HomeModel;
import com.shenhua.nandagy.model.HomeModelImpl;
import com.shenhua.nandagy.manager.HttpManager;
import com.shenhua.nandagy.view.HomeView;

import java.util.List;

/**
 * 主页数据代理
 * Created by shenhua on 8/29/2016.
 */
public class HomePresenter implements HttpCallback<List<HomeData>> {

    private HomeModel<List<HomeData>> homeModel;
    private HomeView homeView;
    private String url;
    private boolean mHasInit;

    public HomePresenter(HomeView homeView, String url) {
        this.homeView = homeView;
        this.url = url;
        homeModel = new HomeModelImpl();
    }

    public void execute() {
        homeModel.toGetHomeData(url, this);
    }

    @Override
    public void onPreRequest() {
        System.out.println("shenhua sout:" + "onPreRequest");
        homeView.showProgress();
    }

    @Override
    public void onSuccess(List<HomeData> data) {
        System.out.println("shenhua sout:" + "onSuccess");
        mHasInit = true;
//        if (data != null)
        homeView.updateList(data, HttpManager.DataLoadType.DATA_TYPE_SUCCESS);
    }

    @Override
    public void onError(String errorInfo) {
        System.out.println("shenhua sout: onError: " + errorInfo);
        mHasInit = false;
//        homeView.updateList(null, HttpManager.DataLoadType.DATA_TYPE_ERROR);
        homeView.showToast(errorInfo);
    }

    @Override
    public void onPostRequest() {
        System.out.println("shenhua sout:" + "onPostRequest");
        homeView.hideProgress();
    }

}
