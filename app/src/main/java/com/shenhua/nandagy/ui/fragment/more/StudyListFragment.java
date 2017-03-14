package com.shenhua.nandagy.ui.fragment.more;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.SparseBooleanArray;
import android.view.View;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.commonlibs.base.BaseFragment;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.adapter.StudyListAdapter;
import com.shenhua.nandagy.bean.StudyListData;
import com.shenhua.nandagy.presenter.StudyListPresenter;
import com.shenhua.nandagy.ui.activity.more.StudyAreaDetailActivity;
import com.shenhua.nandagy.view.StudyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.shenhua.nandagy.R.id.recyclerView;

/**
 * Created by Shenhua on 2/9/2017.
 * e-mail shenhuanet@126.com
 */
@ActivityFragmentInject(contentViewId = R.layout.frag_study)
public class StudyListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, OnMoreListener, StudyListView {

    @BindView(recyclerView)
    SuperRecyclerView mSuperRecyclerView;
    private StudyListPresenter mStudyListPresenter;
    private List<StudyListData> mDatas = new ArrayList<>();
    private StudyListAdapter mAdapter;
    private SparseBooleanArray sba = new SparseBooleanArray();

    public static StudyListFragment getInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        StudyListFragment fragment = new StudyListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initView(View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int type = getArguments().getInt("type");

        mSuperRecyclerView.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        mSuperRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSuperRecyclerView.setRefreshListener(this);
        mAdapter = new StudyListAdapter(getContext(), mDatas);
        mSuperRecyclerView.setAdapter(mAdapter);

        mStudyListPresenter = new StudyListPresenter(getContext(), this, type);
        if (!sba.get(type)) {
            mStudyListPresenter.execute();
            sba.put(type, true);
        }

        mAdapter.setOnItemClickListener((view1, position, data) -> {
            ((BaseActivity) getActivity()).sceneTransitionTo(new Intent(getActivity(), StudyAreaDetailActivity.class)
                    .putExtra("title", data.getTitle())
                    .putExtra("href", data.getHref())
                    .putExtra("type", type)
                    .putExtra("position", position), 0, view1, R.id.tv_title, "title");
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onRefresh() {
        mStudyListPresenter.execute();
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {

    }

    @Override
    public void updateList(List<StudyListData> datas) {
        if (datas != null && datas.size() > 0) {
            mDatas.clear();
            mDatas.addAll(datas);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showToast(String msg) {
        toast(msg);
    }

    @Override
    public void showProgress() {
        mSuperRecyclerView.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSuperRecyclerView.setRefreshing(false);
    }
}
