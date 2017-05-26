package com.shenhua.nandagy.module.acommon.view;

import com.shenhua.commonlibs.mvp.BaseView;
import com.shenhua.nandagy.bean.common.ContentDetailData;

/**
 * Created by Shenhua on 3/23/2017.
 * e-mail shenhuanet@126.com
 */
public interface ContentDetailView extends BaseView {

    void showContent(ContentDetailData contentDetailData);
}
