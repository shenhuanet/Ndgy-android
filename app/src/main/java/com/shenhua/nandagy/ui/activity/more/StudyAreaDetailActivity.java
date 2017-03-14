package com.shenhua.nandagy.ui.activity.more;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.widget.TextView;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.StudyListData;
import com.shenhua.nandagy.presenter.StudyDetailPresenter;
import com.shenhua.nandagy.view.StudyDetailView;

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
        toolbarTitle = R.string.toolbar_title_study_detail
)
public class StudyAreaDetailActivity extends BaseActivity implements StudyDetailView {

    @BindView(R.id.tv_time)
    TextView mTimeTv;
    @BindView(R.id.tv_title)
    TextView mTitleTv;
    @BindView(R.id.tv_content)
    TextView mContentTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_MODE_OVERLAY);
    }

    @Override
    protected void initView(BaseActivity baseActivity) {
//        supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_MODE_OVERLAY);
        ButterKnife.bind(this);
        setToolbarTitle(R.id.toolbar_title);
        initView();
    }

    private void initView() {
        String href = getIntent().getStringExtra("href");
        String title = getIntent().getStringExtra("title");
        int type = getIntent().getIntExtra("type", 0);
        int position = getIntent().getIntExtra("position", 0);
        mTitleTv.setText(title);

        StudyDetailPresenter studyDetailPresenter = new StudyDetailPresenter(this, this, type);
        studyDetailPresenter.execute(position, href);
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
