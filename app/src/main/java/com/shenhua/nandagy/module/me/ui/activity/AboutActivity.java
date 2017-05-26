package com.shenhua.nandagy.module.me.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.GridView;
import android.widget.TextView;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.commonlibs.base.BaseImageTextItem;
import com.shenhua.commonlibs.base.BaseListAdapter;
import com.shenhua.commonlibs.utils.BusProvider;
import com.shenhua.commonlibs.widget.BaseShareView;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.callback.NewMessageEventBus;
import com.shenhua.nandagy.module.me.ui.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateStatus;

/**
 * 关于本软件界面
 * Created by Shenhua on 9/4/2016.
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_about,
        toolbarId = R.id.common_toolbar,
        toolbarHomeAsUp = true,
        toolbarTitle = R.string.toolbar_title_about,
        toolbarTitleId = R.id.toolbar_title
)
public class AboutActivity extends BaseActivity {

    @BindView(R.id.tv_about_version)
    TextView mVersionTv;
    @BindView(R.id.tv_about_update)
    TextView mUpdateTv;
    @BindView(R.id.tv_about_share)
    TextView mShareTv;
    @BindView(R.id.base_share_view)
    BaseShareView mShareView;

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        ButterKnife.bind(this);
        BusProvider.getInstance().post(new NewMessageEventBus(true, UserFragment.EVENT_TYPE_ABOUT));
    }

    @Override
    protected void onResume() {
        super.onResume();
        String version = "1.0";
        try {
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        mVersionTv.setText("当前版本 " + version);
        initShareView();
    }

    private void initShareView() {
        mShareView.setInterpolator(new BounceInterpolator());
        View content = mShareView.getContentView();
        GridView gridView = (GridView) content.findViewById(R.id.gridView);
        List<BaseImageTextItem> items = new ArrayList<>();
        String[] titles = getResources().getStringArray(R.array.share_items);
        int[] resIds = {R.drawable.ic_share_qq, R.drawable.ic_share_wechat, R.drawable.ic_share_moments,
                R.drawable.ic_share_weibo, R.drawable.ic_share_facebook, R.drawable.ic_share_more};
        for (int i = 0; i < titles.length; i++) {
            BaseImageTextItem item = new BaseImageTextItem(resIds[i], titles[i]);
            items.add(item);
        }

        BaseListAdapter adapter = new BaseListAdapter<BaseImageTextItem>(this, items) {
            @Override
            public void onBindItemView(BaseViewHolder baseViewHolder, BaseImageTextItem item, int position) {
                baseViewHolder.setImageResource(R.id.iv_img, item.getDrawable());
                baseViewHolder.setText(R.id.tv_title, item.getTitle());
                baseViewHolder.setOnListItemClickListener((view -> {
                    onShareItem(position);
                }));
            }

            @Override
            public int getItemViewId() {
                return R.layout.item_common_imgtv;
            }
        };
        gridView.setAdapter(adapter);
    }

    private void onShareItem(int position) {
        mShareView.hide();
        switch (position) {
            case 0:
                shareAppAsQQ();
                break;
            case 1:
                shareAppAsWechat();
                break;
            case 2:
                shareAppAsMonment();
                break;
            case 3:
                shareAppAsWeibo();
                break;
            case 4:
                shareAppAsFb();
                break;
            case 5:
                shareAppAsMore();
                break;
        }
    }

    @OnClick({R.id.tv_about_update, R.id.tv_about_share})
    void clicks(View v) {
        switch (v.getId()) {
            case R.id.tv_about_update:
                BmobUpdateAgent.forceUpdate(this);
                BmobUpdateAgent.setUpdateListener((updateStatus, updateInfo) -> {
                    if (updateStatus == UpdateStatus.Yes) {//版本有更新
                        toast("检测到新版本");
                    } else if (updateStatus == UpdateStatus.No) {
                        toast("当前已经是最新版本");
                    } else if (updateStatus == UpdateStatus.TimeOut) {
                        toast("暂时没有网络或者网路堵塞！");
                    }
                });
                break;
            case R.id.tv_about_share:
                if (!mShareView.getIsShowing())
                    mShareView.show();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (mShareView.getIsShowing()) {
                mShareView.hide();
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return true;
    }

    private void shareAppAsFb() {
        toast("该功能暂不可用，请使用更多分享");
    }

    private void shareAppAsWeibo() {
        toast("该功能暂不可用，请使用更多分享");
    }

    private void shareAppAsMonment() {
        toast("该功能暂不可用，请使用更多分享");
    }

    private void shareAppAsWechat() {
        toast("该功能暂不可用，请使用更多分享");
    }

    private void shareAppAsQQ() {
        toast("该功能暂不可用，请使用更多分享");
    }

    private void shareAppAsMore() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "分享：南昌大学共青学院app");
        intent.putExtra(Intent.EXTRA_TEXT,
                "南昌大学共青学院校园app终于上线了，点击这个链接：\nhttp://fir.im/ndgy\n进入app官方网站下载吧！");
        Intent chooserIntent = Intent.createChooser(intent, "请选择一个要发送的应用：");
        if (chooserIntent == null) {
            return;
        }
        startActivity(chooserIntent);
    }
}
