package com.shenhua.nandagy.adapter;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shenhua.commonlibs.base.BaseListAdapter;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.ui.activity.ImageViewerActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Shenhua on 5/7/2017.
 * Email:shenhuanet@126.com
 */
public class CircleGridAdapter extends BaseListAdapter<BmobFile> {

    public CircleGridAdapter(Context context, List<BmobFile> datas) {
        super(context, datas);
    }

    @Override
    public void onBindItemView(BaseViewHolder baseViewHolder, BmobFile bmobFile, int position) {
        ImageView view = baseViewHolder.getView(R.id.iv_nine_grid);
        Glide.with(mContext).load(bmobFile.getFileUrl()).thumbnail(0.5f)
                .placeholder(R.drawable.img_picture_placeholder).into(view);
        view.setOnClickListener(v -> {
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < mDatas.size(); i++) {
                list.add(mDatas.get(i).getFileUrl());
            }
            mContext.startActivity(new Intent(mContext, ImageViewerActivity.class)
                    .putExtra(ImageViewerActivity.EXTRA_POSITION_KEY, position)
                    .putStringArrayListExtra(ImageViewerActivity.EXTRA_IMGS_KEY, list)
            );
        });
    }

    @Override
    public int getItemViewId() {
        return R.layout.item_list_circle_grid;
    }
}
