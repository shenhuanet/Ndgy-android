package com.shenhua.nandagy.module.more.study.view;

import com.shenhua.commonlibs.mvp.BaseView;
import com.shenhua.nandagy.bean.module.StudyListData;

/**
 * Created by shenhua on 2/14/2017.
 * Email shenhuanet@126.com
 */
public interface StudyDetailView extends BaseView {

    void showDetail(StudyListData data);

}
