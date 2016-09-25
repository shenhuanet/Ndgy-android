package com.shenhua.nandagy.frag;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseScoreQueryVerfyFragment;
import com.shenhua.nandagy.bean.ScoreData;
import com.shenhua.nandagy.service.HttpService;
import com.shenhua.nandagy.view.ScoreQueryView;

/**
 * 剑桥商务英语
 * Created by Shenhua on 9/7/2016.
 */
public class ScoreBecFragment extends BaseScoreQueryVerfyFragment implements ScoreQueryView {

    private static ScoreBecFragment instance = null;

    public static ScoreBecFragment newInstance() {
        if (instance == null) {
            instance = new ScoreBecFragment();
        }
        return instance;
    }

    @Override
    public int getViewLayoutId() {
        return R.layout.frag_score_bec;
    }

    @Override
    public String getVerfyCodeUrl() {
        return HttpService.VERFY_CODE_BEC;
    }

    @Override
    protected void doQuery() {

    }

    @Override
    public void onGetQueryResult(ScoreData data) {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

}
