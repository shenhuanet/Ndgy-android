package com.shenhua.nandagy.ui.activity.more;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.adapter.CircleAdapter;
import com.shenhua.nandagy.bean.bmobbean.SchoolCircle;
import com.shenhua.nandagy.presenter.CirclePresenterImpl;
import com.shenhua.nandagy.view.CircleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 校内圈子
 * Created by Shenhua on 9/7/2016.
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_more_circle,
        toolbarId = R.id.common_toolbar,
        toolbarHomeAsUp = true,
        toolbarTitle = R.string.toolbar_title_more_circle,
        toolbarTitleId = R.id.toolbar_title
)
public class CircleActivity extends BaseActivity implements CircleView, SwipeRefreshLayout.OnRefreshListener, OnMoreListener {

    @BindView(R.id.recyclerView)
    SuperRecyclerView mRecycler;
    private CirclePresenterImpl mCirclePresenter;
    private CircleAdapter mCircleAdapter;
    private List<SchoolCircle> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        ButterKnife.bind(this);
        mRecycler.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setRefreshListener(this);

        mCircleAdapter = new CircleAdapter(this, null);
        mCircleAdapter.setOnItemClickListener((view, i, schoolCircle) -> {

        });
        mRecycler.setAdapter(mCircleAdapter);
        mCirclePresenter = new CirclePresenterImpl(this);
        mCirclePresenter.execute();
    }

    @Override
    public void onRefresh() {
        mCirclePresenter.refreshData();
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        mCirclePresenter.loadMoreData();
    }

    @Override
    public void updateList(List<SchoolCircle> datas) {
        mRecycler.getRecyclerView().setVisibility(View.VISIBLE);
        mDatas.addAll(datas);
        mCircleAdapter.setDatas(datas);
    }

    @Override
    public void showToast(String msg) {
        toast(msg);
    }

    @Override
    public void showProgress() {
        mRecycler.showProgress();
    }

    @Override
    public void hideProgress() {
        mRecycler.hideProgress();
    }
}
