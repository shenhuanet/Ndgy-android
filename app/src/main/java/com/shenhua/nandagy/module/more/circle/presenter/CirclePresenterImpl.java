package com.shenhua.nandagy.module.more.circle.presenter;

import com.shenhua.nandagy.bean.bmobbean.SchoolCircle;
import com.shenhua.nandagy.callback.CircleLoaderCallback;
import com.shenhua.nandagy.module.more.circle.model.CircleModel;
import com.shenhua.nandagy.module.more.circle.model.CircleModelImpl;
import com.shenhua.nandagy.utils.DataLoadType;
import com.shenhua.nandagy.module.more.circle.view.CircleView;

import java.util.List;

/**
 * Created by MVPHelper on 2016/10/06
 */
public class CirclePresenterImpl implements CirclePresenter, CircleLoaderCallback {

    private CircleModel<List<SchoolCircle>> circleModel;
    private CircleView circleView;
    private int mStartPage = 0;

    public CirclePresenterImpl(CircleView circleView) {
        this.circleView = circleView;
        circleModel = new CircleModelImpl();
    }

    public void execute() {
        circleModel.toGetCircleData(this);
    }

    @Override
    public void refreshData() {
        circleModel.toGetCircleData(this);
    }

    @Override
    public void loadMoreData() {
        circleModel.toLoadMoreCircleData(mStartPage, this);
    }

    @Override
    public void onPreRequest() {
        circleView.showProgress();
    }

    /**
     * 用于加载更多时
     */
    @Override
    public void onSuccess(List<SchoolCircle> schoolCircles, int type) {
        mStartPage += schoolCircles.size();
        circleView.updateList(schoolCircles, type);
    }

    /**
     * 用于刷新
     */
    @Override
    public void onSuccess(List<SchoolCircle> data) {
        mStartPage = data.size();
        circleView.updateList(data, DataLoadType.TYPE_REFRESH_SUCCESS);
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