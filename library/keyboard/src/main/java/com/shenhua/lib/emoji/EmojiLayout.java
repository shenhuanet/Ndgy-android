package com.shenhua.lib.emoji;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.shenhua.lib.emoji.adapter.EmojiPagerAdapter;
import com.shenhua.lib.keyboard.R;

import java.util.List;

/**
 * Created by shenhua on 4/27/2017.
 * Email shenhuanet@126.com
 */
public class EmojiLayout extends LinearLayout implements View.OnClickListener, View.OnLongClickListener {

    private ViewPager mEmojiViewPager;
    private LinearLayout mEmojiGroupLayout;
    private TabLayout mEmojiGroupTab;
    private View mEmojiAddLayout;
    private View mEmojiSettingLayout;
    private View mEmojiDeleteLayout;
    private boolean mEmojiAddVisiable = true;// 添加按钮是否可见
    private boolean mEmojiSettingVisiable = false;// 设置按钮是否可见
    private EmojiPagerAdapter mEmojiPagerAdapter;
    private EditText editText;

    public EmojiLayout(Context context) {
        this(context, null);
    }

    public EmojiLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmojiLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_sub_panel_emoji, this);
        mEmojiViewPager = (ViewPager) findViewById(R.id.vp_emoji);
        mEmojiGroupLayout = (LinearLayout) findViewById(R.id.ll_emoji_group);
        // 添加按钮
        mEmojiAddLayout = findViewById(R.id.rl_emoji_add);
        mEmojiAddLayout.setOnClickListener(this);
        setEmojiAddVisiable(mEmojiAddVisiable);
        // 设置按钮
        mEmojiSettingLayout = findViewById(R.id.rl_emoji_setting);
        mEmojiSettingLayout.setOnClickListener(this);
        setEmojiSettingVisiable(mEmojiSettingVisiable);
        // 回删按钮
        mEmojiDeleteLayout = findViewById(R.id.rl_emoji_del);
        mEmojiDeleteLayout.setOnClickListener(this);
        mEmojiDeleteLayout.setOnLongClickListener(this);
        setEmojiDeleteIcon(R.drawable.ic_keyborad_emoji_backspace);
        // 表情区的tab group
        mEmojiGroupTab = (TabLayout) findViewById(R.id.tab_emoji_group);
        // 添加默认的小黄脸表情
        mEmojiPagerAdapter = new EmojiPagerAdapter(((FragmentActivity) context).getSupportFragmentManager(), 1);
        mEmojiViewPager.setAdapter(mEmojiPagerAdapter);
        mEmojiGroupTab.setupWithViewPager(mEmojiViewPager);
        // 添加自定义表情
        List<EmojiGroup> emojiGroup = EmojiLoader.getInstance().getEmojiGroups();
        if (emojiGroup != null && emojiGroup.size() != 0) {
            mEmojiPagerAdapter.setGroupCount(emojiGroup.size() + 1);
            mEmojiPagerAdapter.notifyDataSetChanged();
            for (int i = 1; i < emojiGroup.size() + 1; i++) {
                mEmojiGroupTab.getTabAt(i).setIcon(emojiGroup.get(i).getGroupIcon());
            }
        } else {
            mEmojiPagerAdapter.notifyDataSetChanged();
        }
        mEmojiGroupTab.getTabAt(0).setIcon(R.drawable.ic_tab_emoji_default);
    }

    /**
     * 添加表情按钮显隐
     *
     * @param mEmojiAddVisiable 显隐
     */
    public void setEmojiAddVisiable(boolean mEmojiAddVisiable) {
        this.mEmojiAddVisiable = mEmojiAddVisiable;
        if (mEmojiAddLayout != null) {
            mEmojiAddLayout.setVisibility(mEmojiAddVisiable ? VISIBLE : GONE);
        }
    }

    /**
     * 设置按钮显隐
     *
     * @param mEmojiSettingVisiable 显隐
     */
    public void setEmojiSettingVisiable(boolean mEmojiSettingVisiable) {
        this.mEmojiSettingVisiable = mEmojiSettingVisiable;
        if (mEmojiSettingLayout != null) {
            mEmojiSettingLayout.setVisibility(mEmojiSettingVisiable ? VISIBLE : GONE);
            setEmojiSettingIcon(R.drawable.ic_keyborad_emoji_setting);
        }
    }

    /**
     * 设置按钮图标
     *
     * @param resId drawable
     */
    public void setEmojiSettingIcon(@DrawableRes int resId) {
        ImageView imageView = (ImageView) mEmojiSettingLayout.findViewById(R.id.iv_emoji_group_icon);
        imageView.setImageResource(resId);
    }

    public void setEmojiDeleteIcon(@DrawableRes int resId) {
        ImageView imageView = (ImageView) mEmojiDeleteLayout.findViewById(R.id.iv_emoji_group_icon);
        imageView.setImageResource(resId);
    }

    /**
     * 关联editText
     *
     * @param editText editText
     */
    public void wrapEditTextView(EditText editText) {
        this.editText = editText;
        mEmojiPagerAdapter.wrapEditText(editText);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_emoji_add) {
            Toast.makeText(getContext(), "添加", Toast.LENGTH_SHORT).show();
        }
        if (v.getId() == R.id.rl_emoji_setting) {
            Toast.makeText(getContext(), "设置", Toast.LENGTH_SHORT).show();
        }
        if (v.getId() == R.id.rl_emoji_del) {
            editText.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.rl_emoji_del) {
            editText.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
        }
        return true;
    }
}
