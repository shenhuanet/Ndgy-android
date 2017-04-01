package com.shenhua.nandagy.ui.fragment.scorequery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseScoreQueryVerfyFragment;
import com.shenhua.nandagy.bean.scorebean.ScoreQueryData;
import com.shenhua.nandagy.service.HttpService;
import com.shenhua.nandagy.view.ScoreQueryView;

import butterknife.ButterKnife;

/**
 * 英语三级
 * Created by Shenhua on 9/7/2016.
 */
@ActivityFragmentInject(contentViewId = R.layout.frag_score_pets)
public class ScorePETSFragment extends BaseScoreQueryVerfyFragment implements ScoreQueryView {

    public static ScorePETSFragment newInstance() {
        return new ScorePETSFragment();
    }

    @Override
    public void onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @Override
    public String getVerifyCodeUrl() {
        return HttpService.VERFY_CODE_PETS;
    }

    @Override
    protected void doQuery() {

    }

    @Override
    public void onGetQueryResult(ScoreQueryData data) {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

}
