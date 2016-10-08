package com.shenhua.nandagy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseActivity;
import com.shenhua.nandagy.bean.scorebean.ScoreCETBean;
import com.shenhua.nandagy.frag.score.ScoreBecFragment;
import com.shenhua.nandagy.frag.score.ScoreCAPFragment;
import com.shenhua.nandagy.frag.score.ScoreCetResultFragment;
import com.shenhua.nandagy.frag.score.ScoreCetkFragment;
import com.shenhua.nandagy.frag.score.ScoreComputerFragment;
import com.shenhua.nandagy.frag.score.ScoreFlushFragment;
import com.shenhua.nandagy.frag.score.ScoreForeignFragment;
import com.shenhua.nandagy.frag.score.ScoreMandarinFragment;
import com.shenhua.nandagy.frag.score.ScoreNTCEFragment;
import com.shenhua.nandagy.frag.score.ScorePETSFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 成绩查询结果
 * Created by Shenhua on 9/25/2016.
 */
public class ScoreQueryResultActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_query_result);
        ButterKnife.bind(this);
        setupActionBar("成绩查询结果", true);
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
