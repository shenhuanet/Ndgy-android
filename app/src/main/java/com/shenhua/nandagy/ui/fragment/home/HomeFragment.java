package com.shenhua.nandagy.ui.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.shenhua.commonlibs.widget.InnerGridView;
import com.shenhua.libs.bannerview.BannerView;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseFragment;
import com.shenhua.nandagy.service.HttpService;
import com.shenhua.nandagy.ui.activity.WebActivity;
import com.shenhua.nandagy.ui.activity.home.GroupActivity;
import com.shenhua.nandagy.ui.activity.home.LibraryActivity;
import com.shenhua.nandagy.ui.activity.home.PartyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页
 * Created by Shenhua on 8/28/2016.
 */
public class HomeFragment extends BaseFragment implements TabLayout.OnTabSelectedListener, GridView.OnItemClickListener {

    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.img_banner)
    BannerView mHomeImgIv;
    @BindView(R.id.gv_home_tool)
    InnerGridView mInnerGridView;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.frag_home, container, false);
            ButterKnife.bind(this, view);
            String[] titles = getActivity().getResources().getStringArray(R.array.home_items_titles);
            for (String t : titles) {
                mTabLayout.addTab(mTabLayout.newTab().setText(t));
            }
            mTabLayout.setOnTabSelectedListener(this);
            onTabUpdate(0);
            toGetHomeImg();
        }
        ViewGroup group = (ViewGroup) view.getParent();
        if (group != null)
            group.removeView(view);
        return view;
    }

    private void toGetHomeImg() {
        mHomeImgIv.setImageArray(HttpService.HOME_IMG_URL);
        mHomeImgIv.setDelayTime(20000);
        makeToolView(mInnerGridView, R.array.home_tabs_titles, R.array.home_tabs_images);
        mInnerGridView.setOnItemClickListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        onTabUpdate(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    private void onTabUpdate(int tab) {
        Fragment fragment;
        if (tab == 0) {
            fragment = HomeGzFragment.newInstance();
        } else {
            fragment = HomeGgFragment.newInstance();
        }
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_content, fragment);
        ft.commit();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("title", "学院简介");
                intent.putExtra("url", "school-briefing.html");
                break;
            case 1:
                intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("title", "学院风景");
                intent.putExtra("url", "school-scenery.html");
                break;
            case 2:
                intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("title", "学院地图");
                intent.putExtra("url", "school-map.html");
                break;
            case 3:
                intent = new Intent(getActivity(), GroupActivity.class);
                break;
            case 4:
                intent = new Intent(getActivity(), PartyActivity.class);
                break;
            case 5:
                intent = new Intent(getActivity(), LibraryActivity.class);
                break;
        }
        if (intent != null)
            sceneTransitionTo(intent, 0, view, R.id.tv_title, "title");
    }
}
