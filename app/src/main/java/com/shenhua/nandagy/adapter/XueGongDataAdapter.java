package com.shenhua.nandagy.adapter;

import android.content.Context;

import com.shenhua.commonlibs.base.BaseRecyclerAdapter;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.XueGongData;

import java.util.List;

/**
 * 首页数据适配器
 * Created by shenhua on 8/30/2016.
 */
public class XueGongDataAdapter extends BaseRecyclerAdapter<XueGongData.XuegongListData> {

    public XueGongDataAdapter(Context context, List<XueGongData.XuegongListData> datas) {
        super(context, datas);
    }

    @Override
    public int getItemViewId(int viewType) {
        return R.layout.item_data_xuegong;
    }

    @Override
    public void bindData(BaseRecyclerAdapter.BaseRecyclerViewHolder holder, int position, XueGongData.XuegongListData item) {
        holder.setText(R.id.tv_xuegong_title, item.getTitle());
        holder.setText(R.id.tv_xuegong_time, item.getTime());
        holder.setImage(R.id.iv_xuegong_img, item.getNewsType());
    }
}
