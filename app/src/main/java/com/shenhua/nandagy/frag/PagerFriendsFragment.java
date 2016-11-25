package com.shenhua.nandagy.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * 纸飞机好友列表
 * Created by Shenhua on 10/8/2016.
 * e-mail shenhuanet@126.com
 */
public class PagerFriendsFragment extends BaseFragment {

    private static PagerFriendsFragment instance = null;
    private View view;

    public static PagerFriendsFragment getInstance() {
        if (instance == null)
            instance = new PagerFriendsFragment();
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.frag_pager_friends, container, false);
            ButterKnife.bind(this, view);
        }
        ViewGroup group = (ViewGroup) view.getParent();
        if (group != null)
            group.removeView(view);
        return view;
    }
}
