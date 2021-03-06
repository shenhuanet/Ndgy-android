package com.shenhua.nandagy.module.more.circle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.commonlibs.base.BaseSpacesItemDecoration;
import com.shenhua.commonlibs.utils.ConvertUtils;
import com.shenhua.lib.emoji.utils.EmojiLoader;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.module.more.circle.ui.adapter.CircleAdapter;
import com.shenhua.nandagy.bean.bmobbean.SchoolCircle;
import com.shenhua.nandagy.module.more.circle.presenter.CirclePresenterImpl;
import com.shenhua.nandagy.service.Constants;
import com.shenhua.nandagy.module.me.ui.activity.PublishDynamicActivity;
import com.shenhua.nandagy.utils.DataLoadType;
import com.shenhua.nandagy.utils.bmobutils.UserUtils;
import com.shenhua.nandagy.module.more.circle.view.CircleView;
import com.shenhua.nandagy.widget.refresh.AutoLoadMoreRecyclerView;
import com.shenhua.nandagy.widget.refresh.ThreePointLoadingView;

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
public class CircleActivity extends BaseActivity implements CircleView, SwipeRefreshLayout.OnRefreshListener, AutoLoadMoreRecyclerView.OnLoadMoreListener {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler_view)
    AutoLoadMoreRecyclerView mRecyclerView;
    @BindView(R.id.tpl_view)
    ThreePointLoadingView mLoadingView;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.empty)
    TextView mEmptyView;
    private CirclePresenterImpl mCirclePresenter;
    private CircleAdapter mCircleAdapter;
    private List<SchoolCircle> mDatas = new ArrayList<>();
    private int currentItem;

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        ButterKnife.bind(this);
        EmojiLoader.initEmoji(this, new String[]{"emoji_ay", "emoji_aojiao", "emoji_d"});
        mFab.attachToRecyclerView(mRecyclerView);
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setAutoLayoutManager(linearLayoutManager)
                .setAutoHasFixedSize(true)
                .addAutoItemDecoration(new BaseSpacesItemDecoration(ConvertUtils.dp2px(this, 4), false))
                .setAutoItemAnimator(new DefaultItemAnimator());
        mRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setOnLoadMoreListener(this);
        mCircleAdapter = new CircleAdapter(this, null);
        mRecyclerView.setAutoAdapter(mCircleAdapter);

        mCirclePresenter = new CirclePresenterImpl(CircleActivity.this);

        mRecyclerView.postDelayed(() -> {
            mCirclePresenter.execute();
        }, 500);

        mCircleAdapter.setOnItemClickListener((view, i, schoolCircle) -> {
            currentItem = i;
            Intent intent = new Intent(this, CircleDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(CircleDetailActivity.EXTRAKEY, schoolCircle);
            intent.putExtras(bundle);
            startActivityForResult(intent, Constants.Code.REQUEST_CODE_NAV_CIRCLE_DETAIL);
        });
    }

    @Override
    public void onRefresh() {
        mRefreshLayout.setRefreshing(true);
        mCirclePresenter.refreshData();
    }

    @Override
    public void loadMore() {
        mCirclePresenter.loadMoreData();
        mCircleAdapter.showFooter();
        mRecyclerView.scrollToPosition(mCircleAdapter.getItemCount() - 1);
    }

    @Override
    public void updateList(List<SchoolCircle> datas, @DataLoadType.DataLoadTypeChecker int type) {
        switch (type) {
            case DataLoadType.TYPE_REFRESH_SUCCESS:
                mRefreshLayout.setRefreshing(false);
                mDatas.addAll(datas);
                mCircleAdapter.setDatas(datas);
                mEmptyView.setVisibility(datas.size() <= 0 ? View.VISIBLE : View.GONE);
                if (mRecyclerView.isAllLoaded()) {
                    mRecyclerView.notifyMoreLoaded();
                }
                break;
            case DataLoadType.TYPE_REFRESH_FAIL:
                mRefreshLayout.setRefreshing(false);
                mEmptyView.setVisibility(View.VISIBLE);
                break;
            case DataLoadType.TYPE_LOAD_MORE_SUCCESS:
                mCircleAdapter.hideFooter();
                if (datas.size() <= 0) {
                    mRecyclerView.notifyAllLoaded();
                } else {
                    mCircleAdapter.addMoreItem(datas);
                    mRecyclerView.notifyMoreLoaded();
                }
                break;
            case DataLoadType.TYPE_LOAD_MORE_FAIL:
                if (datas.size() <= 0) {
                    mRecyclerView.notifyAllLoaded();
                }
                mCircleAdapter.hideFooter();
                mRecyclerView.notifyMoreLoaded();
                break;
        }
    }

    @Override
    public void showToast(String msg) {
        toast(msg);
    }

    @Override
    public void showProgress() {
        mLoadingView.play();
    }

    @Override
    public void hideProgress() {
        mLoadingView.stop();
    }

    @OnClick(R.id.fab)
    void addPublish() {
        if (!UserUtils.getInstance().isLogin()) {
            toast("请登录后操作");
            return;
        }
        Intent intent = new Intent(this, PublishDynamicActivity.class);
        startActivityForResult(intent, Constants.Code.REQUEST_CODE_NAV_TO_PUBLISH_DYNAMIC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.Code.REQUEST_CODE_NAV_TO_PUBLISH_DYNAMIC) {
                onRefresh();
            }
            if (requestCode == Constants.Code.REQUEST_CODE_NAV_CIRCLE_DETAIL) {
                if (mDatas != null && mDatas.size() > 0) {
                    SchoolCircle sc = (SchoolCircle) data.getSerializableExtra(CircleDetailActivity.EXTRAKEY);
                    if (sc != null) {
                        mDatas.set(currentItem, sc);
                        mCircleAdapter.updateItem(currentItem, sc);
                    }
                }
            }
        }
    }
}
