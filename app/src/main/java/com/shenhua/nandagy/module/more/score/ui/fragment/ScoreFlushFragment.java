package com.shenhua.nandagy.module.more.score.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseScoreQueryVerfyFragment;
import com.shenhua.nandagy.service.HttpService;

import butterknife.ButterKnife;

/**
 * 嵌入式技工
 * Created by Shenhua on 9/7/2016.
 */
@ActivityFragmentInject(contentViewId = R.layout.frag_score_flush)
public class ScoreFlushFragment extends BaseScoreQueryVerfyFragment {

    public static ScoreFlushFragment newInstance() {
        return new ScoreFlushFragment();
    }

    @Override
    public void onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @Override
    public String getVerifyCodeUrl() {
        return HttpService.VERFY_CODE_FLUSH;
    }

    @Override
    protected void doQuery() {

    }
}
