package com.shenhua.nandagy.model;

import com.shenhua.nandagy.bean.bmobbean.LostAndFound;
import com.shenhua.nandagy.bean.bmobbean.MyUser;
import com.shenhua.nandagy.callback.LostFoundLoaderCallback;
import com.shenhua.nandagy.service.Constants;
import com.shenhua.nandagy.utils.DataLoadType;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by shenhua on 5/22/2017.
 * Email shenhuanet@126.com
 */
public class LostFoundModelImpl implements LostFoundModel<List<LostAndFound>> {

    @Override
    public void toGetLostFoundData(LostFoundLoaderCallback<List<LostAndFound>> callback) {
        callback.onPreRequest();
        BmobQuery<LostAndFound> query = new BmobQuery<>();
        query.setLimit(20);
        query.include("user");
        query.addWhereEqualTo("isResolved", false);
        query.order("-createdAt");
        query.findObjects(new FindListener<LostAndFound>() {
            @Override
            public void done(List<LostAndFound> list, BmobException e) {
                callback.onPostRequest();
                if (e == null) {
                    if (list != null && list.size() > 0) {
                        callback.onSuccess(list, DataLoadType.TYPE_REFRESH_SUCCESS);
                    } else {
                        callback.onError(Constants.ErrorInfo.ERROR_NO_DATA);
                    }
                } else {
                    callback.onError(e.getMessage());
                }
            }
        });
    }

    @Override
    public void toLoadMoreLostFoundData(int itemCount, LostFoundLoaderCallback<List<LostAndFound>> callback) {
        BmobQuery<LostAndFound> query = new BmobQuery<>();
        query.setSkip(itemCount);
        query.setLimit(20);
        query.include("user");
        query.addWhereEqualTo("isResolved", false);
        query.order("-createdAt");
        query.findObjects(new FindListener<LostAndFound>() {
            @Override
            public void done(List<LostAndFound> list, BmobException e) {
                if (e == null) {
                    if (list != null && list.size() > 0) {
                        callback.onSuccess(list, DataLoadType.TYPE_LOAD_MORE_SUCCESS);
                    } else {
                        callback.onError(Constants.ErrorInfo.ERROR_NO_MOREDATA);
                    }
                } else {
                    e.printStackTrace();
                    callback.onError(e.getMessage());
                }
            }
        });
    }

    @Override
    public void getSelfLostFound(MyUser user, LostFoundLoaderCallback<List<LostAndFound>> callback) {
        BmobQuery<LostAndFound> query = new BmobQuery<>();
        query.setLimit(50);
        query.addWhereEqualTo("user", user);
        query.include("user");
        query.order("-createdAt");
        query.findObjects(new FindListener<LostAndFound>() {
            @Override
            public void done(List<LostAndFound> list, BmobException e) {
                if (e == null) {
                    if (list != null && list.size() > 0) {
                        callback.onSuccess(list, DataLoadType.TYPE_REFRESH_SUCCESS);
                    } else {
                        callback.onError(Constants.ErrorInfo.ERROR_NO_DATA);
                    }
                } else {
                    callback.onError(e.getMessage());
                }
            }
        });
    }
}
