package com.shenhua.nandagy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * 学生资助
 * Created by Shenhua on 10/6/2016.
 * e-mail shenhuanet@126.com
 */
public class FinanceActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);
        ButterKnife.bind(this);
    }
}
