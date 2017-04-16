package com.shenhua.nandagy.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.commonlibs.base.BaseWebViewClient;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.callback.WebImageJSInterface;
import com.shenhua.nandagy.service.HttpService;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 网页浏览的web
 * Created by Shenhua on 9/19/2016.
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_web,
        toolbarId = R.id.common_toolbar,
        toolbarHomeAsUp = true,
        toolbarTitleId = R.id.toolbar_title
)
public class WebActivity extends BaseActivity {

    @BindView(R.id.web_content)
    WebView mWebView;
    @BindView(R.id.content_pro)
    ProgressBar mProgressBar;
    private boolean isLoading, isInit;
    private String url, imgUrl;

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        ButterKnife.bind(this);
        Intent intent = getIntent();
        setupToolbarTitle(intent.getStringExtra("title"));
        url = intent.getStringExtra("url");
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void onResume() {
        super.onResume();
        if (!isInit) {
            this.registerForContextMenu(mWebView);
            WebSettings settings = mWebView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setAllowContentAccess(true);
            settings.setBuiltInZoomControls(false);
            settings.setSupportZoom(true);
            mWebView.setDrawingCacheEnabled(true);
            mWebView.addJavascriptInterface(new WebImageJSInterface(this), "imgClickListener");
            mWebView.setWebViewClient(new MyWebViewClient());
            mWebView.loadUrl(HttpService.SCHOOL_WEB_HOST + url);
            isInit = true;
        }
    }

    // TODO: 3/28/2017 shouldn't use
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuItem.OnMenuItemClickListener handler = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == 0) {
                    toast("还未实现");
//                    startActivity(new Intent(ContentActivity.this, ViewImgActivity.class).putExtra("url", imgUrl));
                } else {
                    toast("还未实现");
                }
                return true;
            }
        };
        if (v instanceof WebView) {
            WebView.HitTestResult result = ((WebView) v).getHitTestResult();
            int type = result.getType();
            if (type == WebView.HitTestResult.IMAGE_TYPE || type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                imgUrl = result.getExtra();
                menu.add(0, 0, 0, "查看图片")
                        .setOnMenuItemClickListener(handler);
                menu.add(0, 1, 0, "保存图片")
                        .setOnMenuItemClickListener(handler);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItem = menu.findItem(R.id.action_menu_refresh);
        if (isLoading) menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_menu_stop));
        else menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_menu_refresh));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_menu_refresh) {
            if (isLoading) mWebView.stopLoading();
            else mWebView.reload();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyWebViewClient extends BaseWebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            isLoading = true;
            invalidateOptionsMenu();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            isLoading = false;
            invalidateOptionsMenu();
            mProgressBar.setVisibility(View.GONE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack() && event.getRepeatCount() == 0) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
