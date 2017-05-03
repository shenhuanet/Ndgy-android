package com.shenhua.lib.emoji.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.shenhua.commonlibs.base.BaseRecyclerAdapter;
import com.shenhua.lib.emoji.utils.EmojiLoader;
import com.shenhua.lib.emoji.bean.EmojiGroup;
import com.shenhua.lib.keyboard.R;

import java.util.List;

/**
 * Created by shenhua on 4/27/2017.
 * Email shenhuanet@126.com
 */
public class EmojiAdapter extends BaseRecyclerAdapter<EmojiGroup.EmojiBean> {

    public EmojiAdapter(Context context, List<EmojiGroup.EmojiBean> datas) {
        super(context, datas);
    }

    @Override
    public int getItemViewId(int viewType) {
        return R.layout.item_emoji_default;
    }

    @Override
    public void bindData(BaseRecyclerViewHolder holder, int position, EmojiGroup.EmojiBean item) {
        ImageView imageView = (ImageView) holder.getView(R.id.iv_emoji);
        imageView.setImageDrawable(EmojiLoader.getEmojiDispaly(mContext, item.getTag()));
    }
}
