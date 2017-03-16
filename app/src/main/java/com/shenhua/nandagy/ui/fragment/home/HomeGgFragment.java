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
 * 公告公示
 * Created by shenhua on 8/29/2016.
 */
@ActivityFragmentInject(contentViewId = R.layout.frag_home_content)
public class HomeGgFragment extends BaseHomeContentFragment {

    private static final String TAG = "HomeGgFragment";

    public static HomeGgFragment newInstance() {
        return new HomeGgFragment();
    }

    @Override
    public void onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, View rootView) {
        ButterKnife.bind(this, rootView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mProgressBar.setVisibility(View.VISIBLE);
        Log.d(TAG, "onCreateView: 公告公示");
        presenter.execute();
    }

    @Override
    public HomePresenter createPresenter() {
        presenter = new HomePresenter(this, "http://www.ndgy.cn");
        return presenter;
    }

}
