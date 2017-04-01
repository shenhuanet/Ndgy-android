package com.shenhua.nandagy.model;

/**
 * Created by shenhua on 4/1/2017.
 * Email shenhuanet@126.com
 */
public interface ScoreModel {

    /**
     * 登录教务系统
     *
     * @param url url
     * @return TRUE 登录成功 ，false 登录失败
     */
    boolean login(String url);

    /**
     * 获取考试成绩
     *
     * @param url url
     */
    void getExamScore(String url);

    /**
     * 获取等级考试成绩
     *
     * @param url url
     */
    void getGradeScore(String url);
}
