package com.shenhua.nandagy.ui.fragment.home;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

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
@ActivityFragmentInject(contentViewId = R.layout.frag_home_collapsing)
public class HomeFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {

    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.img_banner)
    BannerView mHomeImgIv;
    @BindView(R.id.gv_home_tool)
    InnerGridView mInnerGridView;
    private Fragment[] fragments = new Fragment[2];
    private SparseBooleanArray sba = new SparseBooleanArray(2);

    @Override
    public void onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, View rootView) {
        ButterKnife.bind(this, rootView);
        sba.put(0, false);
        sba.put(1, false);
        String[] titles = getActivity().getResources().getStringArray(R.array.home_items_titles);
        for (String t : titles) {
            mTabLayout.addTab(mTabLayout.newTab().setText(t));
        }
        mTabLayout.setOnTabSelectedListener(this);
        onTabUpdate(0);
        toGetHomeImg();
    }

    private void toGetHomeImg() {
        mHomeImgIv.setBannerImageArray(HttpService.HOME_IMG_URL);
        mHomeImgIv.setDelayTime(20000);
        setupToolView(mInnerGridView, R.array.home_tabs_titles, R.array.home_tabs_images);
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
        if (tab == 0 && !sba.get(0)) {
            fragments[0] = HomeGzFragment.newInstance();
            sba.put(0, true);
        }
        if (tab == 1 && !sba.get(1)) {
            fragments[1] = HomeGgFragment.newInstance();
            sba.put(1, true);
        }
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_content, fragments[tab]);
        ft.commit();
    }

    public void setupToolView(AbsListView abs, int titlesResId, int imagesResId) {
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
        Class[] classes = {WebActivity.class, WebActivity.class, WebActivity.class,
                GroupActivity.class, PartyActivity.class, LibraryActivity.class};
        BaseListAdapter adapter = new BaseListAdapter<BaseImageTextItem>(getContext(), items) {

            @Override
            public void onBindItemView(BaseViewHolder baseViewHolder, BaseImageTextItem item, int position) {
                baseViewHolder.setImageResource(R.id.iv_img, item.getDrawable());
                baseViewHolder.setText(R.id.tv_title, item.getTitle());
                baseViewHolder.setOnListItemClickListener(view -> {
                    Intent intent = new Intent(getContext(), classes[position]);
                    if (position == 0) {
                        intent.putExtra("title", "学院简介");
                        intent.putExtra("url", "school-briefing.html");
                    } else if (position == 1) {
                        intent.putExtra("title", "学院风景");
                        intent.putExtra("url", "school-scenery.html");
                    } else if (position == 2) {
                        intent.putExtra("title", "学院地图");
                        intent.putExtra("url", "school-map.html");
                    }
                    sceneTransitionTo(intent, 0, view, R.id.tv_title, "title");
                });
            }

            @Override
            public int getItemViewId() {
                return R.layout.item_common_imgtv;
            }
        };
        abs.setAdapter(adapter);
    }

}
