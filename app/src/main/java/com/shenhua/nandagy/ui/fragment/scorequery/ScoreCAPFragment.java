package com.shenhua.nandagy.ui.fragment.scorequery;

import android.view.View;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseScoreQueryVerfyFragment;
import com.shenhua.nandagy.service.HttpService;

import butterknife.ButterKnife;

/**
 * 会计从业资格证
 * Created by Shenhua on 9/7/2016.
 */
@ActivityFragmentInject(contentViewId = R.layout.frag_score_cap)
public class ScoreCAPFragment extends BaseScoreQueryVerfyFragment {

    public static ScoreCAPFragment newInstance() {
        return new ScoreCAPFragment();
    }

    @Override
    public void initView(View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @Override
    public String getVerifyCodeUrl() {
        return HttpService.VERFY_CODE_CAP;
    }

    @Override
    protected void doQuery() {

    }

}
