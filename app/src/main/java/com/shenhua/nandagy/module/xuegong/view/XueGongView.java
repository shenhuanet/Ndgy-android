package com.shenhua.nandagy.module.xuegong.view;

import com.shenhua.commonlibs.mvp.BaseView;
import com.shenhua.nandagy.bean.module.XueGongData;

/**
 * 学工视图
 * Created by shenhua on 8/31/2016.
 */
public interface XueGongView extends BaseView {

    void updateList(XueGongData lists);
}
