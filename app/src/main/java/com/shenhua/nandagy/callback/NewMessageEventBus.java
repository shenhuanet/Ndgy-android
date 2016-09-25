package com.shenhua.nandagy.callback;

/**
 * 新消息提醒相关的事件总线
 * Created by shenhua on 8/30/2016.
 */
public class NewMessageEventBus {

    private boolean show;
    private int type;

    public NewMessageEventBus(boolean show, int type) {
        this.show = show;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public boolean unShow() {
        return show;
    }
}
