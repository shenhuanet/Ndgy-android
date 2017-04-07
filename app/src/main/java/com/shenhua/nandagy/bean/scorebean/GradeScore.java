package com.shenhua.nandagy.bean.scorebean;

import java.io.Serializable;

/**
 * 等级考试成绩
 * Created by shenhua on 4/1/2017.
 * Email shenhuanet@126.com
 */
public class GradeScore implements Serializable {

    private static final long serialVersionUID = -3161144166106432676L;
    private String name;//2
    private String year;//0
    private String term;//1
    private String grade;//5
    private String num;//3
    private String date;//4
    private String listen;//6
    private String read;//7
    private String text;//8

    public GradeScore() {
    }

    public GradeScore(String name, String year, String term, String grade, String num, String date, String listen, String read, String text) {
        this.name = name;
        this.year = year;
        this.term = term;
        this.grade = grade;
        this.num = num;
        this.date = date;
        this.listen = listen;
        this.read = read;
        this.text = text;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year == null ? "" : year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTerm() {
        return term == null ? "" : term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getGrade() {
        return grade == null ? " " : grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getNum() {
        return num == null ? "" : num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDate() {
        return date == null ? "" : date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getListen() {
        return listen == null ? "" : listen;
    }

    public void setListen(String listen) {
        this.listen = listen;
    }

    public String getRead() {
        return read == null ? "" : read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getText() {
        return text == null ? "" : text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
