package com.shenhua.nandagy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shenhua on 2/13/2017.
 * Email shenhuanet@126.com
 */
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_test)
    void click() {
        Toast.makeText(this, "点击了", Toast.LENGTH_SHORT).show();
    }
}
