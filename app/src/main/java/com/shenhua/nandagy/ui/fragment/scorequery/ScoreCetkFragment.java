package com.shenhua.nandagy.ui.fragment.scorequery;

import android.view.View;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseScoreQueryVerfyFragment;
import com.shenhua.nandagy.service.HttpService;

import butterknife.ButterKnife;

/**
 * 四六级口语  暂不可用
 * Created by Shenhua on 9/7/2016.
 */
@ActivityFragmentInject(contentViewId = R.layout.frag_score_cetk)
public class ScoreCetkFragment extends BaseScoreQueryVerfyFragment {

    public static ScoreCetkFragment newInstance() {
        return new ScoreCetkFragment();
    }

    @Override
    public void initView(View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @Override
    public String getVerifyCodeUrl() {
        return HttpService.VERFY_CODE_CETK;
    }

    @Override
    protected void doQuery() {

    }

}
