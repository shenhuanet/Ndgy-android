package com.shenhua.nandagy.ui.activity.jiaowu;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.scorebean.ExamScore;
import com.shenhua.nandagy.bean.scorebean.GradeScore;
import com.shenhua.nandagy.presenter.ScorePresenter;
import com.shenhua.nandagy.service.Constants;
import com.shenhua.nandagy.view.ScoreView;
import com.shenhua.nandagy.widget.LoadingAlertDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 教务成绩查询界面
 * Created by shenhua on 3/30/2017.
 * Email shenhuanet@126.com
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_score,
        toolbarId = R.id.common_toolbar,
        toolbarHomeAsUp = true,
        toolbarTitle = R.string.toolbar_title_score,
        toolbarTitleId = R.id.toolbar_title
)
public class ScoreActivity extends BaseActivity implements ScoreView {

    private static final String TAG = "ScoreActivity";
    @BindView(R.id.toolbar_pro)
    ProgressBar mProgressBar;
    private String num;
    private String id;
    private ScorePresenter presenter;

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        ButterKnife.bind(this);

        num = getIntent().getStringExtra("name_num");
        id = getIntent().getStringExtra("name_id");

        presenter = new ScorePresenter(this);
        presenter.login(this, Constants.JiaoWuScore.HOST + Constants.JiaoWuScore.URL_LOGIN);

    }

    @Override
    public void reBinding(String error) {

    }

    @Override
    public void getScoreFailed(String msg) {
        // 成绩获取失败
    }

    @Override
    public void onLoginSuccess(String[] numName) {
        // 显示学号 姓名
    }

    @Override
    public void showExamScore(ExamScore examScore) {

    }

    @Override
    public void showGradeScore(List<GradeScore> gradeScores) {

    }

    @Override
    public void showProgress() {
        // do nothing
    }

    @Override
    public void showProgress(String msg) {
        mProgressBar.setVisibility(View.VISIBLE);
        LoadingAlertDialog.showLoadDialog(this, msg, true);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
        LoadingAlertDialog.dissmissLoadDialog();
    }

    @Override
    public void showToast(String msg) {
        toast(msg);
    }

    @Override
    public void exit() {
        this.finish();
    }
}
