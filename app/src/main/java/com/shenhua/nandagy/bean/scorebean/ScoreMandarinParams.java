package com.shenhua.nandagy.bean.scorebean;

/**
 * 普通话查询参数
 * Created by Shenhua on 9/23/2016.
 */
public class ScoreMandarinParams {

    private String zkzh;
    private String name;
    private String zjh;

    public ScoreMandarinParams(String zkzh, String name, String zjh) {
        this.zkzh = zkzh;
        this.name = name;
        this.zjh = zjh;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZkzh() {
        return zkzh;
    }

    public void setZkzh(String zkzh) {
        this.zkzh = zkzh;
    }
}
