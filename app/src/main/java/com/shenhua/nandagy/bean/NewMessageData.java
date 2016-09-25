package com.shenhua.nandagy.bean;

/**
 * 消息中心实体类
 * Created by Shenhua on 9/4/2016.
 */
public class NewMessageData {

    private String time;
    private String title;
    private String content;
    private String from;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
