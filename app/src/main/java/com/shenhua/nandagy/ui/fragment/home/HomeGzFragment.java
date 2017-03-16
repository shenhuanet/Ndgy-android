package com.shenhua.nandagy.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseHomeContentFragment;
import com.shenhua.nandagy.presenter.HomePresenter;

import butterknife.ButterKnife;

/**
 * 工作动态
 * Created by shenhua on 8/29/2016.
 */
@ActivityFragmentInject(contentViewId = R.layout.frag_home_content)
public class HomeGzFragment extends BaseHomeContentFragment {

    private static final String TAG = "HomeGzFragment";

    public static HomeGzFragment newInstance() {
        return new HomeGzFragment();
    }

    @Override
    public void onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, View rootView) {
        ButterKnife.bind(this, rootView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mProgressBar.setVisibility(View.VISIBLE);
        Log.d(TAG, "onCreateView: 工作动态");
        presenter.execute();
    }

    @Override
    public HomePresenter createPresenter() {
        presenter = new HomePresenter(this, "http://www.daxues.cn/xuexi/yingyu/");
        return presenter;
    }
}
