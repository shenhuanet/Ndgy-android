package com.shenhua.nandagy.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.nandagy.R;
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
        toolbarHomeAsUp = true
)
public class WebActivity extends BaseActivity {

    @BindView(R.id.content_web)
    WebView mWebView;
    @BindView(R.id.content_pro)
    ProgressBar mProgressBar;
    private boolean isLoading, isInit;
    private String JsTag = "img_view";
    private String url;
    private String imgUrl;

    @Override
    protected void initView(BaseActivity baseActivity) {
        ButterKnife.bind(this);
        Intent intent = getIntent();
        setToolbarTitle(intent.getStringExtra("title"), R.id.toolbar_title);
        url = intent.getStringExtra("url");
    }

    @SuppressLint("JavascriptInterface")
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
            mWebView.addJavascriptInterface(new JavascriptInterface(this), JsTag);
            mWebView.setWebViewClient(new MyWebViewClient());
            mWebView.loadUrl(HttpService.SCHOOL_WEB_HOST + url);
            isInit = true;
        }
    }

    public class JavascriptInterface {
        private Context context;

        public JavascriptInterface(Context context) {
            this.context = context;
        }

        public void openImage(String img) {
            System.out.println("clickImgUrl:" + img);
            context.startActivity(new Intent(context, WebActivity.class).putExtra("url", img));
        }
    }

    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        mWebView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window." + JsTag + ".openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuItem.OnMenuItemClickListener handler = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == 0) {
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
        MenuItem menuItem = menu.findItem(R.id.action_refresh);
        if (isLoading) menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_menu_stop));
        else menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_menu_refresh));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            if (isLoading) mWebView.stopLoading();
            else mWebView.reload();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            isLoading = true;
            invalidateOptionsMenu();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            isLoading = false;
            invalidateOptionsMenu();
            mProgressBar.setVisibility(View.GONE);
            addImageClickListner();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
