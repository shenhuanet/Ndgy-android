package com.shenhua.nandagy.module.more.circle.view;

import com.shenhua.commonlibs.mvp.BaseView;
import com.shenhua.nandagy.bean.bmobbean.SchoolCircle;
import com.shenhua.nandagy.utils.DataLoadType;

import java.util.List;

/**
 * Created by MVPHelper on 2016/10/06
 */
public interface CircleView extends BaseView {

    void updateList(List<SchoolCircle> datas, @DataLoadType.DataLoadTypeChecker int type);

}