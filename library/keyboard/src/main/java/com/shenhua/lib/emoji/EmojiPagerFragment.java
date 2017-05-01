package com.shenhua.lib.emoji;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.shenhua.lib.keyboard.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenhua on 4/28/2017.
 * Email shenhuanet@126.com
 */
public class EmojiPagerFragment extends Fragment implements BaseRecyclerAdapter.OnItemClickListener<EmojiLoader.Emoji> {

    private static final String TAG = "EmojiPagerFragment";
    private View rootView;
    private int mEmojiGroupItem;
    private EmojiAdapter mEmojiAdapter;
    private List<EmojiLoader.Emoji> mEmojis = new ArrayList<>();
    private EditText mEditText;

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
            mEmojiAdapter = new EmojiAdapter(getContext(), mEmojis);
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
        if (mEmojiGroupItem == 0) {
            mEmojis = EmojiLoader.loadAssetEmojis(getContext(), "emoji_weico.json");
        } else {
            mEmojis = EmojiLoader.loadEmoji(getContext(), "");
        }
        mEmojiAdapter.setDatas(mEmojis);
        mEmojiAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnItemClick(View view, int position, EmojiLoader.Emoji data) {
//        Toast.makeText(getContext(), data.getId() + "-->" + data.getTag(), Toast.LENGTH_SHORT).show();
        if (mEditText == null) {
            Log.d(TAG, "OnItemClick: editText null");
            return;
        }
        Editable editable = mEditText.getText();
        int start = mEditText.getSelectionStart();
        int end = mEditText.getSelectionEnd();
        start = start < 0 ? 0 : start;
        end = start < 0 ? 0 : end;
        editable.replace(start, end, data.getTag());

    }

    public void setEditText(EditText editText) {
        this.mEditText = editText;
    }
}
