package com.shenhua.nandagy.adapter;

import android.content.Context;

import com.shenhua.commonlibs.base.BaseRecyclerAdapter;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.JiaowuData;

import java.util.List;

/**
 * Created by Shenhua on 3/24/2017.
 * e-mail shenhuanet@126.com
 */
public class JiaowuDataAdapter extends BaseRecyclerAdapter<JiaowuData.JiaowuList> {

    public JiaowuDataAdapter(Context context, List<JiaowuData.JiaowuList> datas) {
        super(context, datas);
    }

    @Override
    public int getItemViewId(int viewType) {
        return R.layout.item_data_jiaowu;
    }

    @Override
    public void bindData(BaseRecyclerViewHolder holder, int position, JiaowuData.JiaowuList item) {
        holder.setText(R.id.tv_title, item.getTitle());
        holder.setText(R.id.tv_time, item.getTime());
        holder.setImage(R.id.circleImageView, item.getDrawable());
    }
}
