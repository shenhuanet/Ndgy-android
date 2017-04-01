package com.shenhua.nandagy.presenter;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.nandagy.bean.scorebean.ScoreQueryData;
import com.shenhua.nandagy.model.ScoreQueryModel;
import com.shenhua.nandagy.model.ScoreQueryModelImpl;
import com.shenhua.nandagy.view.ScoreQueryView;

/**
 * 成绩查询代理
 * Created by shenhua on 9/8/2016.
 */
public class ScoreQueryPresenter implements HttpCallback<ScoreQueryData> {

    private ScoreQueryModel<ScoreQueryData> scoreQueryModel;
    private ScoreQueryView scoreQueryView;
    private String url;

    public ScoreQueryPresenter(ScoreQueryView scoreQueryView, String url) {
        this.scoreQueryView = scoreQueryView;
        this.url = url;
        scoreQueryModel = new ScoreQueryModelImpl();
    }

    public void execute() {
        scoreQueryModel.doQuery(url, this);
    }

    @Override
    public void onPreRequest() {
        scoreQueryView.showProgress();
    }

    @Override
    public void onSuccess(ScoreQueryData data) {
        scoreQueryView.onGetQueryResult(data);
    }

    @Override
    public void onError(String errorInfo) {
        scoreQueryView.showToast(errorInfo);
    }

    @Override
    public void onPostRequest() {
        scoreQueryView.hideProgress();
    }
}
