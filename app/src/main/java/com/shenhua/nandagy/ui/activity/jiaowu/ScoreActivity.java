package com.shenhua.nandagy.ui.activity.jiaowu;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.commonlibs.handler.BaseThreadHandler;
import com.shenhua.commonlibs.handler.CommonUiRunnable;
import com.shenhua.nandagy.BR;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.adapter.ExamScoreAdapter;
import com.shenhua.nandagy.adapter.GradeScoreAdapter;
import com.shenhua.nandagy.base.BaseRecyclerBindingAdapter;
import com.shenhua.nandagy.bean.scorebean.ExamScore;
import com.shenhua.nandagy.bean.scorebean.GradeScore;
import com.shenhua.nandagy.databinding.ActivityScoreBinding;
import com.shenhua.nandagy.presenter.ScorePresenter;
import com.shenhua.nandagy.service.Constants;
import com.shenhua.nandagy.view.ScoreView;
import com.shenhua.nandagy.widget.LoadingAlertDialog;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 教务成绩查询界面
 * Created by shenhua on 3/30/2017.
 * Email shenhuanet@126.com
 */
@ActivityFragmentInject(
        toolbarId = R.id.common_toolbar,
        toolbarHomeAsUp = true,
        toolbarTitle = R.string.toolbar_title_score,
        toolbarTitleId = R.id.toolbar_title
)
public class ScoreActivity extends BaseActivity implements ScoreView {

    @BindView(R.id.toolbar_pro)
    ProgressBar mProgressBar;
    private ScorePresenter mPresenter;
    private ActivityScoreBinding mBinding;
    private ExamScoreAdapter mExamScoreAdapter;
    private GradeScoreAdapter mGradeScoreAdapter;
    private int mCurrentTab = 0;
    private ExamScore mExamScore;
    private List<GradeScore> mGradeScores;

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_score);
        ButterKnife.bind(this);
        initToolbar();
        String num = getIntent().getStringExtra("name_num");
        String id = getIntent().getStringExtra("name_id");
        if (TextUtils.isEmpty(num) || TextUtils.isEmpty(id)) {
            reBinding("请登录教务系统");
            return;
        }
        mBinding.tablayout.addTab(mBinding.tablayout.newTab().setText("考试成绩查询"));
        mBinding.tablayout.addTab(mBinding.tablayout.newTab().setText("等级考试查询"));
        mBinding.tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (mCurrentTab == tab.getPosition()) {
                    return;
                }
                if (tab.getPosition() == 0) {
                    if (mExamScoreAdapter == null) {
                        getExamScore();
                    } else {
                        showExamScore(mExamScore);
                    }
                    mCurrentTab = 0;
                }
                if (tab.getPosition() == 1) {
                    if (mGradeScoreAdapter == null) {
                        getGradeScore();
                    } else {
                        showGradeScore(mGradeScores);
                    }
                    mCurrentTab = 1;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        String url = Constants.JiaoWuScore.HOST + Constants.JiaoWuScore.URL_LOGIN;
        mPresenter = new ScorePresenter(this);
        mPresenter.login(this, url, num, id);
    }

    public void getExamScore() {
        mPresenter.getExamScore(this);
    }

    public void getGradeScore() {
        mPresenter.getGradeScore(this);
    }

    @Override
    public void reBinding(String error) {
        showToast(error);
        Intent intent = new Intent(this, BindingActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void getScoreFailed(String msg) {
        showToast(msg);
        mBinding.emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoginSuccess(String[] numName) {
        showToast(String.format(getResources().getString(R.string.score_toast_welcome), numName[1]));
        setupToolbarTitle(String.format(getResources().getString(R.string.score_toolbar_title), numName[0], numName[1]));
        BaseThreadHandler.getInstance().sendRunnable(new CommonUiRunnable<Integer>(2) {
            @Override
            public void doUIThread() {
                getExamScore();
            }
        }, 2, TimeUnit.SECONDS);
    }

    @Override
    public void showExamScore(ExamScore examScore) {
        this.mExamScore = examScore;
        ExamScore.Overview overview = examScore.getOverview();
        List<ExamScore.ExamScoreList> list = examScore.getExamScoreLists();
        mExamScoreAdapter = new ExamScoreAdapter(list);
        BaseRecyclerBindingAdapter.BaseRecyclerModel mExamScoreHeader = mExamScoreAdapter.new BaseRecyclerModel<ExamScore.Overview>
                (R.layout.item_exam_score_list_head, BR.overView, overview);
        mExamScoreAdapter.addHeadView(mExamScoreHeader);
        mBinding.recyclerView.setAdapter(mExamScoreAdapter);
    }

    @Override
    public void showGradeScore(List<GradeScore> gradeScores) {
        this.mGradeScores = gradeScores;
        mGradeScoreAdapter = new GradeScoreAdapter(gradeScores);
        mBinding.recyclerView.setAdapter(mGradeScoreAdapter);
        mGradeScoreAdapter.setItemLongClickListener((view, position) -> {
            ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            String text = mGradeScores.get(position).getNum();
            manager.setPrimaryClip(ClipData.newPlainText("text", text));
            toast("文本已复制");
        });
    }

    /**
     * do nothing
     * {@link ScoreActivity#showProgress(String msg)}
     */
    @Override
    public void showProgress() {

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
