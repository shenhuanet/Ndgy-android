package com.shenhua.nandagy.module.more.score.view;

import com.shenhua.commonlibs.mvp.BaseView;
import com.shenhua.nandagy.bean.scorebean.ScoreQueryData;

/**
 * 查询成绩视图
 * Created by shenhua on 9/8/2016.
 */
public interface ScoreQueryView extends BaseView {

    void onGetQueryResult(ScoreQueryData data);

}
