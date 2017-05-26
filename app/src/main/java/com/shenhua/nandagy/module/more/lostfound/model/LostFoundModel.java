package com.shenhua.nandagy.module.more.lostfound.model;

import com.shenhua.nandagy.bean.bmobbean.MyUser;
import com.shenhua.nandagy.callback.LostFoundLoaderCallback;

/**
 * Created by shenhua on 5/22/2017.
 * Email shenhuanet@126.com
 */
public interface LostFoundModel<T> {

    /**
     * 初始加载数据
     *
     * @param callback 回调
     */
    void toGetLostFoundData(LostFoundLoaderCallback<T> callback);

    /**
     * 加载更多数据
     *
     * @param itemCount 已有item数目
     * @param callback  回调
     */
    void toLoadMoreLostFoundData(int itemCount, LostFoundLoaderCallback<T> callback);

    /**
     * 查询自己发布的失物招领信息
     *
     * @param user     user
     * @param callback 回调
     */
    void getSelfLostFound(MyUser user, LostFoundLoaderCallback<T> callback);
}
