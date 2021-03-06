package com.shenhua.nandagy.bean.module;

/**
 * 学习列表实体
 * Created by Shenhua on 2/9/2017.
 * e-mail shenhuanet@126.com
 */
public class StudyListData {

    private String title;
    private String description;
    private String href;
    private String time;
    private String content;

    public StudyListData(String title, String href, String description) {
        this.title = title;
        this.href = href;
        this.description = description;
    }

    public StudyListData(String time, String content) {
        this.time = time;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
