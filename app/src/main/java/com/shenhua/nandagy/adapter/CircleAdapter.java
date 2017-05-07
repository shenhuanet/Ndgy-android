package com.shenhua.nandagy.adapter;

import android.content.Context;
import android.widget.GridView;

import com.shenhua.commonlibs.base.BaseRecyclerAdapter;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.bmobbean.SchoolCircle;
import com.shenhua.nandagy.utils.bmobutils.AvatarUtils;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Shenhua on 10/6/2016.
 * e-mail shenhuanet@126.com
 */
public class CircleAdapter extends BaseRecyclerAdapter<SchoolCircle> {

    public CircleAdapter(Context context, List<SchoolCircle> datas) {
        super(context, datas);
    }

    @Override
    public int getItemViewId(int viewType) {
        return R.layout.item_circle;
    }

    @Override
    public void bindData(BaseRecyclerAdapter.BaseRecyclerViewHolder holder, int position, SchoolCircle item) {
        holder.setText(R.id.tv_content, item.getContent());
        String url = AvatarUtils.getOtherUserAvatar(item.getUserZone());
        if (url != null) {
            holder.setImage(R.id.iv_user_photo, url);
        }
        List<BmobFile> file = item.getPics();
        if (file != null && file.size() > 0) {
            GridView gridView = (GridView) holder.getView(R.id.gridView);
            CircleGridAdapter adapter = new CircleGridAdapter(mContext, file);
            gridView.setAdapter(adapter);
        }



    }

}
