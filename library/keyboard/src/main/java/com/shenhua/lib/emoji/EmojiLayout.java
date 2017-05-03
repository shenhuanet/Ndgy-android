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
import com.shenhua.lib.emoji.bean.EmojiGroup;
import com.shenhua.lib.emoji.utils.EmojiLoader;
import com.shenhua.lib.keyboard.R;

/**
 * Created by shenhua on 4/27/2017.
 * Email shenhuanet@126.com
 */
public class EmojiLayout extends LinearLayout implements View.OnClickListener, View.OnLongClickListener {

    public static final String DEFAULT_EMOJI = "emoji_weico";
    private ViewPager mEmojiViewPager;
    private TabLayout mEmojiGroupTab;
    private View mEmojiAddLayout;
    private View mEmojiSettingLayout;
    private View mEmojiDeleteLayout;
    private boolean mEmojiAddVisiable = true;// 添加按钮是否可见
    private boolean mEmojiSettingVisiable = false;// 设置按钮是否可见
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
    }

    public void init(final Context context, EditText editText) {
        this.init(context, editText, null);
    }

    public void init(final Context context, EditText editText, String[] emojiDirs) {
        if (emojiDirs == null) {
            emojiDirs = new String[]{};
        }
        // 初始化适配器
        EmojiPagerAdapter mEmojiPagerAdapter = new EmojiPagerAdapter(((FragmentActivity) context).getSupportFragmentManager(), 1 + emojiDirs.length);
        mEmojiViewPager.setAdapter(mEmojiPagerAdapter);
        mEmojiGroupTab.setupWithViewPager(mEmojiViewPager);
        // 设置editText
        this.editText = editText;
        mEmojiPagerAdapter.wrapEditText(editText);
        // 获取表情组
        EmojiGroup emojiGroup;
        for (int i = 0; i < emojiDirs.length + 1; i++) {
            if (i == 0) {
                emojiGroup = EmojiLoader.getInstance().getEmojiGroup(getContext(), DEFAULT_EMOJI);
                EmojiLoader.getInstance().setDefaultEmojiGroupIcon(context, mEmojiGroupTab.getTabAt(0), emojiGroup.getGroupIcon());
                mEmojiPagerAdapter.setEmojiGroup(0, emojiGroup);
            } else {
                emojiGroup = EmojiLoader.getInstance().getEmojiGroup(context, emojiDirs[i - 1]);
                EmojiLoader.getInstance().setDefaultEmojiGroupIcon(context, mEmojiGroupTab.getTabAt(i), emojiGroup.getGroupIcon());
                mEmojiPagerAdapter.setEmojiGroup(i, emojiGroup);
            }
        }
        // 添加表情组  IndexOutOfBoundsException将导致数组越界
//        for (int i = 0; i < emojiDirs.length + 1; i++) {
//            final int finalI = i;
//            final String[] finalEmojiDirs = emojiDirs;
//            BaseThreadHandler.getInstance().sendRunnable(new CommonRunnable<EmojiGroup>() {
//                @Override
//                public EmojiGroup doChildThread() {
//                    if (finalI == 0) {
//                        return EmojiLoader.getInstance().getEmojiGroup(getContext(), DEFAULT_EMOJI);
//                    } else {
//                        return EmojiLoader.getInstance().getEmojiGroup(context, finalEmojiDirs[finalI - 1]);
//                    }
//                }
//
//                @Override
//                public void doUiThread(EmojiGroup group) {
//                    if (finalI == 0) {
//                        EmojiLoader.getInstance().setDefaultEmojiGroupIcon(context, mEmojiGroupTab.getTabAt(0), DEFAULT_EMOJI, group.getGroupIcon());
//                        mEmojiPagerAdapter.setEmojiGroup(0, group);
//                    } else {
//                        EmojiLoader.getInstance().setDefaultEmojiGroupIcon(context, mEmojiGroupTab.getTabAt(finalI), finalEmojiDirs[finalI - 1], group.getGroupIcon());
//                        mEmojiPagerAdapter.setEmojiGroup(finalI, group);
//                    }
//                }
//            });
//        }
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
