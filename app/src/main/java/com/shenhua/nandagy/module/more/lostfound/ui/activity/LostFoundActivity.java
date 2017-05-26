package com.shenhua.nandagy.module.more.lostfound.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.commonlibs.base.BaseSpacesItemDecoration;
import com.shenhua.commonlibs.utils.ConvertUtils;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.module.more.lostfound.ui.adapter.LostFoundAdapter;
import com.shenhua.nandagy.bean.bmobbean.LostAndFound;
import com.shenhua.nandagy.bean.bmobbean.MyUser;
import com.shenhua.nandagy.module.more.lostfound.presenter.LostFoundPresenter;
import com.shenhua.nandagy.module.more.lostfound.view.LostFoundView;
import com.shenhua.nandagy.widget.refresh.AutoLoadMoreRecyclerView;
import com.shenhua.nandagy.widget.refresh.ThreePointLoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 失物招领
 * Created by Shenhua on 9/7/2016.
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_more_lost,
        toolbarId = R.id.common_toolbar,
        toolbarHomeAsUp = true,
        toolbarTitle = R.string.toolbar_title_more_lost,
        toolbarTitleId = R.id.toolbar_title
)
public class LostFoundActivity extends BaseActivity implements LostFoundView, SwipeRefreshLayout.OnRefreshListener, AutoLoadMoreRecyclerView.OnLoadMoreListener {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler_view)
    AutoLoadMoreRecyclerView mRecyclerView;
    @BindView(R.id.tpl_view)
    ThreePointLoadingView mLoadingView;
    @BindView(R.id.empty_txt)
    TextView mEmptyView;
    private LostFoundAdapter mAdapter;
    private LostFoundPresenter mPresenter;
    private List<LostAndFound> mLostAndFounds = new ArrayList<>();
    private static final int REQUEST_CODE_NAV_ADD = 100;
    private boolean mIsMe = false;

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        ButterKnife.bind(this);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setAutoLayoutManager(linearLayoutManager)
                .setAutoHasFixedSize(true)
                .addAutoItemDecoration(new BaseSpacesItemDecoration(ConvertUtils.dp2px(this, 4), false))
                .setAutoItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setOnLoadMoreListener(this);
        mAdapter = new LostFoundAdapter(this, mLostAndFounds);
        mRecyclerView.setAutoAdapter(mAdapter);
        mPresenter = new LostFoundPresenter(this);
        new Handler().postDelayed(() -> {
            mPresenter.refresh();
        }, 200);
        mAdapter.setOnItemClickListener((view, i, lostAndFound) -> {
            MyUser user = BmobUser.getCurrentUser(MyUser.class);
            if (user == null) {
                return;
            }
            if (user.equals(lostAndFound.getUser()) && mIsMe) {
                showBottomDialog(i, lostAndFound);
            }
        });
    }

    @Override
    public void onRefresh() {
        mRefreshLayout.setRefreshing(true);
        if (mIsMe) {
            mPresenter.getSeltLostFound(BmobUser.getCurrentUser(MyUser.class));
        } else {
            mPresenter.refresh();
        }
    }

    @Override
    public void loadMore() {
        mPresenter.loadMore(mLostAndFounds.size());
    }

    @Override
    public void showEmpty(boolean show) {
        mRefreshLayout.setRefreshing(false);
        mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
        mEmptyView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setDatas(List<LostAndFound> lostAndFounds) {
        mRefreshLayout.setRefreshing(false);
        mLostAndFounds.clear();
        mLostAndFounds.addAll(lostAndFounds);
        mAdapter.setDatas(mLostAndFounds);
    }

    @Override
    public void setMoreDatas(int itemCount, List<LostAndFound> lostAndFounds) {
        mLostAndFounds.addAll(itemCount, lostAndFounds);
        mAdapter.addMoreItem(lostAndFounds);
    }

    @Override
    public void showProgress() {
        mLoadingView.play();
    }

    @Override
    public void hideProgress() {
        mLoadingView.stop();
    }

    @Override
    public void showToast(String s) {
        toast(s);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lostfound, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_menu_my_lostfound) {
            if (!mIsMe) {
                mPresenter.getSeltLostFound(BmobUser.getCurrentUser(MyUser.class));
                item.setTitle("全部");
            } else {
                mPresenter.refresh();
                item.setTitle("我的");
            }
            mIsMe = !mIsMe;
        } else if (item.getItemId() == R.id.action_menu_add_lostfound) {
            startActivityForResult(new Intent(this, LostFoundAddActivity.class), REQUEST_CODE_NAV_ADD);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_NAV_ADD) {
            mIsMe = false;
            mPresenter.refresh();
        }
    }

    private void showBottomDialog(int position, LostAndFound lostAndFound) {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(R.layout.view_bottom_item_lostfound);
        dialog.show();
        dialog.getWindow().findViewById(R.id.tv_change).setOnClickListener(v -> {
            dialog.dismiss();
            lostAndFound.setIsResolved(!lostAndFound.getIsResolved());
            lostAndFound.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        toast("更改成功");
                        updateItem(lostAndFound, position, true);
                    } else {
                        toast("更改失败");
                    }
                }
            });
        });
        dialog.getWindow().findViewById(R.id.tv_delete).setOnClickListener(v -> {
            dialog.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("确认删除?");
            builder.setPositiveButton("确定", (dialog1, which) -> {
                lostAndFound.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            toast("删除成功");
                            updateItem(lostAndFound, position, false);
                        } else {
                            toast("删除失败");
                        }
                    }
                });
            });
            builder.setNegativeButton("取消", null);
            builder.show();
        });
    }

    /**
     * 更新item
     *
     * @param lostAndFound item
     * @param position     item
     * @param b            true更新,false删除
     */
    private void updateItem(LostAndFound lostAndFound, int position, boolean b) {
        if (b) {
            mLostAndFounds.set(position, lostAndFound);
            mAdapter.updateItem(position, lostAndFound);
        } else {
            mLostAndFounds.remove(position);
            mAdapter.notifyItemRemoved(position);
        }
    }

}
