package com.shenhua.nandagy.module.more.lostfound.presenter;

import com.shenhua.nandagy.bean.bmobbean.LostAndFound;
import com.shenhua.nandagy.bean.bmobbean.MyUser;
import com.shenhua.nandagy.callback.LostFoundLoaderCallback;
import com.shenhua.nandagy.module.more.lostfound.model.LostFoundModel;
import com.shenhua.nandagy.module.more.lostfound.model.LostFoundModelImpl;
import com.shenhua.nandagy.service.Constants;
import com.shenhua.nandagy.utils.DataLoadType;
import com.shenhua.nandagy.module.more.lostfound.view.LostFoundView;

import java.util.List;

/**
 * Created by shenhua on 5/22/2017.
 * Email shenhuanet@126.com
 */
public class LostFoundPresenter implements LostFoundLoaderCallback<List<LostAndFound>> {

    private LostFoundModel<List<LostAndFound>> lostFoundModel;
    private LostFoundView lostFoundView;
    private int mItemCount = 0;

    public LostFoundPresenter(LostFoundView lostFoundView) {
        this.lostFoundView = lostFoundView;
        lostFoundModel = new LostFoundModelImpl();
    }

    public void refresh() {
        lostFoundModel.toGetLostFoundData(this);
    }

    public void loadMore(int itemCount) {
        this.mItemCount = itemCount;
        lostFoundModel.toLoadMoreLostFoundData(itemCount, this);
    }

    public void getSeltLostFound(MyUser user) {
        if (user == null) {
            lostFoundView.showToast("请登录后操作");
            return;
        }
        lostFoundModel.getSelfLostFound(user, this);
    }

    @Override
    public void onPreRequest() {
        lostFoundView.showProgress();
    }

    @Override
    public void onSuccess(Object o) {
        // not usage
    }

    @Override
    public void onSuccess(List<LostAndFound> lostAndFounds, int type) {
        if (type == DataLoadType.TYPE_REFRESH_SUCCESS) {
            mItemCount = lostAndFounds.size();
            lostFoundView.setDatas(lostAndFounds);
            lostFoundView.showEmpty(false);
        } else {
            if (lostAndFounds == null || lostAndFounds.size() == 0) {
                return;
            }
            mItemCount += lostAndFounds.size();
            lostFoundView.setMoreDatas(mItemCount, lostAndFounds);
        }
    }

    @Override
    public void onError(String s) {
        switch (s) {
            case Constants.ErrorInfo.ERROR_NO_DATA:
                lostFoundView.showEmpty(true);
                break;
            case Constants.ErrorInfo.ERROR_NO_MOREDATA:
                if (mItemCount > 20) {
                    lostFoundView.showToast("暂无更多数据");
                }
                break;
            default:
                lostFoundView.showToast(s);
                break;
        }
    }

    @Override
    public void onPostRequest() {
        lostFoundView.hideProgress();
    }

}
