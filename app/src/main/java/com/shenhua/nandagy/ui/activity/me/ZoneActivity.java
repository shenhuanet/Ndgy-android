package com.shenhua.nandagy.ui.activity.me;

import android.os.Bundle;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.nandagy.R;

/**
 * 空间，用于访问第三者的空间
 * Created by Shenhua on 4/15/2017.
 * Email:shenhuanet@126.com
 */
@ActivityFragmentInject(
        toolbarId = R.id.common_toolbar,
        toolbarHomeAsUp = true,
        toolbarTitle = R.string.toolbar_title_user_zone,
        toolbarTitleId = R.id.toolbar_title
)
public class ZoneActivity extends BaseActivity {

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {

    }
}
