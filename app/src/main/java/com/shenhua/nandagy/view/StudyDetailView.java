package com.shenhua.nandagy.view;

import com.shenhua.commonlibs.mvp.BaseView;
import com.shenhua.nandagy.bean.StudyListData;

/**
 * Created by shenhua on 2/14/2017.
 * Email shenhuanet@126.com
 */
public interface StudyDetailView extends BaseView {

    void showToast(String msg);

    void showDetail(StudyListData data);

}
