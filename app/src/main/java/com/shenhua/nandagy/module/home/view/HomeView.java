package com.shenhua.nandagy.module.home.view;

import com.shenhua.commonlibs.mvp.BaseView;
import com.shenhua.nandagy.bean.module.HomeData;

import java.util.List;

/**
 * 主页视图
 * Created by shenhua on 8/29/2016.
 */
public interface HomeView extends BaseView {

    void updateList(List<HomeData> datas);

}
