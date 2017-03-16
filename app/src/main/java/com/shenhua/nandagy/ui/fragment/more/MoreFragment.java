package com.shenhua.nandagy.ui.fragment.more;

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

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.commonlibs.base.BaseFragment;
import com.shenhua.libs.bannerview.BannerView;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.ui.activity.WebActivity;
import com.shenhua.nandagy.ui.activity.more.CircleActivity;
import com.shenhua.nandagy.ui.activity.more.CommunityActivity;
import com.shenhua.nandagy.ui.activity.more.LostFoundActivity;
import com.shenhua.nandagy.ui.activity.more.PaperPlaneActivity;
import com.shenhua.nandagy.ui.activity.more.ScoreQueryActivity;
import com.shenhua.nandagy.ui.activity.more.StudyAreaActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 更多界面
 * Created by Shenhua on 8/28/2016.
 */
@ActivityFragmentInject(contentViewId = R.layout.frag_more)
public class MoreFragment extends BaseFragment {

    @BindView(R.id.banner)
    BannerView bannerView;
    @BindView(R.id.tabrow_score)
    View mScoreTabRow;
    @BindView(R.id.tabrow_study)
    View mStudyTabRow;
    @BindView(R.id.tabrow_employ)
    View mEmployTabRow;
    @BindView(R.id.tabrow_circle)
    View mCircleTabRow;
    @BindView(R.id.tabrow_community)
    View mCommunityTabRow;
    @BindView(R.id.tabrow_lost)
    View mLostTabRow;
    @BindView(R.id.tabrow_paper)
    View mPaperTabRow;
    @BindView(R.id.tabrow_poyang)
    View mPoyangTabRow;
    private String[] tabTitles;
    private TextView[] tvs = new TextView[8];
    private ImageView[] ivs = new ImageView[8];

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
        bannerView.setImageArray(images);
//        bannerView.setDefaultErrorImage(R.drawable.img_default_error);
        bannerView.setOnBannerClickListener((view1, position) -> Toast.makeText(getActivity(), images[position], Toast.LENGTH_SHORT).show());
    }

    private void initTabView() {
        tvs[0] = (TextView) mScoreTabRow.findViewById(R.id.list_tab_tv);
        tvs[1] = (TextView) mStudyTabRow.findViewById(R.id.list_tab_tv);
        tvs[2] = (TextView) mEmployTabRow.findViewById(R.id.list_tab_tv);
        tvs[3] = (TextView) mCircleTabRow.findViewById(R.id.list_tab_tv);
        tvs[4] = (TextView) mCommunityTabRow.findViewById(R.id.list_tab_tv);
        tvs[5] = (TextView) mLostTabRow.findViewById(R.id.list_tab_tv);
        tvs[6] = (TextView) mPaperTabRow.findViewById(R.id.list_tab_tv);
        tvs[7] = (TextView) mPoyangTabRow.findViewById(R.id.list_tab_tv);

        ivs[0] = (ImageView) mScoreTabRow.findViewById(R.id.list_tab_iv);
        ivs[1] = (ImageView) mStudyTabRow.findViewById(R.id.list_tab_iv);
        ivs[2] = (ImageView) mEmployTabRow.findViewById(R.id.list_tab_iv);
        ivs[3] = (ImageView) mCircleTabRow.findViewById(R.id.list_tab_iv);
        ivs[4] = (ImageView) mCommunityTabRow.findViewById(R.id.list_tab_iv);
        ivs[5] = (ImageView) mLostTabRow.findViewById(R.id.list_tab_iv);
        ivs[6] = (ImageView) mPaperTabRow.findViewById(R.id.list_tab_iv);
        ivs[7] = (ImageView) mPoyangTabRow.findViewById(R.id.list_tab_iv);

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
        Intent intent = null;
        View view = null;
        switch (v.getId()) {
            case R.id.tabrow_score:
                intent = new Intent(getActivity(), ScoreQueryActivity.class);
                view = mScoreTabRow;
                ((BaseActivity) getActivity()).sceneTransitionTo(intent, 0, view, R.id.list_tab_iv, "list");
                return;
            case R.id.tabrow_study:
                intent = new Intent(getActivity(), StudyAreaActivity.class);
                view = mStudyTabRow;
                break;
            case R.id.tabrow_employ:
                intent = new Intent(getActivity(), WebActivity.class);
                view = mEmployTabRow;
                intent.putExtra("title", tabTitles[2]);
                intent.putExtra("url", "school-employment.html");
                break;
            case R.id.tabrow_circle:
                intent = new Intent(getActivity(), CircleActivity.class);
                view = mCircleTabRow;
                break;
            case R.id.tabrow_community:
                intent = new Intent(getActivity(), CommunityActivity.class);
                view = mCommunityTabRow;
                break;
            case R.id.tabrow_lost:
                intent = new Intent(getActivity(), LostFoundActivity.class);
                view = mLostTabRow;
                break;
            case R.id.tabrow_paper:
                intent = new Intent(getActivity(), PaperPlaneActivity.class);
                view = mPaperTabRow;
                break;
            case R.id.tabrow_poyang:
                intent = new Intent(getActivity(), WebActivity.class);
                view = mPoyangTabRow;
                intent.putExtra("title", tabTitles[7]);
                intent.putExtra("url", "school-poyang.html");
                break;
        }
        if (view != null) {
            ((BaseActivity) getActivity()).sceneTransitionTo(intent, 0, view, R.id.list_tab_tv, "title");
        }
    }
}
