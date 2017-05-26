package com.shenhua.nandagy.module.more.study.presenter;

import android.content.Context;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.nandagy.bean.module.StudyListData;
import com.shenhua.nandagy.module.more.study.model.StudyModelImpl;
import com.shenhua.nandagy.module.more.study.view.StudyDetailView;

/**
 * Created by shenhua on 2/14/2017.
 * Email shenhuanet@126.com
 */
public class StudyDetailPresenter implements HttpCallback<StudyListData> {

    private StudyModelImpl studyModel;
    private StudyDetailView studyDetailView;
    private Context context;
    private int type;

    public StudyDetailPresenter(Context context, StudyDetailView studyDetailView, int type) {
        this.context = context;
        this.studyDetailView = studyDetailView;
        this.type = type;
        studyModel = new StudyModelImpl();
    }

    public void execute(int position, String url) {
        studyModel.toGetDetail(context, type, position, url, this);
    }

    @Override
    public void onPreRequest() {
        studyDetailView.showProgress();
    }

    @Override
    public void onSuccess(StudyListData data) {
        studyDetailView.showDetail(data);
    }

    @Override
    public void onError(String errorInfo) {
        studyDetailView.showToast(errorInfo);
    }

    @Override
    public void onPostRequest() {
        studyDetailView.hideProgress();
    }
}
