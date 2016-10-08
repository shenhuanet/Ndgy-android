package com.shenhua.nandagy.presenter;

import com.shenhua.nandagy.bean.CircleData;
import com.shenhua.nandagy.callback.HttpCallback;
import com.shenhua.nandagy.model.CircleModel;
import com.shenhua.nandagy.model.CircleModelImpl;
import com.shenhua.nandagy.view.CircleView;

import java.util.List;

/**
 * Created by MVPHelper on 2016/10/06
 */
public class CirclePresenterImpl implements CirclePresenter, HttpCallback<List<CircleData>> {

    private CircleModel<List<CircleData>> circleModel;
    private CircleView circleView;

    public CirclePresenterImpl(CircleView circleView) {
        this.circleView = circleView;
        circleModel = new CircleModelImpl();
    }

    public void execute() {
        circleModel.toGetCircleData(this);
    }

    @Override
    public void refreshData() {
        // TODO: 10/6/2016  下拉刷新数据
    }

    @Override
    public void loadMoreData() {
        // TODO: 10/6/2016   加载更多数据
    }

    @Override
    public void onPreRequest() {
        circleView.showProgress();
    }

    @Override
    public void onSuccess(List<CircleData> data) {
        circleView.updateList(data);
    }

    @Override
    public void onError(String errorInfo) {
        circleView.showToast(errorInfo);
    }

    @Override
    public void onPostRequest() {
        circleView.hideProgress();
    }
}