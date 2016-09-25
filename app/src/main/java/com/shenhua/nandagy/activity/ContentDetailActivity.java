package com.shenhua.nandagy.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseActivity;
import com.shenhua.nandagy.utils.MeasureUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 正文详情界面
 * Created by shenhua on 8/31/2016.
 */
public class ContentDetailActivity extends BaseActivity {

    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.iv_detail_photo)
    ImageView mImageView;
    @Bind(R.id.tv_detail_title)
    TextView mTitleTv;
    @Bind(R.id.tv_detail_time)
    TextView mTimeTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_detail);
        ButterKnife.bind(this);
        mToolbarLayout.setTitle("正文详情");
        mToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.colorPrimary));
        mToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        materialCollapsingForKitkat(mToolbarLayout);
        setupActionBar("正文详情", true);
        mImageView.setImageResource(getIntent().getIntExtra("photo", R.drawable.img_default));
        mTitleTv.setText(getIntent().getStringExtra("title"));
        mTimeTv.setText(getIntent().getStringExtra("time"));
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
            final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            assert toolbar != null;
            final FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) toolbar.getLayoutParams();
            final int statusBarHeight = layoutParams.topMargin = MeasureUtil.getStatusBarHeight(this);
            // 算出伸缩移动的总距离
            final AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
            final int[] verticalMoveDistance = new int[1];
            toolbarLayout.getViewTreeObserver()
                    .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            verticalMoveDistance[0] = toolbarLayout
                                    .getMeasuredHeight() - MeasureUtil
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        }
    }
}
