package com.shenhua.nandagy.module.more.study.model;

import android.content.Context;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.nandagy.bean.module.StudyListData;

import java.util.List;

/**
 * Created by Shenhua on 2/9/2017.
 * e-mail shenhuanet@126.com
 */
public interface StudyModel {

    void toGetList(Context context, int type, HttpCallback<List<StudyListData>> callback);

    void toGetDetail(Context context, int type, int position, String url, HttpCallback<StudyListData> callback);
}
