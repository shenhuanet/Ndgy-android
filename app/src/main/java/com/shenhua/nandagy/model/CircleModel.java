package com.shenhua.nandagy.model;

import com.shenhua.nandagy.callback.CircleLoaderCallback;

/**
 * Created by MVPHelper on 2016/10/06
 */
public interface CircleModel<T> {

    void toGetCircleData(CircleLoaderCallback callback);

    /**
     * 加载更多数据
     *
     * @param itemCount 已有item数目
     * @param callback  回调
     */
    void toLoadMoreCircleData(int itemCount, CircleLoaderCallback callback);
}