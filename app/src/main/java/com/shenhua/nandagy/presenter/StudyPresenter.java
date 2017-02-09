package com.shenhua.nandagy.presenter;

import com.shenhua.nandagy.bean.StudyListData;
import com.shenhua.nandagy.callback.HttpCallback;
import com.shenhua.nandagy.model.StudyModel;
import com.shenhua.nandagy.model.StudyModelImpl;
import com.shenhua.nandagy.view.StudyListView;

import java.util.List;

/**
 * Created by Shenhua on 2/9/2017.
 * e-mail shenhuanet@126.com
 */
public class StudyPresenter implements HttpCallback<List<StudyListData>> {

    private StudyModel<List<StudyListData>> studyModel;
    private StudyListView studyListView;
    private int type;

    public StudyPresenter(StudyListView studyListView, int type) {
        this.studyListView = studyListView;
        this.type = type;
        studyModel = new StudyModelImpl();
    }

    public void execute() {
        studyModel.toGetList(type, this);
    }

    @Override
    public void onPreRequest() {

    }

    @Override
    public void onSuccess(List<StudyListData> data) {

    }

    @Override
    public void onError(String errorInfo) {

    }

    @Override
    public void onPostRequest() {

    }

}
