package com.shenhua.nandagy.presenter;

import android.content.Context;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.commonlibs.mvp.ApiCallback;
import com.shenhua.commonlibs.mvp.BasePresenter;
import com.shenhua.nandagy.bean.HomeData;
import com.shenhua.nandagy.manager.HttpManager;
import com.shenhua.nandagy.model.HomeModel;
import com.shenhua.nandagy.model.HomeModelImpl;
import com.shenhua.nandagy.view.HomeView;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 主页数据代理
 * Created by shenhua on 8/29/2016.
 */
public class HomePresenter extends BasePresenter<HomeView> implements HttpCallback<List<HomeData>> {

    private HomeModel<List<HomeData>> homeModel;
    private Context context;
    private String url;
    private boolean mHasInit;

    public HomePresenter(Context context, HomeView homeView, String url) {
        attachView(homeView);
        this.context = context;
        this.url = url;
        homeModel = new HomeModelImpl();
    }

    public void execute() {
//        homeModel.toGetHomeData(context, url, this);

        new CompositeSubscription().add(com.shenhua.commonlibs.mvp.HttpManager.getInstance(context).createHtmlGetObservable(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<String>() {
                    @Override
                    public void onPreExecute() {
                        System.out.println("shenhua sout:" + "kaishi");
                    }

                    @Override
                    public void onSuccess(String model) {
                        System.out.println("shenhua sout:" + model);
                    }

                    @Override
                    public void onFailure(String msg) {
                        System.out.println("shenhua sout:" + "shibai");
                    }

                    @Override
                    public void onFinish() {
                        System.out.println("shenhua sout:" + "wancheng");
                    }
                }));

//        addSubscription(com.shenhua.commonlibs.mvp.HttpManager.getInstance().createHtmlGetObservable(url), new ApiCallback() {
//            @Override
//            public void onPreExecute() {
//
//            }
//
//            @Override
//            public void onSuccess(Object model) {
//
//            }
//
//            @Override
//            public void onFailure(String msg) {
//
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        });
    }

    @Override
    public void onPreRequest() {
        System.out.println("shenhua sout:" + "onPreRequest");
        mvpView.showProgress();
    }

    @Override
    public void onSuccess(List<HomeData> data) {
        System.out.println("shenhua sout:" + "onSuccess");
        mHasInit = true;
//        if (data != null)
        mvpView.updateList(data, HttpManager.DataLoadType.DATA_TYPE_SUCCESS);
    }

    @Override
    public void onError(String errorInfo) {
        System.out.println("shenhua sout: onError: " + errorInfo);
        mHasInit = false;
//        homeView.updateList(null, HttpManager.DataLoadType.DATA_TYPE_ERROR);
        mvpView.showToast(errorInfo);
    }

    @Override
    public void onPostRequest() {
        System.out.println("shenhua sout:" + "onPostRequest");
        mvpView.hideProgress();
    }

}
