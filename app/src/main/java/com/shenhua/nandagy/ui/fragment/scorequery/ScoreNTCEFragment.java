package com.shenhua.nandagy.ui.fragment.scorequery;

import android.view.View;

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
    public void initView(View rootView) {
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
