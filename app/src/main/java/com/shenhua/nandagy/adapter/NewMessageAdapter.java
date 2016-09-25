package com.shenhua.nandagy.adapter;

import android.content.Context;
import android.widget.TextView;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseRecyclerAdapter;
import com.shenhua.nandagy.base.BaseRecyclerViewHolder;
import com.shenhua.nandagy.bean.NewMessageData;

import java.util.List;

/**
 * 消息中心数据适配器
 * Created by Shenhua on 9/4/2016.
 */
public class NewMessageAdapter extends BaseRecyclerAdapter<NewMessageData> {

    Context context;

    public NewMessageAdapter(Context context, List<NewMessageData> datas) {
        super(context, datas);
        this.context = context;
    }

    @Override
    public int getItemViewId() {
        return R.layout.item_new_message;
    }

    @Override
    public void bindData(BaseRecyclerViewHolder holder, int position, NewMessageData item) {
        TextView title = (TextView) holder.getView(R.id.msg_title);
        TextView content = (TextView) holder.getView(R.id.msg_content);
        TextView from = (TextView) holder.getView(R.id.msg_from);
        TextView time = (TextView) holder.getView(R.id.msg_time);
        title.setText(item.getTitle());
        content.setText(item.getContent());
        from.setText(item.getFrom());
        time.setText(item.getTime());
    }
}
