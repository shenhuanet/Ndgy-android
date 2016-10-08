package com.shenhua.nandagy.adapter;

import android.content.Context;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseRecyclerAdapter;
import com.shenhua.nandagy.base.BaseRecyclerViewHolder;
import com.shenhua.nandagy.bean.CircleData;

import java.util.List;

/**
 * Created by Shenhua on 10/6/2016.
 * e-mail shenhuanet@126.com
 */
public class CircleAdapter extends BaseRecyclerAdapter<CircleData> {

    public CircleAdapter(Context context, List<CircleData> datas) {
        super(context, datas);
    }

    @Override
    public int getItemViewId() {
        return R.layout.item_circle;
    }

    @Override
    public void bindData(BaseRecyclerViewHolder holder, int position, CircleData item) {

    }
}
