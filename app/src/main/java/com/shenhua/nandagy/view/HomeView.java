package com.shenhua.nandagy.view;

import com.shenhua.nandagy.bean.HomeData;

import java.util.List;

/**
 * 主页视图
 * Created by shenhua on 8/29/2016.
 */
public interface HomeView extends com.shenhua.commonlibs.mvp.BaseView {

    void showToast(String msg);

    void updateList(List<HomeData> datas);

}
