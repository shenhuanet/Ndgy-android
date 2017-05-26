package com.shenhua.nandagy.bean.module;

import com.shenhua.libs.bannerview.BannerData;

import java.io.Serializable;
import java.util.List;

/**
 * 学工处数据实体
 * Created by shenhua on 8/31/2016.
 */
public class XueGongData implements Serializable {

    private BannerData bannerData;
    private List<XuegongListData> xuegongListDatas;

    public BannerData getBannerData() {
        return bannerData;
    }

    public void setBannerData(BannerData bannerData) {
        this.bannerData = bannerData;
    }

    public List<XuegongListData> getXuegongListDatas() {
        return xuegongListDatas;
    }

    public void setXuegongListDatas(List<XuegongListData> xuegongListDatas) {
        this.xuegongListDatas = xuegongListDatas;
    }

    public class XuegongListData implements Serializable{
        private static final long serialVersionUID = -353400109541085240L;
        private String title;
        private String time;
        private String href;
        private int newsType;

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

}
