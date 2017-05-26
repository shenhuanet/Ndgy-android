package com.shenhua.nandagy.module.home.ui.activity;

import android.os.Bundle;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.nandagy.R;

import butterknife.ButterKnife;

/**
 * 图书馆
 * Created by Shenhua on 10/6/2016.
 * e-mail shenhuanet@126.com
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_library
)
public class LibraryActivity extends BaseActivity {

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }
}
