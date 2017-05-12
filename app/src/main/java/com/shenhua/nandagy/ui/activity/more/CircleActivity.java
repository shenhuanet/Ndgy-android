package com.shenhua.nandagy.ui.activity.more;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.lib.emoji.EmojiLayout;
import com.shenhua.lib.emoji.utils.EmojiLoader;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.adapter.CircleAdapter;
import com.shenhua.nandagy.bean.bmobbean.SchoolCircle;
import com.shenhua.nandagy.presenter.CirclePresenterImpl;
import com.shenhua.nandagy.view.CircleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private String[] mEmojiDirs = {"emoji_ay", "emoji_aojiao", "emoji_d"};

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        ButterKnife.bind(this);

        EmojiLoader.getInstance().getEmojiGroup(this, EmojiLayout.DEFAULT_EMOJI);
        for (String mEmojiDir : mEmojiDirs) {
            EmojiLoader.getInstance().getEmojiGroup(this, mEmojiDir);
        }

        mRecycler.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setRefreshListener(this);
        mRecycler.setupMoreListener(this, 6);// 50
        mCircleAdapter = new CircleAdapter(this, null);
        mRecycler.setAdapter(mCircleAdapter);
        mRecycler.postDelayed(() -> {
            mCirclePresenter = new CirclePresenterImpl(CircleActivity.this);
            mCirclePresenter.execute();
        }, 500);

        mCircleAdapter.setOnItemClickListener((view, i, schoolCircle) -> {
            Intent intent = new Intent(this, CircleDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(CircleDetailActivity.EXTRAKEY, schoolCircle);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    @Override
    public void onRefresh() {
        mCirclePresenter.refreshData();
        mRecycler.postDelayed(() -> mRecycler.setRefreshing(false), 2000);
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        Toast.makeText(this, "加载更多哦", Toast.LENGTH_SHORT).show();
        mCirclePresenter.loadMoreData();
        mRecycler.postDelayed(() -> {
            mRecycler.setLoadingMore(false);
            mRecycler.hideMoreProgress();
        }, 2000);
    }

    @Override
    public void updateList(List<SchoolCircle> datas) {
        mRecycler.getEmptyView().setVisibility(View.GONE);
        mRecycler.getRecyclerView().setVisibility(View.VISIBLE);
        mDatas.addAll(datas);
        mCircleAdapter.setDatas(datas);
    }

    @Override
    public void showToast(String msg) {
        mRecycler.getEmptyView().setVisibility(View.VISIBLE);
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

    @OnClick(R.id.fab)
    void addPublish() {
        toast("点我干嘛");
    }
}
