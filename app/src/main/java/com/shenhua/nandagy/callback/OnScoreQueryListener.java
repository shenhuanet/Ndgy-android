package com.shenhua.nandagy.callback;

/**
 * 查询成绩结果回调
 * Created by Shenhua on 9/23/2016.
 */
public interface OnScoreQueryListener {

    void onQuerySuccess(Object result);

    void onQueryFailed(int errorCode,String msg);
}
