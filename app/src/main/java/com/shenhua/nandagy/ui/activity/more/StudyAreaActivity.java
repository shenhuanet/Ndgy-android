package com.shenhua.nandagy.ui.activity.more;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.adapter.StudyViewPagerAdapter;
import com.shenhua.nandagy.base.BaseActivity;
import com.shenhua.nandagy.ui.fragment.more.StudyListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 学习专区
 * Created by Shenhua on 9/7/2016.
 */
public class StudyAreaActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.tabLayout)
    TabLayout mTableLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_study);
        ButterKnife.bind(this);
        setupActionBar("学习专区", true);
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
