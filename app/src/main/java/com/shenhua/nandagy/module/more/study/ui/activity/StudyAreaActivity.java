package com.shenhua.nandagy.module.more.study.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.module.more.study.ui.adapter.StudyViewPagerAdapter;
import com.shenhua.nandagy.module.more.common.StudyListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 学习专区
 * Created by Shenhua on 9/7/2016.
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_more_study,
        toolbarId = R.id.common_toolbar,
        toolbarHomeAsUp = true,
        toolbarTitle = R.string.toolbar_title_more_study,
        toolbarTitleId = R.id.toolbar_title
)
public class StudyAreaActivity extends BaseActivity {

    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.tabLayout)
    TabLayout mTableLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initViewPager();
    }

    private void initViewPager() {
        mTableLayout.setSelectedTabIndicatorHeight(4);
        StudyViewPagerAdapter viewPagerAdapter = new StudyViewPagerAdapter(getSupportFragmentManager());
        String[] title = getResources().getStringArray(R.array.study_items);
        for (int i = 0; i < title.length; i++) {
            viewPagerAdapter.addFragment(StudyListFragment.getInstance(i), title[i]);
            mTableLayout.addTab(mTableLayout.newTab().setText(title[i]));
        }
        mViewPager.setAdapter(viewPagerAdapter);
        mTableLayout.setupWithViewPager(mViewPager);
    }
}
