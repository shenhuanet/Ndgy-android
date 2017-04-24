package com.shenhua.lib.keyboard.interfaces;

import android.view.ViewGroup;

import com.shenhua.lib.keyboard.adpater.EmoticonsAdapter;

public interface EmoticonDisplayListener<T> {

    void onBindView(int position, ViewGroup parent, EmoticonsAdapter.ViewHolder viewHolder, T t, boolean isDelBtn);
}
