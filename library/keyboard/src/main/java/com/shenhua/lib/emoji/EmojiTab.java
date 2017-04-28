package com.shenhua.lib.emoji;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.shenhua.lib.keyboard.R;

/**
 * 表情种类tab
 * Created by shenhua on 4/27/2017.
 * Email shenhuanet@126.com
 */
@SuppressLint("ViewConstructor")
public class EmojiTab extends RelativeLayout {

    private String mEmojiPath;
    private int mIconSrc;

    public EmojiTab(Context context, int iconSrc) {
        super(context);
        mIconSrc = iconSrc;
        init(context);
    }

    public EmojiTab(Context context, String emojiPath) {
        super(context);
        mEmojiPath = emojiPath;
        init(context);
    }

    private void init(final Context context) {
        setId(R.id.id_emoji_tab);
        inflate(context, R.layout.view_emoji_add_tab, this);
        ImageView icon = (ImageView) findViewById(R.id.iv_emoji_group_icon);
        if (TextUtils.isEmpty(mEmojiPath)) {
            icon.setImageResource(mIconSrc);
        } else {
            EmojiLoader.loadEmoji(context, mEmojiPath);
        }
    }

}
