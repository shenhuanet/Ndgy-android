package com.shenhua.nandagy.view;

import com.shenhua.nandagy.bean.ScoreData;

/**
 * 查询成绩视图
 * Created by shenhua on 9/8/2016.
 */
public interface ScoreQueryView extends BaseView {

    void onGetQueryResult(ScoreData data);

}
