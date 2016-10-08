package com.shenhua.nandagy.frag.score;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseScoreQueryVerfyFragment;
import com.shenhua.nandagy.service.HttpService;

/**
 * 全国外语水平
 * Created by Shenhua on 9/7/2016.
 */
public class ScoreForeignFragment extends BaseScoreQueryVerfyFragment {

    private static ScoreForeignFragment instance = null;

    public static ScoreForeignFragment newInstance() {
        if (instance == null) {
            instance = new ScoreForeignFragment();
        }
        return instance;
    }

    @Override
    public int getViewLayoutId() {
        return R.layout.frag_score_foreign;
    }

    @Override
    public String getVerfyCodeUrl() {
        return HttpService.VERFY_CODE_FOREIGN;
    }

    @Override
    protected void doQuery() {

    }

}
