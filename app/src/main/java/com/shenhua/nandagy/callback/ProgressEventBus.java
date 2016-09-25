package com.shenhua.nandagy.callback;

/**
 * 进度条相关的事件总线
 * Created by shenhua on 8/30/2016.
 */
public class ProgressEventBus {

    private boolean show;

    public ProgressEventBus(boolean show) {
        this.show = show;
    }

    public boolean show() {
        return show;
    }
}
