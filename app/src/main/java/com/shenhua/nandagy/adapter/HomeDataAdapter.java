package com.shenhua.nandagy.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shenhua.commonlibs.base.BaseRecyclerAdapter;
import com.shenhua.commonlibs.base.BaseRecyclerViewHolder;
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
    public int getItemViewId() {
        return R.layout.item_data_home;
    }

    @Override
    public void bindData(BaseRecyclerViewHolder holder, int position, HomeData item) {
        final TextView title = (TextView) holder.getView(R.id.tv_home_list_title);
        final TextView detail = (TextView) holder.getView(R.id.tv_home_list_detail);
        final TextView time = (TextView) holder.getView(R.id.tv_home_list_time);
        final ImageView image = (ImageView) holder.getView(R.id.iv_home_list_img);
        title.setText(item.getTitle());
        detail.setText(item.getDetail());
        time.setText(item.getTime());
        Glide.with(mContext).load(item.getImgUrl()).centerCrop().crossFade().into(image);
    }
}
