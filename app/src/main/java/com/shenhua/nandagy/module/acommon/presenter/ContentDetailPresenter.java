package com.shenhua.nandagy.module.acommon.presenter;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.commonlibs.mvp.BasePresenter;
import com.shenhua.nandagy.bean.common.ContentDetailData;
import com.shenhua.nandagy.module.acommon.model.ContentDetailModel;
import com.shenhua.nandagy.module.acommon.model.ContentDetailModelImpl;
import com.shenhua.nandagy.service.Constants;
import com.shenhua.nandagy.module.acommon.view.ContentDetailView;

/**
 * Created by Shenhua on 3/23/2017.
 * e-mail shenhuanet@126.com
 */
public class ContentDetailPresenter extends BasePresenter<ContentDetailView> implements HttpCallback<ContentDetailData> {

    private ContentDetailModel<ContentDetailData> model;
    private int type;
    private String url;

    public ContentDetailPresenter(ContentDetailView view, int type, String url) {
        attachView(view);
        this.type = type;
        this.url = url;
        model = new ContentDetailModelImpl();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void execute() {
        if (type == Constants.Code.URL_REQUEST_TYPE_HOME) {
            model.getHomeDetail(this, url, this);
        } else if (type==Constants.Code.URL_REQUEST_TYPE_XUEGONG) {
            model.getXuegongDetail(this, url, this);
        } else if (type == Constants.Code.URL_REQUEST_TYPE_JIAOWU) {
            model.getJiaowuDetail(this, url, this);
        }
    }

    @Override
    public void onPreRequest() {
        mvpView.showProgress();
    }

    @Override
    public void onSuccess(ContentDetailData data) {
        mvpView.showContent(data);
    }

    @Override
    public void onError(String errorInfo) {
        mvpView.showToast(errorInfo);
    }

    @Override
    public void onPostRequest() {
        mvpView.hideProgress();
    }
}
