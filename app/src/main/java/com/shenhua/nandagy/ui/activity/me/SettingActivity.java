package com.shenhua.nandagy.ui.activity.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseActivity;
import com.shenhua.nandagy.callback.NewMessageEventBus;
import com.shenhua.nandagy.ui.fragment.more.UserFragment;
import com.shenhua.nandagy.manager.DataCleanManager;
import com.shenhua.nandagy.utils.SettingUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 通用设置界面
 * Created by Shenhua on 9/4/2016.
 */
public class SettingActivity extends BaseActivity {

    @Bind(R.id.tv_setting_cache)
    TextView mCacheTv;
    @Bind(R.id.switch_setting_nanwifi)
    Switch mNanWifiSc;
    @Bind(R.id.switch_setting_nanpush)
    Switch mNanPushSc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setupActionBar("通用设置", true);

        EventBus.getDefault().post(new NewMessageEventBus(true, UserFragment.EVENT_TYPE_SETTING));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCache();
        updateConfig();
        setListener();
    }

    private void setListener() {
        mNanWifiSc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingUtils.getInstance().setConfig(SettingActivity.this, "nanWifi", isChecked);
            }
        });
        mNanPushSc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingUtils.getInstance().setConfig(SettingActivity.this, "nanPush", isChecked);
            }
        });
    }

    @OnClick({R.id.layout_setting_feedback, R.id.layout_setting_cache})
    void clicks(View v) {
        switch (v.getId()) {
            case R.id.layout_setting_feedback:
                startActivity(new Intent(this, FeedbackActivity.class));
                break;
            case R.id.layout_setting_cache:
                DataCleanManager.clearAllCache(this);
                toast("缓存清理成功！");
                updateCache();
                break;
        }
    }

    private void updateCache() {
        String cache = "0KB";
        try {
            cache = DataCleanManager.getTotalCacheSize(this);
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
