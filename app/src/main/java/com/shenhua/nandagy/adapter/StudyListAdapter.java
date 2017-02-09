package com.shenhua.nandagy.adapter;

import android.content.Context;

import com.shenhua.nandagy.base.BaseRecyclerAdapter;
import com.shenhua.nandagy.base.BaseRecyclerViewHolder;
import com.shenhua.nandagy.bean.StudyListData;

import java.util.List;

/**
 * Created by Shenhua on 2/9/2017.
 * e-mail shenhuanet@126.com
 */
public class StudyListAdapter extends BaseRecyclerAdapter<StudyListData> {

    public StudyListAdapter(Context context, List<StudyListData> datas) {
        super(context, datas);
    }

    @Override
    public int getItemViewId() {
        return 0;
    }

    @Override
    public void bindData(BaseRecyclerViewHolder holder, int position, StudyListData item) {

    }
}
