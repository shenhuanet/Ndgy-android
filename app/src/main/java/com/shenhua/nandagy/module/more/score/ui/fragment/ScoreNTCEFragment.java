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
 * 教师资格证
 * Created by Shenhua on 9/7/2016.
 */
@ActivityFragmentInject(contentViewId = R.layout.frag_score_ntce)
public class ScoreNTCEFragment extends BaseScoreQueryVerfyFragment {

    public static ScoreNTCEFragment newInstance() {
        return new ScoreNTCEFragment();
    }

    @Override
    public void onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @Override
    public String getVerifyCodeUrl() {
        return HttpService.VERFY_CODE_NTCE;
    }

    @Override
    protected void doQuery() {

    }

}
