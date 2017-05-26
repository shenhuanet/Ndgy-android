package com.shenhua.nandagy.module.more.circle.model;

import com.shenhua.nandagy.bean.bmobbean.SchoolCircle;
import com.shenhua.nandagy.callback.CircleLoaderCallback;
import com.shenhua.nandagy.utils.DataLoadType;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by MVPHelper on 2016/10/06
 */
public class CircleModelImpl implements CircleModel<List<SchoolCircle>> {

    @Override
    public void toGetCircleData(CircleLoaderCallback callback) {
        callback.onPreRequest();
        BmobQuery<SchoolCircle> query = new BmobQuery<>();
        query.setLimit(50);
        query.include("userzone.user");
        query.order("-createdAt");
        query.findObjects(new FindListener<SchoolCircle>() {
            @Override
            public void done(List<SchoolCircle> list, BmobException e) {
                callback.onPostRequest();
                if (e == null) {
                    callback.onSuccess(list);
                } else {
                    callback.onError(e.getMessage());
                    callback.onSuccess(list, DataLoadType.TYPE_REFRESH_FAIL);
                }
            }
        });
    }

    @Override
    public void toLoadMoreCircleData(int itemCount, CircleLoaderCallback callback) {
        BmobQuery<SchoolCircle> query = new BmobQuery<>();
        query.setSkip(itemCount);
        query.setLimit(20);
        query.include("userzone.user");
        query.order("-createdAt");
        query.findObjects(new FindListener<SchoolCircle>() {
            @Override
            public void done(List<SchoolCircle> list, BmobException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        callback.onSuccess(list, DataLoadType.TYPE_LOAD_MORE_SUCCESS);
                    } else {
                        callback.onSuccess(list, DataLoadType.TYPE_LOAD_MORE_FAIL);
                    }
                } else {
                    callback.onError(e.getMessage());
                }
            }
        });
    }
}