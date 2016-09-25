package com.shenhua.nandagy.bean.bmobbean;

import cn.bmob.v3.BmobObject;

/**
 * 成绩查询实体
 * Created by Shenhua on 9/8/2016.
 */
public class ScoreQuery extends BmobObject {

    private String queryType;
    private String zkzh;
    private String name;
    private String sfzh;
    private String examTime;
    private String examType;

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getZkzh() {
        return zkzh;
    }

    public void setZkzh(String zkzh) {
        this.zkzh = zkzh;
    }
}
