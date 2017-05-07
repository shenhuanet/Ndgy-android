package com.shenhua.nandagy.adapter;

import android.content.Context;

import com.shenhua.commonlibs.base.BaseListAdapter;
import com.shenhua.nandagy.R;

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
    public void onBindItemView(BaseViewHolder baseViewHolder, BmobFile bmobFile, int i) {
        baseViewHolder.setImage(R.id.iv_nine_grid, bmobFile.getFileUrl());
    }

    @Override
    public int getItemViewId() {
        return R.layout.item_list_circle_grid;
    }
}
