package com.shenhua.nandagy.frag;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseScoreQueryFragment;

/**
 * 普通话水平
 * Created by Shenhua on 9/7/2016.
 */
public class ScoreMandarinFragment extends BaseScoreQueryFragment {

    private static ScoreMandarinFragment instance = null;

    public static ScoreMandarinFragment newInstance() {
        if (instance == null) {
            instance = new ScoreMandarinFragment();
        }
        return instance;
    }

    @Override
    protected int getViewLayoutId() {
        return R.layout.frag_score_mandarin;
    }

    @Override
    protected void doQuery() {

    }

}
