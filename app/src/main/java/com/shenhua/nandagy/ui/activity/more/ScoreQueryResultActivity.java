package com.shenhua.nandagy.ui.activity.more;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.scorebean.ScoreCETBean;
import com.shenhua.nandagy.ui.fragment.scorequery.ScoreBecFragment;
import com.shenhua.nandagy.ui.fragment.scorequery.ScoreCAPFragment;
import com.shenhua.nandagy.ui.fragment.scorequery.ScoreCetResultFragment;
import com.shenhua.nandagy.ui.fragment.scorequery.ScoreCetkFragment;
import com.shenhua.nandagy.ui.fragment.scorequery.ScoreComputerFragment;
import com.shenhua.nandagy.ui.fragment.scorequery.ScoreFlushFragment;
import com.shenhua.nandagy.ui.fragment.scorequery.ScoreForeignFragment;
import com.shenhua.nandagy.ui.fragment.scorequery.ScoreMandarinFragment;
import com.shenhua.nandagy.ui.fragment.scorequery.ScoreNTCEFragment;
import com.shenhua.nandagy.ui.fragment.scorequery.ScorePETSFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 成绩查询结果
 * Created by Shenhua on 9/25/2016.
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_score_query_result,
        toolbarId = R.id.common_toolbar,
        toolbarHomeAsUp = true,
        toolbarTitle = R.string.toolbar_title_score_query_result,
        toolbarTitleId = R.id.toolbar_title
)
public class ScoreQueryResultActivity extends BaseActivity {

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        ButterKnife.bind(this);
        navFragment(getIntent().getIntExtra("type", -1), getIntent().getSerializableExtra("result"));
    }

    private void navFragment(int position, Object object) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = ScoreCetResultFragment.newInstance((ScoreCETBean) object);
                break;
            case 1:
                fragment = ScoreCetkFragment.newInstance();
                break;
            case 2:
                fragment = ScoreMandarinFragment.newInstance();
                break;
            case 3:
                fragment = ScorePETSFragment.newInstance();
                break;
            case 4:
                fragment = ScoreComputerFragment.newInstance();
                break;
            case 5:
                fragment = ScoreBecFragment.newInstance();
                break;
            case 6:
                fragment = ScoreFlushFragment.newInstance();
                break;
            case 7:
                fragment = ScoreNTCEFragment.newInstance();
                break;
            case 8:
                fragment = ScoreCAPFragment.newInstance();
                break;
            case 9:
                fragment = ScoreForeignFragment.newInstance();
                break;
        }
        navigateTo(fragment);
    }

    private void navigateTo(Fragment newFragment) {
        if (newFragment == null) return;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_score_result, newFragment);
        ft.commit();
    }

    @OnClick({R.id.btn_save_score, R.id.btn_share_score})
    void clicks(View v) {
        switch (v.getId()) {
            case R.id.btn_save_score:

                break;
            case R.id.btn_share_score:

                break;
        }
    }

}
