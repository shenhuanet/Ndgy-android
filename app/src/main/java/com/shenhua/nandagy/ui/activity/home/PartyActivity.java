package com.shenhua.nandagy.ui.activity.home;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.nandagy.R;

import butterknife.ButterKnife;

/**
 * 党建风采
 * Created by Shenhua on 10/6/2016.
 * e-mail shenhuanet@126.com
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_party
)
public class PartyActivity extends BaseActivity {

    @Override
    protected void initView(BaseActivity baseActivity) {
        ButterKnife.bind(this);
    }
}
