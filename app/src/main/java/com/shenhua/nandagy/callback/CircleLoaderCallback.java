package com.shenhua.nandagy.callback;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.nandagy.bean.bmobbean.SchoolCircle;

import java.util.List;

/**
 * Created by shenhua on 5/15/2017.
 * Email shenhuanet@126.com
 */
public interface CircleLoaderCallback extends HttpCallback<List<SchoolCircle>> {

    void onSuccess(List<SchoolCircle> schoolCircles, int type);
}
