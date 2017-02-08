package com.shenhua.nandagy.ui.activity.more;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.adapter.CircleAdapter;
import com.shenhua.nandagy.base.BaseActivity;
import com.shenhua.nandagy.bean.CircleData;
import com.shenhua.nandagy.callback.OnItemClickListener;
import com.shenhua.nandagy.presenter.CirclePresenterImpl;
import com.shenhua.nandagy.view.CircleView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 校内圈子
 * Created by Shenhua on 9/7/2016.
 */
public class CircleActivity extends BaseActivity implements CircleView, SwipeRefreshLayout.OnRefreshListener, OnMoreListener {

    @Bind(R.id.list)
    SuperRecyclerView recyclerView;
    CirclePresenterImpl circlePresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_circle);
        ButterKnife.bind(this);
        setupActionBar("校内圈子", true);

        recyclerView.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setRefreshListener(this);

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
                recyclerView.setRefreshing(false);
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
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {

            }
        });
    }

    @Override
    public void showToast(String msg) {
        toast(msg);
    }

    @Override
    public void showProgress() {
        recyclerView.showProgress();
    }

    @Override
    public void hideProgress() {
        recyclerView.hideProgress();
    }
}
