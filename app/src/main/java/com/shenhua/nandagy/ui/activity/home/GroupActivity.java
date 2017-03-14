package com.shenhua.nandagy.ui.activity.home;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.nandagy.R;

import butterknife.ButterKnife;

/**
 * 共青团
 * Created by Shenhua on 10/6/2016.
 * e-mail shenhuanet@126.com
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_group
)
public class GroupActivity extends BaseActivity {

    @Override
    protected void initView(BaseActivity baseActivity) {
        ButterKnife.bind(this);
    }
}
