package com.shenhua.nandagy.module.more.lostfound.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenhua.commonlibs.base.BaseRecyclerAdapter;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.bmobbean.LostAndFound;
import com.shenhua.nandagy.module.more.circle.ui.adapter.CircleGridAdapter;
import com.shenhua.nandagy.utils.RelativeDateFormat;
import com.shenhua.nandagy.utils.bmobutils.AvatarUtils;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * 失物招领 适配器
 * Created by shenhua on 5/22/2017.
 * Email shenhuanet@126.com
 */
public class LostFoundAdapter extends BaseRecyclerAdapter<LostAndFound> {

    public LostFoundAdapter(Context context, List<LostAndFound> datas) {
        super(context, datas);
    }

    @Override
    public int getItemViewId(int i) {
        return R.layout.item_lost_found;
    }

    public void updateItem(int position, LostAndFound lostAndFound) {
        mDatas.set(position, lostAndFound);
        notifyItemChanged(position);
    }

    @Override
    public void bindData(BaseRecyclerViewHolder holder, int i, LostAndFound lostAndFound) {
        AvatarUtils.loadUserAvatar(mContext, lostAndFound.getUser(), (ImageView) holder.getView(R.id.iv_user_photo));
        holder.setText(R.id.tv_title, lostAndFound.getTitle());
        holder.setText(R.id.tv_describe, lostAndFound.getDescribe());
        holder.setText(R.id.tv_time_ago, RelativeDateFormat.friendly_time(lostAndFound.getCreatedAt()));

        TextView tvTag = (TextView) holder.getView(R.id.tv_tag);
        tvTag.setText(lostAndFound.getType() == LostAndFound.TYPE_LOST ? "丢了东西" : "捡到东西");
        tvTag.setBackgroundResource(lostAndFound.getType() == LostAndFound.TYPE_LOST ? R.drawable.bg_round_red : R.drawable.bg_round_theme);

        if (lostAndFound.getIsResolved()) {
            tvTag.setText("已解决");
            tvTag.setBackgroundResource(R.drawable.bg_round_green);
        }

        TextView tvContact = (TextView) holder.getView(R.id.tv_contact);
        tvContact.setOnClickListener(v -> {
            tvContact.setText("联系方式:" + lostAndFound.getContact());
        });

        GridView gridView = (GridView) holder.getView(R.id.nineGridView);
        gridView.setVisibility(View.GONE);
        List<BmobFile> file = lostAndFound.getPics();
        if (file != null && file.size() > 0) {
            gridView.setVisibility(View.VISIBLE);
            gridView.setNumColumns(file.size() <= 3 ? file.size() : 3);
            CircleGridAdapter adapter = new CircleGridAdapter(mContext, file);
            gridView.setAdapter(adapter);
        }
    }
}