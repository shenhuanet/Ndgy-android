package com.shenhua.nandagy.base;

import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.bmobbean.BombUtil;
import com.umeng.analytics.MobclickAgent;

import cn.bmob.v3.Bmob;

/**
 * activity基类
 * Created by shenhua on 2016/4/27.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar mToolbar;
    NetworkReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initFitsWindow();
        Bmob.initialize(this, BombUtil.APP_KEY);
        receiver = new NetworkReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    protected ActionBar setupActionBar(int titleResId) {
        if (titleResId != 0) {
            return setupActionBar(getString(titleResId), true);
        }
        return null;
    }

    protected ActionBar setupActionBar(String titleResId) {
        if (titleResId != null) {
            return setupActionBar(titleResId, true);
        }
        return null;
    }

    protected ActionBar setupActionBar(boolean showAsUpEnabled, String title) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar == null) return null;
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setTitle(title);
        ab.setDisplayHomeAsUpEnabled(showAsUpEnabled);
        ab.setDisplayShowHomeEnabled(showAsUpEnabled);
        return ab;
    }

    protected ActionBar setupActionBar(int titleResId, boolean showAsUpEnabled) {
        if (titleResId != 0) {
            return setupActionBar(getString(titleResId), showAsUpEnabled);
        }
        return null;
    }

    protected ActionBar setupActionBar(String title, boolean showAsUpEnabled) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar == null) return null;
        if (title != null) {
            TextView toolBarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
            if (toolBarTitle != null)
                toolBarTitle.setText(title);
        }
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setTitle("");
        ab.setDisplayHomeAsUpEnabled(showAsUpEnabled);
        ab.setDisplayShowHomeEnabled(showAsUpEnabled);
        return ab;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (Build.VERSION.SDK_INT > 21)
                finishAfterTransition();
            else
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initFitsWindow() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public class NetworkReceiver extends BroadcastReceiver {

        public NetworkReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {// 有网
                onNetWorkIsOk();
            } else {
                onNetWorkIsError();
            }
        }
    }

    protected void onNetWorkIsOk() {

    }

    protected void onNetWorkIsError() {

    }

    public void SceneTransitionTo(Intent intent, View view, int viewId, String sharedElementName) {
        if (Build.VERSION.SDK_INT > 21) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,
                    view.findViewById(viewId), sharedElementName);
            startActivity(intent, options.toBundle());
        } else {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(view,
                    view.getWidth() / 2, view.getHeight() / 2, 0, 0);
            ActivityCompat.startActivity(this, intent, options.toBundle());
        }
    }

    public void LollipopStartActivity() {
//        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                activity, transitionView, DetailActivity.EXTRA_IMAGE);
//        ActivityCompat.startActivity(activity, newIntent(activity, DetailActivity.class),
//                options.toBundle());
    }

    public void hideKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
