package com.shenhua.nandagy.module.more.study.view;

import com.shenhua.commonlibs.mvp.BaseView;
import com.shenhua.nandagy.bean.module.StudyListData;

import java.util.List;

/**
 * Created by Shenhua on 2/9/2017.
 * e-mail shenhuanet@126.com
 */
public interface StudyListView extends BaseView {

    void updateList(List<StudyListData> datas);
}
