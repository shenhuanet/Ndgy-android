package com.shenhua.nandagy.ui.fragment.home;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseFragment;
import com.shenhua.commonlibs.base.BaseImageTextItem;
import com.shenhua.commonlibs.base.BaseListAdapter;
import com.shenhua.commonlibs.widget.InnerGridView;
import com.shenhua.libs.bannerview.BannerView;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.service.HttpService;
import com.shenhua.nandagy.ui.activity.WebActivity;
import com.shenhua.nandagy.ui.activity.home.GroupActivity;
import com.shenhua.nandagy.ui.activity.home.LibraryActivity;
import com.shenhua.nandagy.ui.activity.home.PartyActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页
 * Created by Shenhua on 8/28/2016.
 */
@ActivityFragmentInject(
        contentViewId = R.layout.frag_home
)
public class HomeFragment extends BaseFragment implements TabLayout.OnTabSelectedListener, GridView.OnItemClickListener {

    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.img_banner)
    BannerView mHomeImgIv;
    @BindView(R.id.gv_home_tool)
    InnerGridView mInnerGridView;

    @Override
    public void onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, View rootView) {
        ButterKnife.bind(this, rootView);
        String[] titles = getActivity().getResources().getStringArray(R.array.home_items_titles);
        for (String t : titles) {
            mTabLayout.addTab(mTabLayout.newTab().setText(t));
        }
        mTabLayout.setOnTabSelectedListener(this);
        onTabUpdate(0);
        toGetHomeImg();
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

    public void makeToolView(AbsListView abs, int titlesResId, int imagesResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            abs.setNestedScrollingEnabled(false);
        }
        List<BaseImageTextItem> items = new ArrayList<>();
        String[] titles = getResources().getStringArray(titlesResId);
        TypedArray ar = getResources().obtainTypedArray(imagesResId);
        for (int i = 0; i < titles.length; i++) {
            BaseImageTextItem item = new BaseImageTextItem(ar.getResourceId(i, 0), titles[i]);
            items.add(item);
        }
        ar.recycle();
        BaseListAdapter adapter = new BaseListAdapter<BaseImageTextItem>(getActivity(), items) {

            @Override
            public void onBindItemView(BaseViewHolder baseViewHolder, BaseImageTextItem item, int i) {
                baseViewHolder.setImageResource(R.id.iv_img, item.getDrawable());
                baseViewHolder.setText(R.id.tv_title, item.getTitle());
            }

            @Override
            public int getItemViewId() {
                return R.layout.item_common_imgtv;
            }
        };
        abs.setAdapter(adapter);
    }

}
