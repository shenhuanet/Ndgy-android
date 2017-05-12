package com.shenhua.nandagy.model;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.nandagy.bean.bmobbean.SchoolCircle;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by MVPHelper on 2016/10/06
 */
public class CircleModelImpl implements CircleModel<List<SchoolCircle>> {

    @Override
    public void toGetCircleData(HttpCallback<List<SchoolCircle>> callback) {
        callback.onPreRequest();
        BmobQuery<SchoolCircle> query = new BmobQuery<>();
//        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        query.setLimit(50);
        query.include("userzone.user");
        query.order("-createdAt");// 降序排列
        query.findObjects(new FindListener<SchoolCircle>() {
            @Override
            public void done(List<SchoolCircle> list, BmobException e) {
                callback.onPostRequest();
                if (e == null) {
                    callback.onSuccess(list);
                } else {
                    callback.onError(e.getMessage());
                }
            }
        });
    }
}