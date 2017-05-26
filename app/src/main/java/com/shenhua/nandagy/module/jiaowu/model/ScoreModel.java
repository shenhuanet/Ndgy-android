package com.shenhua.nandagy.module.jiaowu.model;

import android.content.Context;

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
     * @param context context
     * @param url     url
     * @param num     num
     * @param pwd     pwd
     * @return 登录结果
     */
    int login(Context context, String url, String num, String pwd);

    /**
     * 获取考试成绩
     *
     * @param context context
     * @return 结果
     */
    ExamScore getExamScore(Context context);

    /**
     * 获取等级考试成绩
     *
     * @param context context
     * @return 结果
     */
    List<GradeScore> getGradeScore(Context context);
}
