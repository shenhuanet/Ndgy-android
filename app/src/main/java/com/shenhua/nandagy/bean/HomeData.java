package com.shenhua.nandagy.bean;

import java.io.Serializable;

/**
 * 首页数据
 * Created by shenhua on 8/30/2016.
 */
public class HomeData implements Serializable {

    private static final long serialVersionUID = 7992443113639534614L;
    private String title, detail, time, href, imgUrl;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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
