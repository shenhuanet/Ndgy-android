package com.shenhua.nandagy.bean.common;

import java.io.Serializable;

/**
 * 内容详情跳转时传递参数
 * Created by Shenhua on 3/24/2017.
 * e-mail shenhuanet@126.com
 */
public class ContentPassesData implements Serializable {

    private static final long serialVersionUID = 1973445603082576916L;
    private int type;
    private Object image;
    private String title;
    private String time;
    private String url;

    public ContentPassesData(int type, String title, Object image, String time, String url) {
        this.type = type;
        this.title = title;
        this.image = image;
        this.time = time;
        this.url = url;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
