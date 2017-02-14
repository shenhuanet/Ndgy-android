package com.shenhua.nandagy.presenter;

import android.content.Context;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.nandagy.bean.StudyListData;
import com.shenhua.nandagy.model.StudyModel;
import com.shenhua.nandagy.model.StudyModelImpl;
import com.shenhua.nandagy.view.StudyListView;

import java.util.List;

/**
 * Created by Shenhua on 2/9/2017.
 * e-mail shenhuanet@126.com
 */
public class StudyListPresenter implements HttpCallback<List<StudyListData>> {

    private StudyModel<List<StudyListData>> studyModel;
    private StudyListView studyListView;
    private Context context;
    private int type;

    public StudyListPresenter(Context context, StudyListView studyListView, int type) {
        this.context = context;
        this.studyListView = studyListView;
        this.type = type;
        studyModel = new StudyModelImpl();
    }

    public void execute() {
        studyModel.toGetList(context, type, this);
    }

    @Override
    public void onPreRequest() {
        studyListView.showProgress();
    }

    @Override
    public void onSuccess(List<StudyListData> data) {
        studyListView.updateList(data);
    }

    @Override
    public void onError(String errorInfo) {
        studyListView.showToast(errorInfo);
    }

    @Override
    public void onPostRequest() {
        studyListView.hideProgress();
    }

}
