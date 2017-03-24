package com.shenhua.nandagy.bean;

import com.shenhua.nandagy.service.ContentDetailType;

import java.io.Serializable;

/**
 * Created by Shenhua on 3/24/2017.
 * e-mail shenhuanet@126.com
 */
public class ContentPassesData implements Serializable {

    private static final long serialVersionUID = 1973445603082576916L;
    private ContentDetailType type;
    private Object image;
    private String title;
    private String time;
    private String url;

    public ContentPassesData(ContentDetailType type, String title, Object image, String time, String url) {
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

    public ContentDetailType getType() {
        return type;
    }

    public void setType(ContentDetailType type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
