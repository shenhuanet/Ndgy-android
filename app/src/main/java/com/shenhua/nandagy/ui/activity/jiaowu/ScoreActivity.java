package com.shenhua.nandagy.ui.activity.jiaowu;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.bmobbean.MyUser;
import com.shenhua.nandagy.utils.bmobutils.UserUtils;
import com.shenhua.nandagy.widget.LoadingAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

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
public class ScoreActivity extends BaseActivity {

    private static final String TAG = "ScoreActivity";
    @BindView(R.id.toolbar_pro)
    ProgressBar mProgressBar;
    private String num;
    private String id;

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        ButterKnife.bind(this);

        num = getIntent().getStringExtra("name_num");
        id = getIntent().getStringExtra("name_id");

        doLoginJiaowu();

    }

    private void doLoginJiaowu() {
        mProgressBar.setVisibility(View.VISIBLE);
        LoadingAlertDialog.showLoadDialog(this, "正在登录教务系统", true);




    }

    private void onLoginSuccess() {
        MyUser user = UserUtils.getInstance().getUser(this);
        String objectId = user.getUserId();
        user.setName_num(num);
        user.setName_id(id);
        user.setInfo("update");
        user.update(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    UserUtils.getInstance().updateUserInfo(ScoreActivity.this, "name_num", num);
                    UserUtils.getInstance().updateUserInfo(ScoreActivity.this, "name_id", id);
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
