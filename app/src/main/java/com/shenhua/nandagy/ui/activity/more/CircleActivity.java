package com.shenhua.nandagy.ui.activity.more;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.adapter.CircleAdapter;
import com.shenhua.nandagy.base.BaseActivity;
import com.shenhua.nandagy.bean.CircleData;
import com.shenhua.nandagy.presenter.CirclePresenterImpl;
import com.shenhua.nandagy.view.CircleView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.shenhua.nandagy.R.id.recyclerView;

/**
 * 校内圈子
 * Created by Shenhua on 9/7/2016.
 */
public class CircleActivity extends BaseActivity implements CircleView, SwipeRefreshLayout.OnRefreshListener, OnMoreListener {

    @BindView(recyclerView)
    SuperRecyclerView mSwipeRefreshLayout;
    CirclePresenterImpl circlePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_circle);
        ButterKnife.bind(this);
        setupActionBar("校内圈子", true);

        mSwipeRefreshLayout.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        mSwipeRefreshLayout.setLayoutManager(new LinearLayoutManager(this));
        mSwipeRefreshLayout.setRefreshListener(this);

        circlePresenter = new CirclePresenterImpl(this);
        circlePresenter.execute();
    }

    @Override
    public void onRefresh() {
        circlePresenter.refreshData();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toast("ok");
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        System.out.println("shenhua sout:" + "more");
        circlePresenter.loadMoreData();
    }

    @Override
    public void updateList(List<CircleData> datas) {
        CircleAdapter adapter = new CircleAdapter(this, datas);
        mSwipeRefreshLayout.setAdapter(adapter);
        adapter.setOnItemClickListener((view, i) -> {

        });
    }

    @Override
    public void showToast(String msg) {
        toast(msg);
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.showProgress();
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.hideProgress();
    }
}
