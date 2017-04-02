package com.shenhua.nandagy.view;

import com.shenhua.commonlibs.mvp.BaseView;
import com.shenhua.nandagy.bean.scorebean.ExamScore;
import com.shenhua.nandagy.bean.scorebean.GradeScore;

import java.util.List;

/**
 * Created by shenhua on 4/1/2017.
 * Email shenhuanet@126.com
 */
public interface ScoreView extends BaseView {

    void reBinding(String error);

    void exit();

    void getScoreFailed(String msg);

    void onLoginSuccess(String[] numName);

    void showExamScore(ExamScore examScore);

    void showGradeScore(List<GradeScore> gradeScores);

    void showProgress(String msg);

}
