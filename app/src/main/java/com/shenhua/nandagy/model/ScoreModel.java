package com.shenhua.nandagy.model;

import com.shenhua.nandagy.bean.scorebean.ExamScore;
import com.shenhua.nandagy.bean.scorebean.GradeScore;

import java.util.List;

/**
 * Created by shenhua on 4/1/2017.
 * Email shenhuanet@126.com
 */
public interface ScoreModel {

    /**
     * 登录教务系统
     *
     * @param url url
     * @return 登录结果
     */
    int login(String url);

    /**
     * 获取考试成绩
     *
     * @param url url
     */
    ExamScore getExamScore(String url);

    /**
     * 获取等级考试成绩
     *
     * @param url url
     */
    List<GradeScore> getGradeScore(String url);
}
