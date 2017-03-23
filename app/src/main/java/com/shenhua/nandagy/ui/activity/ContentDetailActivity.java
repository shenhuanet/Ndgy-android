package com.shenhua.nandagy.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.commonlibs.mvp.BaseMvpActivity;
import com.shenhua.commonlibs.utils.ScreenUtils;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.HomeData;
import com.shenhua.nandagy.bean.scorebean.ContentDetailData;
import com.shenhua.nandagy.presenter.ContentDetailPresenter;
import com.shenhua.nandagy.view.ContentDetailView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 正文详情界面
 * Created by shenhua on 8/31/2016.
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_content_detail,
        toolbarId = R.id.common_toolbar,
        toolbarHomeAsUp = true,
        toolbarTitle = R.string.toolbar_title_content_detail
)
public class ContentDetailActivity extends BaseMvpActivity<ContentDetailPresenter, ContentDetailView> implements ContentDetailView {

    private static final String TAG = "ContentDetailActivity";
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.iv_detail_photo)
    ImageView mImageView;
    @BindView(R.id.tv_detail_title)
    TextView mTitleTv;
    @BindView(R.id.tv_detail_time)
    TextView mTimeTv;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.progress)
    ProgressBar mProgressBar;
    @BindView(R.id.web_content)
    WebView mWebView;
    protected ContentDetailPresenter mContentDetailPresenter;
    private String detail;
    private String mUrl;

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        ButterKnife.bind(this);
        HomeData data = (HomeData) getIntent().getExtras().getSerializable("data");
        initWebView();
        if (data == null) return;
        Glide.with(this).load(data.getImgUrl()).error(R.drawable.about_logo_pic).centerCrop().into(mImageView);
        mTitleTv.setText(data.getTitle());
        mTimeTv.setText(data.getTime());
        ActionBar actionBar = getToolbar();
        actionBar.setTitle(data.getTitle());
        mUrl = data.getHref();
    }

    @Override
    public ContentDetailPresenter createPresenter() {
        mContentDetailPresenter = new ContentDetailPresenter(this, mUrl);
        mContentDetailPresenter.execute();
        return mContentDetailPresenter;
    }

    @Override
    public void showContent(ContentDetailData html) {
        mTitleTv.setText(html.getTitle());
        mWebView.loadDataWithBaseURL("", html.getContent(), "text/html", "UTF-8", "");
        Document d = Jsoup.parse(html.getContent());
        detail = d.text();
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void initWebView() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        // TODO: 3/23/2017  chromium: [INFO:CONSOLE(1)] "Uncaught TypeError: window.imgClickListener.openImage is not a function", source:  (1)
//        mWebView.addJavascriptInterface(new JSInterface(this), "imgClickListener");
//        mWebView.setWebViewClient(new BaseWebViewClient());
    }

    @OnClick(R.id.fab)
    void share(View v) {
        if (!TextUtils.isEmpty(detail)) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
            intent.putExtra(Intent.EXTRA_TEXT, detail);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(Intent.createChooser(intent, "请选择要分享的应用"));
        }
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showToast(String msg) {
        toast(msg);
        if (msg.equals("数据解析失败")) {
            mWebView.loadUrl("file:///android_asset/html/common-dataerror.html");
        } else {
            mWebView.loadUrl("file:///android_asset/html/common-nonet.html");
        }
    }

    /**
     * 4.4设置全屏并动态修改Toolbar的位置实现类5.0效果，确保布局延伸到状态栏的效果
     *
     * @param toolbarLayout CollapsingToolbarLayout
     */
    private void materialCollapsingForKitkat(final CollapsingToolbarLayout toolbarLayout) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            // 设置全屏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            // 设置Toolbar对顶部的距离
            final Toolbar toolbar = (Toolbar) findViewById(R.id.common_toolbar);
            assert toolbar != null;
            final FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) toolbar.getLayoutParams();
            final int statusBarHeight = layoutParams.topMargin = ScreenUtils.getStatusBarHeight(this);
            // 算出伸缩移动的总距离
            final AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
            final int[] verticalMoveDistance = new int[1];
            toolbarLayout.getViewTreeObserver()
                    .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onGlobalLayout() {
                            verticalMoveDistance[0] = toolbarLayout
                                    .getMeasuredHeight() - ScreenUtils
                                    .getToolbarHeight(ContentDetailActivity.this);
                            toolbarLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    });
            assert appBarLayout != null;
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                int lastVerticalOffset = 0;

                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    // KLog.e(lastVerticalOffset + ";" + verticalOffset);
                    if (lastVerticalOffset != verticalOffset && verticalMoveDistance[0] != 0) {
                        layoutParams.topMargin = (int) (statusBarHeight - Math
                                .abs(verticalOffset) * 1.0f / verticalMoveDistance[0] * statusBarHeight);
                        // KLog.e(layoutParams.topMargin);
                        toolbar.setLayoutParams(layoutParams);
                    }
                    lastVerticalOffset = verticalOffset;
                }
            });
        }
    }
}
