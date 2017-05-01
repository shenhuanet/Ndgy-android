package com.shenhua.lib.emoji.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.EditText;

import com.shenhua.lib.emoji.EmojiPagerFragment;

/**
 * Created by shenhua on 4/27/2017.
 * Email shenhuanet@126.com
 */
public class EmojiPagerAdapter extends FragmentPagerAdapter {

    private int mGroupCount = 0;
    private EmojiPagerFragment emojiPagerFragment;
    private EditText editText;

    public EmojiPagerAdapter(FragmentManager fm, int groupCount) {
        super(fm);
        mGroupCount = groupCount;
    }

    public void wrapEditText(EditText editText) {
        this.editText = editText;
    }

    @Override
    public Fragment getItem(int position) {
        emojiPagerFragment = EmojiPagerFragment.getInstance(position);
        emojiPagerFragment.setEditText(editText);
        return emojiPagerFragment;
    }

    public void setGroupCount(int mGroupCount) {
        this.mGroupCount = mGroupCount;
    }

    @Override
    public int getCount() {
        return mGroupCount;
    }
}
