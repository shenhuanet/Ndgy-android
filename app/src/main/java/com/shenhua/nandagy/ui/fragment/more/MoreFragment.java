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

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.ui.activity.more.CircleActivity;
import com.shenhua.nandagy.ui.activity.more.CommunityActivity;
import com.shenhua.nandagy.ui.activity.more.LostFoundActivity;
import com.shenhua.nandagy.ui.activity.more.PaperPlaneActivity;
import com.shenhua.nandagy.ui.activity.more.ScoreQueryActivity;
import com.shenhua.nandagy.ui.activity.more.StudyAreaActivity;
import com.shenhua.nandagy.ui.activity.WebActivity;
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
                "http://www.ndgy.cn/UploadFiles/2013-06/admin/2013060716091695372.jpg",
                "http://www.ndgy.cn/UploadFiles/2013-06/admin/2013060716043094556.jpg",
                "http://www.ndgy.cn/UploadFiles/2013-06/admin/201306071624476077.jpg",
                "http://www.ndgy.cn/UploadFiles/2013-06/admin/2013060716251384101.jpg",
                "http://www.ndgy.cn/UploadFiles/2013-06/admin/2013060716131218116.jpg",
                "http://www.ndgy.cn/UploadFiles/2013-06/admin/2013060716152035466.jpg"
        };
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
                intent = new Intent(getActivity(), ScoreQueryActivity.class);
                sceneTransitionTo(intent, 0, mScoreTabrow, R.id.list_iv, "list");
                break;
            case R.id.tabrow_study:
                sceneTransitionTo(new Intent(getActivity(), StudyAreaActivity.class)
                        , 0, mStudyTabrow, R.id.list_tv, "title");
                break;
            case R.id.tabrow_employ:
                intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("title", tabTitles[2]);
                intent.putExtra("url", "school-employment.html");
                sceneTransitionTo(intent, 0, mEmployTabrow, R.id.list_tv, "title");
                break;
            case R.id.tabrow_circle:
                sceneTransitionTo(new Intent(getActivity(), CircleActivity.class)
                        , 0, mCircleTabrow, R.id.list_tv, "title");
                break;
            case R.id.tabrow_community:
                sceneTransitionTo(new Intent(getActivity(), CommunityActivity.class)
                        , 0, mCommunityTabrow, R.id.list_tv, "title");
                break;
            case R.id.tabrow_lost:
                sceneTransitionTo(new Intent(getActivity(), LostFoundActivity.class)
                        , 0, mLostTabrow, R.id.list_tv, "title");
                break;
            case R.id.tabrow_paper:
                sceneTransitionTo(new Intent(getActivity(), PaperPlaneActivity.class)
                        , 0, mPaperTabrow, R.id.list_tv, "title");
                break;
            case R.id.tabrow_poyang:
                intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("title", tabTitles[7]);
                intent.putExtra("url", "school-poyang.html");
                sceneTransitionTo(intent, 0, mPoyangTabrow, R.id.list_tv, "title");
                break;
        }
    }
}
