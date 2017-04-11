package com.shenhua.nandagy.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.shenhua.nandagy.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    @BindView(R.id.iv_shenhua_logo)
    ImageView mShenhuaLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
        ace.callEndpoint("AD_Control", new CloudCodeListener() {
            @Override
            public void done(Object o, BmobException e) {
                if (e==null){
                    Log.d(TAG, "done: 云端回调结果："+o.toString());
                }else {
                    Log.d(TAG, "done: 云端回调结果：e is null");
                }
            }
        });

        mShenhuaLogo.postDelayed(this::navTo, 3000);
    }

    private void navTo() {
        Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
        SplashActivity.this.startActivity(mainIntent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        SplashActivity.this.finish();
    }
}
