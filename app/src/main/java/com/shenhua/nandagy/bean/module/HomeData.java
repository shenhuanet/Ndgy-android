package com.shenhua.nandagy.bean.module;

import android.text.TextUtils;

import com.shenhua.nandagy.service.Constants;

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
        if (TextUtils.isEmpty(detail))
            this.detail = "暂无描述";
        else
            this.detail = detail + "...";
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
        this.imgUrl = Constants.HOME_URL_GZDT + imgUrl;
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
