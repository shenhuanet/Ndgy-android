package com.shenhua.nandagy.bean.scorebean;

import java.io.Serializable;

/**
 * 英语四六级结果实体
 * Created by Shenhua on 9/25/2016.
 */
public class ScoreCETBean implements Serializable{

    private static final long serialVersionUID = 1843733072865690798L;
    private String examType;
    private String name;
    private String school;
    private String examNum;
    private String examTime;
    private String listen;
    private String reading;
    private String compos;
    private String sum;

    public String getCompos() {
        return compos;
    }

    public void setCompos(String compos) {
        this.compos = compos;
    }

    public String getExamNum() {
        return examNum;
    }

    public void setExamNum(String examNum) {
        this.examNum = examNum;
    }

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

    public String getListen() {
        return listen;
    }

    public void setListen(String listen) {
        this.listen = listen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReading() {
        return reading;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }
}
