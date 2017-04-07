package com.shenhua.nandagy;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.nandagy.bean.scorebean.GradeScore;
import com.shenhua.nandagy.databinding.ActivityTestBinding;

/**
 * Created by shenhua on 2/13/2017.
 * Email shenhuanet@126.com
 */
@ActivityFragmentInject(
        toolbarId = R.id.common_toolbar,
        toolbarHomeAsUp = true,
        toolbarTitle = R.string.toolbar_title_score,
        toolbarTitleId = R.id.toolbar_title
)
public class TestActivity extends BaseActivity {

    ActivityTestBinding binding;
    private static final String TAG = "TestActivity";

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test);

        GradeScore gradeScore = new GradeScore();
        gradeScore.setRead(null);
        binding.setGrade(gradeScore);

    }
}
