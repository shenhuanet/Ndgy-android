package com.shenhua.lib.emoji;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.shenhua.commonlibs.base.BaseRecyclerAdapter;
import com.shenhua.lib.emoji.adapter.EmojiAdapter;
import com.shenhua.lib.emoji.bean.EmojiGroup;
import com.shenhua.lib.emoji.utils.EmojiLoader;
import com.shenhua.lib.keyboard.R;

/**
 * Created by shenhua on 4/28/2017.
 * Email shenhuanet@126.com
 */
public class EmojiPagerFragment extends Fragment implements BaseRecyclerAdapter.OnItemClickListener<EmojiGroup.EmojiBean> {

    private View rootView;
    private int mEmojiGroupItem;

    private EmojiAdapter mEmojiAdapter;
    private EditText mEditText;
    private EmojiGroup emojiGroup;

    public static EmojiPagerFragment getInstance(int emojiGroupItem) {
        EmojiPagerFragment fragment = new EmojiPagerFragment();
        Bundle args = new Bundle();
        args.putInt("page", emojiGroupItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEmojiGroupItem = getArguments().getInt("page");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.layout_page_emoji, container, false);
            RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_emoji);
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));
            mEmojiAdapter = new EmojiAdapter(getContext(), null);
            mRecyclerView.setAdapter(mEmojiAdapter);
            mEmojiAdapter.setOnItemClickListener(this);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEmojiAdapter.setDatas(emojiGroup.getEmoji());
        mEmojiAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnItemClick(View view, int position, EmojiGroup.EmojiBean data) {
        EmojiLoader.getInstance().replaceEmoji(getContext(), mEditText, data.getTag());
    }

    public void setEditText(EditText editText) {
        this.mEditText = editText;
    }

    public void setEmojiGroup(EmojiGroup emojiGroup) {
        this.emojiGroup = emojiGroup;
    }
}
