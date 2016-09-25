package com.shenhua.nandagy.frag;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.activity.MoreScoreActivity;
import com.shenhua.nandagy.activity.WebActivity;
import com.shenhua.nandagy.base.BaseFragment;
import com.shenhua.nandagy.widget.BannerView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 更多界面
 * Created by Shenhua on 8/28/2016.
 */
public class MoreFragment extends BaseFragment {

    @Bind(R.id.banner)
    BannerView bannerView;
    @Bind(R.id.tabrow_score)
    View mScoreTabrow;
    @Bind(R.id.tabrow_study)
    View mStudyTabrow;
    @Bind(R.id.tabrow_employ)
    View mEmployTabrow;
    @Bind(R.id.tabrow_circle)
    View mCircleTabrow;
    @Bind(R.id.tabrow_community)
    View mCommunityTabrow;
    @Bind(R.id.tabrow_lost)
    View mLostTabrow;
    @Bind(R.id.tabrow_paper)
    View mPaperTabrow;
    @Bind(R.id.tabrow_poyang)
    View mPoyangTabrow;
    private View view;
    private String[] tabTitles;
    private TextView[] tvs = new TextView[8];
    private ImageView[] ivs = new ImageView[8];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.frag_more, container, false);
            ButterKnife.bind(this, view);
            initTableView();
        }
        ViewGroup group = (ViewGroup) view.getParent();
        if (group != null)
            group.removeView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String[] images = {
                "http://img1.3lian.com/img013/v4/57/d/111.jpg",
                "http://pic.58pic.com/58pic/12/31/48/18658PICXxM.jpg",
                "http://tupian.enterdesk.com/2013/lxy/",
                "http://tupian.enterdesk.com/2013/mxy/12/07/3/9.jpg",
                "http://img5.imgtn.bdimg.com/it/u=2071825957,3967640546&fm=21&gp=0.jpg"};
        String[] titles = {
                "支持SPDY",
                "可以合并多个到同一个主机的请求",
                "使用GZIP压缩减少传输的数据量",
                "缓存响应避免重复的网络请求、拦截器",
                "第一缺点是消息回来需要切到主线程，主线程要自己去写"};
        bannerView.setBannerStyle(BannerView.BannerViewConfig.CIRCLE_INDICATOR);
        bannerView.setImageArray(images);
        bannerView.setDefaultErrorImage(R.drawable.img_default_error);
        bannerView.setOnBannerClickListener(new BannerView.OnBannerItemClickListener() {
            @Override
            public void OnBannerClick(View view, int position) {
                Toast.makeText(getActivity(), images[position], Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initTableView() {
        tvs[0] = (TextView) mScoreTabrow.findViewById(R.id.list_tv);
        tvs[1] = (TextView) mStudyTabrow.findViewById(R.id.list_tv);
        tvs[2] = (TextView) mEmployTabrow.findViewById(R.id.list_tv);
        tvs[3] = (TextView) mCircleTabrow.findViewById(R.id.list_tv);
        tvs[4] = (TextView) mCommunityTabrow.findViewById(R.id.list_tv);
        tvs[5] = (TextView) mLostTabrow.findViewById(R.id.list_tv);
        tvs[6] = (TextView) mPaperTabrow.findViewById(R.id.list_tv);
        tvs[7] = (TextView) mPoyangTabrow.findViewById(R.id.list_tv);

        ivs[0] = (ImageView) mScoreTabrow.findViewById(R.id.list_iv);
        ivs[1] = (ImageView) mStudyTabrow.findViewById(R.id.list_iv);
        ivs[2] = (ImageView) mEmployTabrow.findViewById(R.id.list_iv);
        ivs[3] = (ImageView) mCircleTabrow.findViewById(R.id.list_iv);
        ivs[4] = (ImageView) mCommunityTabrow.findViewById(R.id.list_iv);
        ivs[5] = (ImageView) mLostTabrow.findViewById(R.id.list_iv);
        ivs[6] = (ImageView) mPaperTabrow.findViewById(R.id.list_iv);
        ivs[7] = (ImageView) mPoyangTabrow.findViewById(R.id.list_iv);

        tabTitles = getContext().getResources().getStringArray(R.array.more_tabs_titles);
        TypedArray ar = getResources().obtainTypedArray(R.array.more_tabs_images);
        for (int i = 0; i < tvs.length; i++) {
            tvs[i].setText(tabTitles[i]);
            ivs[i].setImageResource(ar.getResourceId(i, 0));
        }
        ar.recycle();
    }

    @OnClick({R.id.tabrow_score, R.id.tabrow_study, R.id.tabrow_employ, R.id.tabrow_circle,
            R.id.tabrow_community, R.id.tabrow_lost, R.id.tabrow_paper, R.id.tabrow_poyang,})
    void onClicks(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tabrow_score:
                intent = new Intent(getActivity(), MoreScoreActivity.class);
                sceneTransitionTo(intent, 0, view, R.id.list_iv, "list");
                break;
            case R.id.tabrow_study:
                toast(tabTitles[1]);
                break;
            case R.id.tabrow_employ:
                intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("title", tabTitles[2]);
                intent.putExtra("url", "school-employment.html");
                sceneTransitionTo(intent, 0, view, R.id.list_tv, "title");
                break;
            case R.id.tabrow_circle:
                toast(tabTitles[3]);
                break;
            case R.id.tabrow_community:
                toast(tabTitles[4]);
                break;
            case R.id.tabrow_lost:
                toast(tabTitles[5]);
                break;
            case R.id.tabrow_paper:
                toast(tabTitles[6]);
                break;
            case R.id.tabrow_poyang:
                intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("title", tabTitles[7]);
                intent.putExtra("url", "school-poyang.html");
                sceneTransitionTo(intent, 0, view, R.id.list_tv, "title");
                break;
        }
    }
}
