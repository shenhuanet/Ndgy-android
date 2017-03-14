package com.shenhua.nandagy.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.shenhua.commonlibs.mvp.BaseMvpFragment;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.adapter.HomeDataAdapter;
import com.shenhua.nandagy.bean.HomeData;
import com.shenhua.nandagy.callback.ProgressEventBus;
import com.shenhua.nandagy.presenter.HomePresenter;
import com.shenhua.nandagy.ui.activity.ContentDetailActivity;
import com.shenhua.nandagy.view.HomeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 首页两块fragment 基类
 * Created by shenhua on 8/30/2016.
 */
public abstract class BaseHomeContentFragment extends BaseMvpFragment<HomePresenter, HomeView> implements HomeView {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.layout_empty)
    LinearLayout mEmptyLayout;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    protected View view;
    protected HomePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.frag_home_content, container, false);
            ButterKnife.bind(this, view);
            mRecyclerView.setNestedScrollingEnabled(false);
            mProgressBar.setVisibility(View.VISIBLE);
            init();
        }
        ViewGroup group = (ViewGroup) view.getParent();
        if (group != null)
            group.removeView(view);
        return view;
    }

    protected abstract void init();

    @Override
    public HomePresenter createPresenter() {
        presenter = new HomePresenter(this, "http://www.daxues.cn/xuexi/yingyu/");
        return presenter;
    }

    @Override
    public void updateList(final List<HomeData> datas) {
        getActivity().runOnUiThread(() -> {
            if (datas == null) {
                mEmptyLayout.setVisibility(View.VISIBLE);
            } else {
                mEmptyLayout.setVisibility(View.GONE);
                final HomeDataAdapter adapter = new HomeDataAdapter(getContext(), datas);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                mRecyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener((view1, position, data) -> navToDetail(view1, data));
//                adapter.setOnItemClickListener((view1, position) -> navToDetail(view1, adapter.getDatas().get(position)));
            }
        });
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
        getActivity().runOnUiThread(() -> {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            mEmptyLayout.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void showProgress() {
        getActivity().runOnUiThread(() -> {
            EventBus.getDefault().post(new ProgressEventBus(true));
            mProgressBar.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void hideProgress() {
        getActivity().runOnUiThread(() -> {
            EventBus.getDefault().post(new ProgressEventBus(false));
            mProgressBar.setVisibility(View.GONE);
        });
    }

    @OnClick(R.id.layout_empty_reload)
    void onClick(View v) {
        onReload();
    }

    public void onReload() {
        System.out.println("shenhua sout:" + "重新加载");
        presenter.execute();
    }

}
