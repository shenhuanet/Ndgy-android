package com.shenhua.nandagy.frag.score;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseScoreQueryVerfyFragment;
import com.shenhua.nandagy.service.HttpService;

/**
 * 四六级口语  暂不可用
 * Created by Shenhua on 9/7/2016.
 */
public class ScoreCetkFragment extends BaseScoreQueryVerfyFragment {

    private static ScoreCetkFragment instance = null;

    public static ScoreCetkFragment newInstance() {
        if (instance == null) {
            instance = new ScoreCetkFragment();
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
