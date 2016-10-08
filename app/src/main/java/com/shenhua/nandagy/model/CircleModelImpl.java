package com.shenhua.nandagy.model;

import com.shenhua.nandagy.callback.HttpCallback;
import com.shenhua.nandagy.manager.HttpManager;

/**
 * Created by MVPHelper on 2016/10/06
 */
public class CircleModelImpl implements CircleModel {

    private HttpManager httpManager = HttpManager.getInstance();

    @Override
    public void toGetCircleData(HttpCallback callback) {
        // TODO: 10/6/2016 获取数据
    }
}