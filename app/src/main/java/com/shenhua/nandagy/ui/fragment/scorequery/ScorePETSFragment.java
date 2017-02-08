package com.shenhua.nandagy.ui.fragment.scorequery;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseScoreQueryVerfyFragment;
import com.shenhua.nandagy.bean.scorebean.ScoreData;
import com.shenhua.nandagy.service.HttpService;
import com.shenhua.nandagy.view.ScoreQueryView;

/**
 * 英语三级
 * Created by Shenhua on 9/7/2016.
 */
public class ScorePETSFragment extends BaseScoreQueryVerfyFragment implements ScoreQueryView {

    private static ScorePETSFragment instance = null;

    public static ScorePETSFragment newInstance() {
        if (instance == null) {
            instance = new ScorePETSFragment();
        }
        return instance;
    }

    @Override
    public int getViewLayoutId() {
        return R.layout.frag_score_pets;
    }

    @Override
    public String getVerfyCodeUrl() {
        return HttpService.VERFY_CODE_PETS;
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
