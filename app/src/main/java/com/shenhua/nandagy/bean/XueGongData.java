package com.shenhua.nandagy.bean;

import java.io.Serializable;

/**
 * 学工处数据实体
 * Created by shenhua on 8/31/2016.
 */
public class XueGongData implements Serializable {

    private static final long serialVersionUID = -353400109541085240L;
    private String title;
    private String time;
    private String href;
    private int newsType;

    public class BannerData implements Serializable {

        private static final long serialVersionUID = -6328108404454839746L;
        private String bTitle;
        private String bImage;
        private String bHref;

        public String getbHref() {
            return bHref;
        }

        public void setbHref(String bHref) {
            this.bHref = bHref;
        }

        public String getbImage() {
            return bImage;
        }

        public void setbImage(String bImage) {
            this.bImage = bImage;
        }

        public String getbTitle() {
            return bTitle;
        }

        public void setbTitle(String bTitle) {
            this.bTitle = bTitle;
        }
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getNewsType() {
        return newsType;
    }

    public void setNewsType(int newsType) {
        this.newsType = newsType;
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
