package com.shenhua.nandagy.ui.activity.more;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.adapter.StudyViewPagerAdapter;
import com.shenhua.nandagy.base.BaseActivity;
import com.shenhua.nandagy.bean.StudyListData;
import com.shenhua.nandagy.ui.fragment.more.StudyListFragment;
import com.shenhua.nandagy.view.StudyListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 学习专区
 * Created by Shenhua on 9/7/2016.
 */
public class StudyAreaActivity extends BaseActivity implements StudyListView {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.appbar)
    AppBarLayout mAppbar;
    @Bind(R.id.tabLayout)
    TabLayout mTableLayout;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
//        }
        setContentView(R.layout.activity_more_study);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            mToolbar.setPadding(0, 40, 0, 0);
//        }
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

    @Override
    public void updateList(List<StudyListData> datas) {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
