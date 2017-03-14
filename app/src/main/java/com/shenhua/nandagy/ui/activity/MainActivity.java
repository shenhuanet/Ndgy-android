package com.shenhua.nandagy.ui.activity;

import android.content.res.TypedArray;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.callback.ProgressEventBus;
import com.shenhua.nandagy.ui.fragment.home.HomeFragment;
import com.shenhua.nandagy.ui.fragment.jiaowu.JiaoWuFragment;
import com.shenhua.nandagy.ui.fragment.more.MoreFragment;
import com.shenhua.nandagy.ui.fragment.more.UserFragment;
import com.shenhua.nandagy.ui.fragment.xuegong.XueGongFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * MainActivity
 * Created by Shenhua on 8/28/2016.
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_main,
        toolbarId = R.id.common_toolbar,
        toolbarTitle = R.string.home
)
public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener {

    @BindView(R.id.tab_host)
    FragmentTabHost tabHost;
    @BindView(R.id.toolbar_pro)
    ProgressBar progressBar;
    private static long firstTime;

    @Override
    protected void initView(BaseActivity baseActivity) {
//        BmobUpdateAgent.update(this);
//        BmobUpdateAgent.setUpdateOnlyWifi(false);
        ButterKnife.bind(this);
        setToolbarTitle(R.id.toolbar_title);
        setupTabHost();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void setupTabHost() {
        Class[] fragments = new Class[]{HomeFragment.class, XueGongFragment.class,
                JiaoWuFragment.class, MoreFragment.class, UserFragment.class};
        final String titles[] = getResources().getStringArray(R.array.main_tabs_titles);
        int[] icons = new int[titles.length];
        TypedArray ar = getResources().obtainTypedArray(R.array.main_tabs_images);
        for (int i = 0; i < titles.length; i++) {
            icons[i] = ar.getResourceId(i, 0);
        }
        ar.recycle();
        tabHost.setup(this, getSupportFragmentManager(), R.id.content_frame);
        tabHost.setOnTabChangedListener(this);
        tabHost.getTabWidget().setDividerDrawable(null);
        for (int i = 0; i < titles.length; i++) {
            View tabIndicatorView = LayoutInflater.from(this).inflate(R.layout.item_tabhost, null, false);
            TextView titleTv = (TextView) tabIndicatorView.findViewById(R.id.tv_main_tab);
            titleTv.setText(titles[i]);
            titleTv.setCompoundDrawablesWithIntrinsicBounds(0, icons[i], 0, 0);
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(titles[i]).setIndicator(tabIndicatorView);
            tabHost.addTab(tabSpec, fragments[i], null);
        }
        tabHost.setCurrentTab(0);
    }

    @Override
    public void onTabChanged(String tabId) {
        setToolbarTitle(tabId, R.id.toolbar_title);
    }

    public void onEventMainThread(ProgressEventBus event) {
        if (event.show()) progressBar.setVisibility(View.VISIBLE);
        else progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (firstTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            MainActivity.this.finish();
            System.exit(0);
        } else {
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_LONG)
                    .show();
        }
        firstTime = System.currentTimeMillis();
    }

}
