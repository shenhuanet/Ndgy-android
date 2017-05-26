package com.shenhua.nandagy.module.more.circle.ui.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenhua.commonlibs.base.BaseRecyclerAdapter;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.bmobbean.UserZone;
import com.shenhua.nandagy.utils.bmobutils.AvatarUtils;

import java.util.List;

/**
 * Created by shenhua on 5/18/2017.
 * Email shenhuanet@126.com
 */
public class CircleDetailUzAdapter extends BaseRecyclerAdapter<UserZone> {

    public CircleDetailUzAdapter(Context context, List<UserZone> datas) {
        super(context, datas);
    }

    @Override
    public int getItemViewId(int i) {
        return R.layout.item_circle_detail_uz;
    }

    @Override
    public void bindData(BaseRecyclerViewHolder holder, int i, UserZone zone) {
        AvatarUtils.loadUserAvatar(mContext, zone.getUser(), (ImageView) holder.getView(R.id.iv_detail_photo));
        AvatarUtils.loadUserNick(zone, (TextView) holder.getView(R.id.tv_detail_nike));
    }
}
