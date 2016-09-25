package com.shenhua.nandagy.view;

import com.shenhua.nandagy.bean.HomeData;
import com.shenhua.nandagy.manager.HttpManager;

import java.util.List;

/**
 * 主页视图
 * Created by shenhua on 8/29/2016.
 */
public interface HomeView extends BaseView {

    void updateList(List<HomeData> datas, @HttpManager.DataLoadType.DataLoadTypeChecker int type);

}
