package com.shenhua.nandagy.model;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.commonlibs.mvp.HttpManager;
import com.shenhua.nandagy.bean.bmobbean.SchoolCircle;

import java.util.List;

/**
 * Created by MVPHelper on 2016/10/06
 */
public class CircleModelImpl implements CircleModel<List<SchoolCircle>> {

    private HttpManager httpManager = HttpManager.getInstance();

    @Override
    public void toGetCircleData(HttpCallback callback) {
        // TODO: 10/6/2016 获取数据
    }
}