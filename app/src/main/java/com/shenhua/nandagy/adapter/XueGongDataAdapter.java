package com.shenhua.nandagy.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenhua.commonlibs.base.BaseRecyclerAdapter;
import com.shenhua.commonlibs.base.BaseRecyclerViewHolder;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.XueGongData;

import java.util.List;

/**
 * 首页数据适配器
 * Created by shenhua on 8/30/2016.
 */
public class XueGongDataAdapter extends BaseRecyclerAdapter<XueGongData> {

    public XueGongDataAdapter(Context context, List<XueGongData> datas) {
        super(context, datas);
    }

    @Override
    public int getItemViewId() {
        return R.layout.item_data_xuegong;
    }

    @Override
    public void bindData(BaseRecyclerViewHolder holder, int position, XueGongData item) {
        final TextView title = (TextView) holder.getView(R.id.tv_xuegong_title);
        final TextView time = (TextView) holder.getView(R.id.tv_xuegong_time);
        final ImageView image = (ImageView) holder.getView(R.id.iv_xuegong_img);
        title.setText(item.getTitle());
        time.setText(item.getTime());
        image.setImageResource(item.getNewsType());
    }
}
