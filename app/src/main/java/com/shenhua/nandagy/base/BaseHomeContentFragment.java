package com.shenhua.nandagy.base;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.shenhua.commonlibs.mvp.BaseMvpFragment;
import com.shenhua.commonlibs.utils.BusBooleanEvent;
import com.shenhua.commonlibs.utils.BusProvider;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.adapter.HomeDataAdapter;
import com.shenhua.nandagy.bean.HomeData;
import com.shenhua.nandagy.presenter.HomePresenter;
import com.shenhua.nandagy.ui.activity.ContentDetailActivity;
import com.shenhua.nandagy.view.HomeView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页两块fragment 基类
 * Created by shenhua on 8/30/2016.
 */
public abstract class BaseHomeContentFragment extends BaseMvpFragment<HomePresenter, HomeView> implements HomeView {

    @BindView(R.id.recyclerview)
    public RecyclerView mRecyclerView;
    @BindView(R.id.layout_empty)
    public LinearLayout mEmptyLayout;
    @BindView(R.id.progress_bar)
    public ProgressBar mProgressBar;
    protected HomePresenter presenter;

    @Override
    public void updateList(final List<HomeData> datas) {
        if (datas == null) {
            mEmptyLayout.setVisibility(View.VISIBLE);
        } else {
            mEmptyLayout.setVisibility(View.INVISIBLE);
            final HomeDataAdapter adapter = new HomeDataAdapter(getContext(), datas);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener((view1, position, data) -> navToDetail(view1, data));
//                adapter.setOnItemClickListener((view1, position) -> navToDetail(view1, adapter.getDatas().get(position)));
        }
    }

    private void navToDetail(View view, HomeData homeData) {
        Intent intent = new Intent(getActivity(), ContentDetailActivity.class);
        intent.putExtra("photo", homeData.getImgUrl());
        intent.putExtra("title", homeData.getTitle());
        intent.putExtra("time", homeData.getTime());
//        sceneTransitionTo(intent, 0, view, R.id.iv_home_list_img, "photos");
    }

    @Override
    public void showToast(final String msg) {
        toast(msg);
        mEmptyLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgress() {
        BusProvider.getInstance().post(new BusBooleanEvent(true));
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        BusProvider.getInstance().post(new BusBooleanEvent(false));
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.layout_empty_reload)
    void onClick(View v) {
        onReload();
    }

    public void onReload() {
        presenter.execute();
    }

}
