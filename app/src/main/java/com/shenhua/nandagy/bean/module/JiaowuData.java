package com.shenhua.nandagy.bean.module;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Shenhua on 3/24/2017.
 * e-mail shenhuanet@126.com
 */
public class JiaowuData implements Serializable {

    private static final long serialVersionUID = -2010479720955278317L;
    private String week;
    private List<JiaowuList> list;

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public List<JiaowuList> getList() {
        return list;
    }

    public void setList(List<JiaowuList> list) {
        this.list = list;
    }

    public class JiaowuList implements Serializable {

        private static final long serialVersionUID = 7489274688498686700L;
        private int drawable;
        private String title;
        private String time;
        private String href;

        public int getDrawable() {
            return drawable;
        }

        public void setDrawable(int drawable) {
            this.drawable = drawable;
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
    }

}
