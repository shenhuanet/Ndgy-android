package com.shenhua.nandagy.module.more.score.ui.adapter;

import android.content.Context;
import android.widget.LinearLayout;

import com.shenhua.commonlibs.base.BaseImageTextItem;
import com.shenhua.commonlibs.base.BaseListAdapter;
import com.shenhua.nandagy.R;

import java.util.List;

/**
 * 成绩查询各种类别适配器
 * Created by shenhua on 3/14/2017.
 * Email shenhuanet@126.com
 */
public class ScoreQueryCategoryAdapter extends BaseListAdapter<BaseImageTextItem> {

    public ScoreQueryCategoryAdapter(Context context, List<BaseImageTextItem> datas) {
        super(context, datas);
    }

    @Override
    public void onBindItemView(BaseViewHolder holder, BaseImageTextItem baseImageTextItem, int position) {
        LinearLayout background = holder.getView(R.id.common_layout_item);
        background.setBackgroundResource(R.drawable.bg_score_category_item);

        holder.setText(R.id.common_txt_name, baseImageTextItem.getTitle());
        holder.setImageResource(R.id.common_iv_img, baseImageTextItem.getDrawable());
    }

    @Override
    public int getItemViewId() {
        return R.layout.item_common_score_category;
    }
}
