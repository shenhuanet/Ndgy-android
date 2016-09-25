package com.shenhua.nandagy.base;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.activity.ContentDetailActivity;
import com.shenhua.nandagy.adapter.HomeDataAdapter;
import com.shenhua.nandagy.bean.HomeData;
import com.shenhua.nandagy.callback.OnItemClickListener;
import com.shenhua.nandagy.callback.ProgressEventBus;
import com.shenhua.nandagy.presenter.HomePresenter;
import com.shenhua.nandagy.manager.HttpManager;
import com.shenhua.nandagy.view.HomeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 首页两块fragment 基类
 * Created by shenhua on 8/30/2016.
 */
public abstract class BaseHomeContentFragment extends Fragment implements HomeView {

    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.layout_empty)
    LinearLayout mEmptyLayout;
    @Bind(R.id.progress_bar)
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
    public void updateList(final List<HomeData> datas, @HttpManager.DataLoadType.DataLoadTypeChecker int type) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (datas == null) {
                    mEmptyLayout.setVisibility(View.VISIBLE);
                } else {
                    mEmptyLayout.setVisibility(View.GONE);
                    final HomeDataAdapter adapter = new HomeDataAdapter(getContext(), datas);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    mRecyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void OnItemClick(View view, int position) {
                            navToDetail(view, adapter.getDatas().get(position));
                        }
                    });
                }
            }
        });
    }

    private void navToDetail(View view, HomeData homeData) {
        Intent intent = new Intent(getActivity(), ContentDetailActivity.class);
        intent.putExtra("photo", homeData.getImgUrl());
        intent.putExtra("title", homeData.getTitle());
        intent.putExtra("time", homeData.getTime());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(getActivity(), view.findViewById(R.id.iv_home_list_img), "photos");
            getActivity().startActivity(intent, options.toBundle());
        } else {
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
        }
    }

    @Override
    public void showToast(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                mEmptyLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void showProgress() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new ProgressEventBus(true));
                mProgressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideProgress() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new ProgressEventBus(false));
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    @OnClick(R.id.layout_empty_reload)
    void onClick(View v) {
        onRead();
    }

    public void onRead() {
        System.out.println("shenhua sout:" + "重新加载");
        presenter.execute();
    }

}
