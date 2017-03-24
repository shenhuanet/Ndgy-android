package com.shenhua.nandagy.presenter;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.commonlibs.mvp.BasePresenter;
import com.shenhua.nandagy.bean.ContentDetailData;
import com.shenhua.nandagy.model.ContentDetailModel;
import com.shenhua.nandagy.model.ContentDetailModelImpl;
import com.shenhua.nandagy.service.ContentDetailType;
import com.shenhua.nandagy.view.ContentDetailView;

/**
 * Created by Shenhua on 3/23/2017.
 * e-mail shenhuanet@126.com
 */
public class ContentDetailPresenter extends BasePresenter<ContentDetailView> implements HttpCallback<ContentDetailData> {

    private ContentDetailModel<ContentDetailData> model;
    private ContentDetailType type;
    private String url;

    public ContentDetailPresenter(ContentDetailView view, ContentDetailType type, String url) {
        attachView(view);
        this.type = type;
        this.url = url;
        model = new ContentDetailModelImpl();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void execute() {
        if (type == ContentDetailType.TYPE_HOME) {
            model.getHomeDetail(this, url, this);
        } else if (type == ContentDetailType.TYPE_XUEGONG) {
            model.getXuegongDetail(this, url, this);
        } else if (type == ContentDetailType.TYPE_JIAOWU) {
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
