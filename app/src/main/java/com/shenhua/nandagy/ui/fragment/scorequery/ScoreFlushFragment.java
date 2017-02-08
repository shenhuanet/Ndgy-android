package com.shenhua.nandagy.ui.fragment.scorequery;

import android.view.View;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseScoreQueryVerfyFragment;
import com.shenhua.nandagy.service.HttpService;

/**
 * 嵌入式技工
 * Created by Shenhua on 9/7/2016.
 */
public class ScoreFlushFragment extends BaseScoreQueryVerfyFragment {

    private static ScoreFlushFragment instance = null;
    protected View view;

    public static ScoreFlushFragment newInstance() {
        if (instance == null) {
            instance = new ScoreFlushFragment();
        }
        return instance;
    }

    @Override
    public int getViewLayoutId() {
        return R.layout.frag_score_flush;
    }

    @Override
    public String getVerfyCodeUrl() {
        return HttpService.VERFY_CODE_FLUSH;
    }

    @Override
    protected void doQuery() {

    }
}
