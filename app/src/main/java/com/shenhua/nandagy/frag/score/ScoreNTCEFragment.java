package com.shenhua.nandagy.frag.score;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseScoreQueryVerfyFragment;
import com.shenhua.nandagy.service.HttpService;

/**
 * 教师资格证
 * Created by Shenhua on 9/7/2016.
 */
public class ScoreNTCEFragment extends BaseScoreQueryVerfyFragment {

    private static ScoreNTCEFragment instance = null;

    public static ScoreNTCEFragment newInstance() {
        if (instance == null) {
            instance = new ScoreNTCEFragment();
        }
        return instance;
    }

    @Override
    public int getViewLayoutId() {
        return R.layout.frag_score_ntce;
    }

    @Override
    public String getVerfyCodeUrl() {
        return HttpService.VERFY_CODE_NTCE;
    }

    @Override
    protected void doQuery() {

    }

}
