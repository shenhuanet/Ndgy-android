package com.shenhua.nandagy.frag;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseScoreQueryVerfyFragment;
import com.shenhua.nandagy.service.HttpService;

/**
 * 会计从业资格证
 * Created by Shenhua on 9/7/2016.
 */
public class ScoreCAPFragment extends BaseScoreQueryVerfyFragment {

    private static ScoreCAPFragment instance = null;

    public static ScoreCAPFragment newInstance() {
        if (instance == null) {
            instance = new ScoreCAPFragment();
        }
        return instance;
    }

    @Override
    public int getViewLayoutId() {
        return R.layout.frag_score_cap;
    }

    @Override
    public String getVerfyCodeUrl() {
        return HttpService.VERFY_CODE_CAP;
    }

    @Override
    protected void doQuery() {

    }

}
