package com.shenhua.nandagy.ui.activity.more;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import com.melnykov.fab.FloatingActionButton;
import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.commonlibs.base.BaseSpacesItemDecoration;
import com.shenhua.commonlibs.utils.ConvertUtils;
import com.shenhua.lib.emoji.utils.EmojiLoader;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.adapter.CircleAdapter;
import com.shenhua.nandagy.bean.bmobbean.SchoolCircle;
import com.shenhua.nandagy.presenter.CirclePresenterImpl;
import com.shenhua.nandagy.service.Constants;
import com.shenhua.nandagy.ui.activity.me.PublishDynamicActivity;
import com.shenhua.nandagy.utils.DataLoadType;
import com.shenhua.nandagy.utils.bmobutils.UserUtils;
import com.shenhua.nandagy.view.CircleView;
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
    AutoLoadMoreRecyclerView mRecycler;
    @BindView(R.id.tpl_view)
    ThreePointLoadingView mLoadingView;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    private CirclePresenterImpl mCirclePresenter;
    private CircleAdapter mCircleAdapter;
    private List<SchoolCircle> mDatas = new ArrayList<>();
    private int currentItem;

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        ButterKnife.bind(this);
        EmojiLoader.initEmoji(this, new String[]{"emoji_ay", "emoji_aojiao", "emoji_d"});
        mFab.attachToRecyclerView(mRecycler);
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setAutoLayoutManager(linearLayoutManager)
                .setAutoHasFixedSize(true)
                .addAutoItemDecoration(new BaseSpacesItemDecoration(ConvertUtils.dp2px(this, 4), false))
                .setAutoItemAnimator(new DefaultItemAnimator());
        mRefreshLayout.setOnRefreshListener(this);
        mRecycler.setOnLoadMoreListener(this);
        mCircleAdapter = new CircleAdapter(this, null);
        mRecycler.setAutoAdapter(mCircleAdapter);

        mCirclePresenter = new CirclePresenterImpl(CircleActivity.this);

        mRecycler.postDelayed(() -> {
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
        mRecycler.scrollToPosition(mCircleAdapter.getItemCount() - 1);
    }

    @Override
    public void updateList(List<SchoolCircle> datas, @DataLoadType.DataLoadTypeChecker int type) {
        switch (type) {
            case DataLoadType.TYPE_REFRESH_SUCCESS:
                mRefreshLayout.setRefreshing(false);
                mDatas.addAll(datas);
                mCircleAdapter.setDatas(datas);
                if (mRecycler.isAllLoaded()) {
                    // 之前全部加载完了的话，这里把状态改回来供底部加载用
                    mRecycler.notifyMoreLoaded();
                }
                break;
            case DataLoadType.TYPE_REFRESH_FAIL:
                mRefreshLayout.setRefreshing(false);
                break;
            case DataLoadType.TYPE_LOAD_MORE_SUCCESS:
                mCircleAdapter.hideFooter();
                if (datas == null || datas.size() == 0) {
                    mRecycler.notifyAllLoaded();
                } else {
                    mCircleAdapter.addMoreItem(datas);
                    mRecycler.notifyMoreLoaded();
                }
                break;
            case DataLoadType.TYPE_LOAD_MORE_FAIL:
                mCircleAdapter.hideFooter();
                mRecycler.notifyMoreLoaded();
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
