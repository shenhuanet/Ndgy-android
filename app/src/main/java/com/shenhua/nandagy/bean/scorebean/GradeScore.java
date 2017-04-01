package com.shenhua.nandagy.bean.scorebean;

import java.io.Serializable;

/**
 * 等级考试成绩
 * Created by shenhua on 4/1/2017.
 * Email shenhuanet@126.com
 */
public class GradeScore implements Serializable {

    private static final long serialVersionUID = -3161144166106432676L;
    private String name;
    private String year;
    private String term;
    private String grade;
    private String num;
    private String date;

    public GradeScore(String name, String year, String term, String grade, String num, String date) {
        this.name = name;
        this.year = year;
        this.term = term;
        this.grade = grade;
        this.num = num;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
