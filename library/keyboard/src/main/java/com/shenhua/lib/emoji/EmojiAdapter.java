package com.shenhua.lib.emoji;

import android.content.Context;
import android.widget.ImageView;

import com.shenhua.lib.keyboard.R;

import java.util.List;

/**
 * Created by shenhua on 4/27/2017.
 * Email shenhuanet@126.com
 */
public class EmojiAdapter extends BaseRecyclerAdapter<EmojiLoader.Emoji> {

    public EmojiAdapter(Context context, List<EmojiLoader.Emoji> datas) {
        super(context, datas);
    }

    @Override
    public int getItemViewId(int viewType) {
        return R.layout.item_emoji_default;
    }

    @Override
    public void bindData(BaseRecyclerViewHolder holder, int position, EmojiLoader.Emoji item) {
        ImageView imageView = (ImageView) holder.getView(R.id.iv_emoji);
        imageView.setImageDrawable(EmojiLoader.getDefaultEmojiBitmap(mContext, item.getFile()));
    }
}
