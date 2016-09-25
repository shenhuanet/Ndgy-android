package com.shenhua.nandagy.frag;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseScoreQueryVerfyFragment;
import com.shenhua.nandagy.service.HttpService;

/**
 * 四六级口语
 * Created by Shenhua on 9/7/2016.
 */
public class ScoreCETKFragment extends BaseScoreQueryVerfyFragment {

    private static ScoreCETKFragment instance = null;

    public static ScoreCETKFragment newInstance() {
        if (instance == null) {
            instance = new ScoreCETKFragment();
        }
        return instance;
    }

    @Override
    public int getViewLayoutId() {
        return R.layout.frag_score_cetk;
    }

    @Override
    public String getVerfyCodeUrl() {
        return HttpService.VERFY_CODE_CETK;
    }

    @Override
    protected void doQuery() {

    }

}
