package com.shenhua.nandagy.frag.score;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseScoreQueryVerfyFragment;
import com.shenhua.nandagy.service.HttpService;

/**
 * 计算机二级
 * Created by Shenhua on 9/7/2016.
 */
public class ScoreComputerFragment extends BaseScoreQueryVerfyFragment {

    private static ScoreComputerFragment instance = null;

    public static ScoreComputerFragment newInstance() {
        if (instance == null) {
            instance = new ScoreComputerFragment();
        }
        return instance;
    }

    @Override
    public int getViewLayoutId() {
        return R.layout.frag_score_computer;
    }

    @Override
    public String getVerfyCodeUrl() {
        return HttpService.VERFY_CODE_COMPUTER;
    }

    @Override
    protected void doQuery() {

    }

}
