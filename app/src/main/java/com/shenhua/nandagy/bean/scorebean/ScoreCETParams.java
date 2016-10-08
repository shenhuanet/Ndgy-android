package com.shenhua.nandagy.bean.scorebean;

/**
 * 英语四六级查询参数
 * Created by Shenhua on 9/23/2016.
 */
public class ScoreCETParams {

    private String name;
    private String zkzh;

    public ScoreCETParams(String name, String zkzh) {
        this.name = name;
        this.zkzh = zkzh;
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
