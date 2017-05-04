package com.shenhua.nandagy.adapter;

import android.content.Context;

import com.shenhua.commonlibs.base.BaseRecyclerAdapter;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.bmobbean.SchoolCircle;

import java.util.List;

/**
 * Created by Shenhua on 10/6/2016.
 * e-mail shenhuanet@126.com
 */
public class CircleAdapter extends BaseRecyclerAdapter<SchoolCircle> {

    public CircleAdapter(Context context, List<SchoolCircle> datas) {
        super(context, datas);
    }

    @Override
        public int getItemViewId(int viewType) {
        return R.layout.item_circle;
    }

    @Override
    public void bindData(BaseRecyclerAdapter.BaseRecyclerViewHolder holder, int position, SchoolCircle item) {

    }

}
