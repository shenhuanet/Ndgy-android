package com.shenhua.nandagy.ui.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * 图书馆
 * Created by Shenhua on 10/6/2016.
 * e-mail shenhuanet@126.com
 */
public class LibraryActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        ButterKnife.bind(this);
    }
}
