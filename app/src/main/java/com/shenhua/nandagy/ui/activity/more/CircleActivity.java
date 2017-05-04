package com.shenhua.nandagy.ui.activity.more;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.adapter.CircleAdapter;
import com.shenhua.nandagy.bean.bmobbean.SchoolCircle;
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
@ActivityFragmentInject(
        contentViewId = R.layout.activity_more_circle,
        toolbarId = R.id.common_toolbar,
        toolbarHomeAsUp = true,
        toolbarTitle = R.string.toolbar_title_more_circle,
        toolbarTitleId = R.id.toolbar_title
)
public class CircleActivity extends BaseActivity implements CircleView, SwipeRefreshLayout.OnRefreshListener, OnMoreListener {

    @BindView(recyclerView)
    SuperRecyclerView mSwipeRefreshLayout;
    CirclePresenterImpl circlePresenter;

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        ButterKnife.bind(this);
        mSwipeRefreshLayout.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        mSwipeRefreshLayout.setLayoutManager(new LinearLayoutManager(this));
        mSwipeRefreshLayout.setRefreshListener(this);

        circlePresenter = new CirclePresenterImpl(this);
        circlePresenter.execute();
    }

    @Override
    public void onRefresh() {
        circlePresenter.refreshData();

        new Handler().postDelayed(() -> {
            toast("ok");
            mSwipeRefreshLayout.setRefreshing(false);
        }, 2000);
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        System.out.println("shenhua sout:" + "more");
        circlePresenter.loadMoreData();
    }

    @Override
    public void updateList(List<SchoolCircle> datas) {
        CircleAdapter adapter = new CircleAdapter(this, datas);
        mSwipeRefreshLayout.setAdapter(adapter);
        adapter.setOnItemClickListener((view, i, data) -> {
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
