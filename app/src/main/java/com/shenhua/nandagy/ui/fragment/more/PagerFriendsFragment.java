package com.shenhua.nandagy.ui.fragment.more;

import android.view.View;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseFragment;
import com.shenhua.nandagy.R;

import butterknife.ButterKnife;

/**
 * 纸飞机好友列表
 * Created by Shenhua on 10/8/2016.
 * e-mail shenhuanet@126.com
 */
@ActivityFragmentInject(contentViewId = R.layout.frag_pager_friends)
public class PagerFriendsFragment extends BaseFragment {

    public static PagerFriendsFragment getInstance() {
        return new PagerFriendsFragment();
    }

    @Override
    public void initView(View rootView) {
        ButterKnife.bind(this, rootView);
    }
}
