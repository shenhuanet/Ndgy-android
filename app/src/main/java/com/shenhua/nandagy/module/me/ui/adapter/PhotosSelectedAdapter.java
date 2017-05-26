package com.shenhua.nandagy.module.me.ui.adapter;

import android.content.Context;

import com.shenhua.commonlibs.base.BaseRecyclerAdapter;
import com.shenhua.lib.boxing.model.entity.BaseMedia;
import com.shenhua.nandagy.R;

import java.util.List;

/**
 * 发布动态时已选照片适配器
 * Created by shenhua on 4/24/2017.
 * Email shenhuanet@126.com
 */
public class PhotosSelectedAdapter extends BaseRecyclerAdapter<BaseMedia> {

    public PhotosSelectedAdapter(Context context, List<BaseMedia> datas) {
        super(context, datas);
    }

    @Override
    public int getItemViewId(int viewType) {
        return R.layout.item_rv_selected_media_item;
    }

    @Override
    public void bindData(BaseRecyclerViewHolder holder, int position, BaseMedia item) {
        holder.setImage(R.id.media_item, item.getPath());
    }
}
