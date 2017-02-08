package com.shenhua.nandagy.ui.fragment.more;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * 纸飞机消息列表
 * Created by Shenhua on 10/8/2016.
 * e-mail shenhuanet@126.com
 */
public class PagerMessageFragment extends BaseFragment {

    private static PagerMessageFragment instance = null;
    private View view;

    public static PagerMessageFragment getInstance() {
        if (instance == null)
            instance = new PagerMessageFragment();
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.frag_pager_message, container, false);
            ButterKnife.bind(this, view);
        }
        ViewGroup group = (ViewGroup) view.getParent();
        if (group != null)
            group.removeView(view);
        return view;
    }
}
