package com.shenhua.lib.emoji.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.EditText;

import com.shenhua.lib.emoji.EmojiPagerFragment;
import com.shenhua.lib.emoji.bean.EmojiGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenhua on 4/27/2017.
 * Email shenhuanet@126.com
 */
public class EmojiPagerAdapter extends FragmentPagerAdapter {

    private int mGroupCount = 0;
    private EditText mEditText;
    private List<EmojiGroup> mEmojiGroups = new ArrayList<>();

    public EmojiPagerAdapter(FragmentManager fm, int mGgroupCount) {
        super(fm);
        this.mGroupCount = mGgroupCount;
    }

    public void wrapEditText(EditText editText) {
        this.mEditText = editText;
    }

    @Override
    public Fragment getItem(int position) {
        EmojiPagerFragment emojiPagerFragment = EmojiPagerFragment.getInstance(position);
        emojiPagerFragment.setEmojiGroup(getEmojiGroups(position));
        emojiPagerFragment.setEditText(mEditText);
        return emojiPagerFragment;
    }

    private EmojiGroup getEmojiGroups(int position) {
        return mEmojiGroups.get(position);
    }

    public void setEmojiGroup(int position, EmojiGroup mEmojiGroup) {
        this.mEmojiGroups.add(position, mEmojiGroup);
    }

    @Override
    public int getCount() {
        return mGroupCount;
    }
}
