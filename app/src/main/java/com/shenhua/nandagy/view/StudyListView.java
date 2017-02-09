package com.shenhua.nandagy.view;

import com.shenhua.nandagy.bean.StudyListData;

import java.util.List;

/**
 * Created by Shenhua on 2/9/2017.
 * e-mail shenhuanet@126.com
 */
public interface StudyListView extends BaseView {

    void updateList(List<StudyListData> datas);
}
