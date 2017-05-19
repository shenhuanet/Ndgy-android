package com.shenhua.nandagy.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenhua.commonlibs.base.BaseRecyclerAdapter;
import com.shenhua.lib.emoji.utils.EmojiLoader;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.bmobbean.SchoolCircleComment;
import com.shenhua.nandagy.utils.RelativeDateFormat;
import com.shenhua.nandagy.utils.bmobutils.AvatarUtils;

import java.util.List;

/**
 * Created by shenhua on 5/18/2017.
 * Email shenhuanet@126.com
 */
public class CircleDetailCmAdapter extends BaseRecyclerAdapter<SchoolCircleComment> {

    public CircleDetailCmAdapter(Context context, List<SchoolCircleComment> datas) {
        super(context, datas);
    }

    @Override
    public int getItemViewId(int i) {
        return R.layout.item_circle_detail_cm;
    }

    @Override
    public void bindData(BaseRecyclerViewHolder holder, int i, SchoolCircleComment schoolCircleComment) {
        AvatarUtils.loadUserAvatar(mContext, schoolCircleComment.getCommenter().getUser(), (ImageView) holder.getView(R.id.iv_detail_photo));
        AvatarUtils.loadUserNick(schoolCircleComment.getCommenter(), (TextView) holder.getView(R.id.tv_detail_nike));
        holder.setText(R.id.tv_time_ago, RelativeDateFormat.friendly_time(schoolCircleComment.getCreatedAt()));
        holder.setText(R.id.tv_detail_comment, schoolCircleComment.getContent());
        EmojiLoader.replaceEmoji(mContext, (TextView) holder.getView(R.id.tv_detail_comment));
    }
}