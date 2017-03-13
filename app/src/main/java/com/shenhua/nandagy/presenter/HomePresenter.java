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

//        addSubscription();
    }

    @Override
    public void onPreRequest() {
        System.out.println("shenhua sout:" + "onPreRequest");
//        mvpView.showProgress();
    }

    @Override
    public void onSuccess(List<HomeData> data) {
        System.out.println("shenhua sout:" + "onSuccess");
        mHasInit = true;
//        if (data != null)
//        mvpView.updateList(data, HttpManager.DataLoadType.DATA_TYPE_SUCCESS);
    }

    @Override
    public void onError(String errorInfo) {
        System.out.println("shenhua sout: onError: " + errorInfo);
        mHasInit = false;
//        homeView.updateList(null, HttpManager.DataLoadType.DATA_TYPE_ERROR);
//        mvpView.showToast(errorInfo);
    }

    @Override
    public void onPostRequest() {
        System.out.println("shenhua sout:" + "onPostRequest");
//        mvpView.hideProgress();
    }

}
