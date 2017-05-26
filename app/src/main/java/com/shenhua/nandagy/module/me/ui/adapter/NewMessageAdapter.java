package com.shenhua.nandagy.module.me.ui.adapter;

import android.content.Context;

import com.shenhua.commonlibs.base.BaseRecyclerAdapter;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.module.NewMessageData;

import java.util.List;

/**
 * 消息中心数据适配器
 * Created by Shenhua on 9/4/2016.
 */
public class NewMessageAdapter extends BaseRecyclerAdapter<NewMessageData> {

    public NewMessageAdapter(Context context, List<NewMessageData> datas) {
        super(context, datas);
    }

    @Override
    public int getItemViewId(int viewType) {
        return R.layout.item_new_message;
    }

    @Override
    public void bindData(BaseRecyclerAdapter.BaseRecyclerViewHolder holder, int position, NewMessageData item) {
        holder.setText(R.id.msg_title,item.getTitle());
        holder.setText(R.id.msg_content,item.getContent());
        holder.setText(R.id.msg_from,item.getFrom());
        holder.setText(R.id.msg_time,item.getTime());
    }
}
