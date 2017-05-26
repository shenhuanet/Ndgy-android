package com.shenhua.nandagy.module.more.common;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseFragment;
import com.shenhua.libs.bannerview.BannerView;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.module.acommon.ui.activity.WebActivity;
import com.shenhua.nandagy.module.more.circle.ui.activity.CircleActivity;
import com.shenhua.nandagy.module.more.community.ui.activity.CommunityActivity;
import com.shenhua.nandagy.module.more.lostfound.ui.activity.LostFoundActivity;
import com.shenhua.nandagy.module.more.pager.ui.activity.PaperPlaneActivity;
import com.shenhua.nandagy.module.more.score.ui.activity.ScoreQueryActivity;
import com.shenhua.nandagy.module.more.study.ui.activity.StudyAreaActivity;
import com.shenhua.nandagy.widget.NineGridView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 更多界面
 * Created by Shenhua on 8/28/2016.
 */
@ActivityFragmentInject(contentViewId = R.layout.frag_more)
public class MoreFragment extends BaseFragment {

    @BindView(R.id.banner)
    BannerView bannerView;
    @BindView(R.id.nineGridView)
    NineGridView mNineGridView;

    private Class[] aClass = {ScoreQueryActivity.class, StudyAreaActivity.class,
            WebActivity.class, CircleActivity.class, CommunityActivity.class,
            LostFoundActivity.class, PaperPlaneActivity.class, WebActivity.class};

    @Override
    public void onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, View rootView) {
        ButterKnife.bind(this, rootView);
        initTabView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String[] images = {
                "http://www.ndgy.cn/UploadFiles/2013-06/admin/2013060716091695372.jpg",
                "http://www.ndgy.cn/UploadFiles/2013-06/admin/2013060716043094556.jpg",
                "http://www.ndgy.cn/UploadFiles/2013-06/admin/201306071624476077.jpg",
                "http://www.ndgy.cn/UploadFiles/2013-06/admin/2013060716251384101.jpg",
                "http://www.ndgy.cn/UploadFiles/2013-06/admin/2013060716131218116.jpg",
                "http://www.ndgy.cn/UploadFiles/2013-06/admin/2013060716152035466.jpg"
        };
        bannerView.setBannerStyle(BannerView.BannerViewConfig.CIRCLE_INDICATOR);
        bannerView.setBannerImageArray(images);
        bannerView.setOnBannerClickListener((view1, position) -> {
            toast(images[position]);
        });
    }

    private void initTabView() {
        int[] images;
        String[] titiels = getContext().getResources().getStringArray(R.array.more_tabs_titles);
        TypedArray ar = getResources().obtainTypedArray(R.array.more_tabs_images);
        // 判断
        if (titiels.length != ar.length()) {
            toast("TabView titles length are not same as images");
            ar.recycle();
            return;
        }
        // 装载images
        images = new int[ar.length()];
        for (int i = 0; i < ar.length(); i++) {
            images[i] = ar.getResourceId(i, 0);
        }
        ar.recycle();
        mNineGridView.setupNineGridView(images, titiels);
        mNineGridView.setOnItemClickListener((view, position) -> {
            Intent intent = new Intent(getContext(), aClass[position]);
            if (position == 2) {
                intent.putExtra("title", titiels[2]);
                intent.putExtra("url", "school-employment.html");
            } else if (position == 7) {
                intent.putExtra("title", titiels[7]);
                intent.putExtra("url", "school-poyang.html");
            }
            sceneTransitionTo(intent, 0, view, R.id.textView, "title");
        });
    }
}
