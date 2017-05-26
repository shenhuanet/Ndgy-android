package com.shenhua.nandagy.view;

import com.shenhua.commonlibs.mvp.BaseView;
import com.shenhua.nandagy.bean.bmobbean.LostAndFound;

import java.util.List;

/**
 * Created by shenhua on 5/22/2017.
 * Email shenhuanet@126.com
 */
public interface LostFoundView extends BaseView {

    void showEmpty(boolean show);

    void setDatas(List<LostAndFound> lostAndFounds);

    void setMoreDatas(int itemCount, List<LostAndFound> lostAndFounds);
}
