package com.shenhua.nandagy.adapter;

import android.content.Context;

import com.shenhua.commonlibs.base.BaseRecyclerAdapter;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.HomeData;

import java.util.List;

/**
 * 首页数据适配器
 * Created by shenhua on 8/30/2016.
 */
public class HomeDataAdapter extends BaseRecyclerAdapter<HomeData> {

    public HomeDataAdapter(Context context, List<HomeData> datas) {
        super(context, datas);
    }

    @Override
    public int getItemViewId(int viewType) {
        return R.layout.item_data_home;
    }

    @Override
    public void bindData(BaseRecyclerAdapter.BaseRecyclerViewHolder holder, int position, HomeData item) {
        holder.setText(R.id.tv_home_list_title,item.getTitle());
        holder.setText(R.id.tv_home_list_detail,item.getDetail());
        holder.setText(R.id.tv_home_list_time,item.getTime());
        holder.setImage(R.id.iv_home_list_img,item.getImgUrl());
    }
}
