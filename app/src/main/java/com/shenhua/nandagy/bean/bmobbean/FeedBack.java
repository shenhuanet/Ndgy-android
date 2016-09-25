package com.shenhua.nandagy.bean.bmobbean;

import cn.bmob.v3.BmobObject;

/**
 * 反馈意见
 * Created by Shenhua on 9/17/2016.
 */
public class FeedBack extends BmobObject{

    private int option;
    private String content;

    public void setContent(String content) {
        this.content = content;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public String getContent() {
        return content;
    }

    public int getOption() {
        return option;
    }
}
