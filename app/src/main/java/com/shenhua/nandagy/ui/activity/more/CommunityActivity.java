package com.shenhua.nandagy.ui.activity.more;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.nandagy.R;

import butterknife.ButterKnife;

/**
 * 社团百态
 * Created by Shenhua on 9/7/2016.
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_more_community,
        toolbarId = R.id.common_toolbar,
        toolbarHomeAsUp = true,
        toolbarTitle = R.string.toolbar_title_more_community
)
public class CommunityActivity extends BaseActivity {

    @Override
    protected void initView(BaseActivity baseActivity) {
        ButterKnife.bind(this);
        setToolbarTitle(R.id.toolbar_title);
    }
}
