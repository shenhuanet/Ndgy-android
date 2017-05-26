package com.shenhua.nandagy.module.me.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.commonlibs.utils.BusProvider;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.callback.NewMessageEventBus;
import com.shenhua.nandagy.utils.FileUtils;
import com.shenhua.nandagy.module.me.ui.fragment.UserFragment;
import com.shenhua.nandagy.utils.SettingUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 通用设置界面
 * Created by Shenhua on 9/4/2016.
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_setting,
        toolbarId = R.id.common_toolbar,
        toolbarHomeAsUp = true,
        toolbarTitle = R.string.toolbar_title_setting,
        toolbarTitleId = R.id.toolbar_title
)
public class SettingActivity extends BaseActivity {

    @BindView(R.id.tv_setting_cache)
    TextView mCacheTv;
    @BindView(R.id.switch_setting_nanwifi)
    Switch mNanWifiSc;
    @BindView(R.id.switch_setting_nanpush)
    Switch mNanPushSc;

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        ButterKnife.bind(this);
        BusProvider.getInstance().post(new NewMessageEventBus(true, UserFragment.EVENT_TYPE_SETTING));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCache();
        updateConfig();
        setListener();
    }

    private void setListener() {
        mNanWifiSc.setOnCheckedChangeListener((buttonView, isChecked) ->
                SettingUtils.getInstance().setConfig(SettingActivity.this, "nanWifi", isChecked));
        mNanPushSc.setOnCheckedChangeListener((buttonView, isChecked) ->
                SettingUtils.getInstance().setConfig(SettingActivity.this, "nanPush", isChecked));
    }

    @OnClick({R.id.layout_setting_feedback, R.id.layout_setting_cache})
    void clicks(View v) {
        switch (v.getId()) {
            case R.id.layout_setting_feedback:
                startActivity(new Intent(this, FeedbackActivity.class));
                break;
            case R.id.layout_setting_cache:
                FileUtils.clearAllCache(this);
                toast("缓存清理成功！");
                updateCache();
                break;
        }
    }

    private void updateCache() {
        String cache = "0KB";
        try {
            cache = FileUtils.getTotalCacheSize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mCacheTv.setText(cache);
    }

    private void updateConfig() {
        SettingUtils.Config config = SettingUtils.getInstance().getConfig(this);
        mNanWifiSc.setChecked(config.isNanWifi());
        mNanPushSc.setChecked(config.isNanPush());
    }
}
