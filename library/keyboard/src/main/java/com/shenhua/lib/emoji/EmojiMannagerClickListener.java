package com.shenhua.lib.emoji;

import android.view.View;

/**
 * Created by shenhua on 4/27/2017.
 * Email shenhuanet@126.com
 */
public interface EmojiMannagerClickListener {

    /**
     * 添加按钮
     *
     * @param view
     */
    void onAddClick(View view);

    /**
     * 设置按钮
     *
     * @param view
     */
    void onSettingClick(View view);

    /**
     * 删除按钮
     *
     * @param view
     */
    void onDeleteClick(View view);

    /**
     * 表情
     *
     * @param view
     * @param position 位置
     * @param emojiTag 表情码
     */
    void onEmojiItemClick(View view, int position, String emojiTag);

}
