package com.shenhua.nandagy.module.more.study.ui.activity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.libs.selectabletextprovider.SelectableTextProvider;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.module.StudyListData;
import com.shenhua.nandagy.module.more.study.presenter.StudyDetailPresenter;
import com.shenhua.nandagy.module.more.study.view.StudyDetailView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 学习专区
 * Created by Shenhua on 9/7/2016.
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_study_detail,
        toolbarId = R.id.common_toolbar,
        toolbarHomeAsUp = true,
        toolbarTitle = R.string.toolbar_title_study_detail,
        toolbarTitleId = R.id.toolbar_title
)
public class StudyAreaDetailActivity extends BaseActivity implements StudyDetailView {

    @BindView(R.id.tv_title)
    TextView mTitleTv;
    @BindView(R.id.tv_time)
    TextView mTimeTv;
    @BindView(R.id.tv_content)
    TextView mContentTv;

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String href = getIntent().getStringExtra("href");
        String title = getIntent().getStringExtra("title");
        int type = getIntent().getIntExtra("type", 0);
        int position = getIntent().getIntExtra("position", 0);
        mTitleTv.setText(title);
        setupToolbarTitle(title);
        StudyDetailPresenter studyDetailPresenter = new StudyDetailPresenter(this, this, type);
        studyDetailPresenter.execute(position, href);
        new SelectableTextProvider.Builder(mTitleTv).build();
        new SelectableTextProvider.Builder(mTimeTv).build();
        new SelectableTextProvider.Builder(mContentTv).build();
    }

    @Override
    public void showDetail(StudyListData data) {
        mTimeTv.setText(data.getTime());
        if (data.getContent() != null)
            mContentTv.setText(Html.fromHtml(data.getContent()));
        else
            toast("数据获取失败");
    }

    @Override
    public void showToast(String msg) {
        toast(msg);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
