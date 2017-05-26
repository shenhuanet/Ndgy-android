package com.shenhua.nandagy.module.home.ui.fragement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.shenhua.commonlibs.mvp.BaseMvpFragment;
import com.shenhua.commonlibs.utils.BusBooleanEvent;
import com.shenhua.commonlibs.utils.BusProvider;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.module.home.ui.adapter.HomeDataAdapter;
import com.shenhua.nandagy.bean.common.ContentPassesData;
import com.shenhua.nandagy.bean.module.HomeData;
import com.shenhua.nandagy.module.home.presenter.HomePresenter;
import com.shenhua.nandagy.service.Constants;
import com.shenhua.nandagy.module.acommon.ui.activity.ContentDetailActivity;
import com.shenhua.nandagy.module.home.view.HomeView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页两块fragment 基类
 * Created by shenhua on 8/30/2016.
 */
public abstract class HomeContentBaseFragment extends BaseMvpFragment<HomePresenter, HomeView> implements HomeView {

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
            HomeDataAdapter adapter = new HomeDataAdapter(getContext(), datas);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener((view1, position, data) -> {
                Intent intent = new Intent(getContext(), ContentDetailActivity.class);
                ContentPassesData contentPassesData = new ContentPassesData(
                        Constants.Code.URL_REQUEST_TYPE_HOME,
                        data.getTitle(),
                        data.getImgUrl(),
                        data.getTime(),
                        data.getHref());
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", contentPassesData);
                intent.putExtras(bundle);
                sceneTransitionTo(intent, 0, view1, R.id.iv_home_list_img, "photos");
            });

        }
    }

    @Override
    public void showToast(String msg) {
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
        mEmptyLayout.setVisibility(View.INVISIBLE);
    }

}
