package com.shenhua.nandagy.view;

import com.shenhua.commonlibs.mvp.BaseView;
import com.shenhua.nandagy.bean.ContentDetailData;

/**
 * Created by Shenhua on 3/23/2017.
 * e-mail shenhuanet@126.com
 */
public interface ContentDetailView extends BaseView {

    void showContent(ContentDetailData contentDetailData);
}
