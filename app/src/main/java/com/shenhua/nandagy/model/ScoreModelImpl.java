package com.shenhua.nandagy.model;

import com.shenhua.nandagy.bean.scorebean.ExamScore;
import com.shenhua.nandagy.bean.scorebean.GradeScore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shenhua on 4/1/2017.
 * e-mail shenhuanet@126.com
 */
public class ScoreModelImpl implements ScoreModel {

    public static final int LOGIN_USERNAME_ERROR = 1;
    public static final int LOGIN_PASSWORE_ERROR = 2;
    public static final int LOGIN_REDIRCT_HOME = 3;
    public static final int LOGIN_ERROR = 4;
    public static final int LOGIN_SUCCESS = 45;

    public String[] mNumName;
    private String mRandom;

    @Override
    public int login(String url) {


        return 0;
    }

    @Override
    public ExamScore getExamScore(String url) {
        ExamScore examScore = new ExamScore();
        ExamScore.Overview overview = examScore.new Overview();
        ExamScore.ExamScoreList list = examScore.new ExamScoreList();

        List<ExamScore.ExamScoreList> lists = new ArrayList<>();
        lists.add(list);

        examScore.setOverview(overview);
        examScore.setExamScoreLists(lists);

        return examScore;
    }

    @Override
    public List<GradeScore> getGradeScore(String url) {
        return null;
    }

    public String[] getmNumName() {
        return mNumName == null ? new String[]{"", ""} : mNumName;
    }
}
