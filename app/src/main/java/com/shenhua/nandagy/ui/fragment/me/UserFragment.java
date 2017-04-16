package com.shenhua.nandagy.ui.fragment.me;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shenhua.commonlibs.handler.BaseThreadHandler;
import com.shenhua.commonlibs.handler.CommonUiRunnable;
import com.shenhua.commonlibs.utils.BusProvider;
import com.shenhua.commonlibs.utils.NetworkUtils;
import com.shenhua.commonlibs.widget.CircleImageView;
import com.shenhua.floatingtextview.Animator.TranslateFloatingAnimator;
import com.shenhua.floatingtextview.base.FloatingText;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseDefaultFragment;
import com.shenhua.nandagy.bean.bmobbean.MyUser;
import com.shenhua.nandagy.bean.bmobbean.UserZone;
import com.shenhua.nandagy.callback.NewMessageEventBus;
import com.shenhua.nandagy.databinding.FragUserBinding;
import com.shenhua.nandagy.service.Constants;
import com.shenhua.nandagy.ui.activity.me.AboutActivity;
import com.shenhua.nandagy.ui.activity.me.LoginActivity;
import com.shenhua.nandagy.ui.activity.me.MessageActivity;
import com.shenhua.nandagy.ui.activity.me.PublishDynamicActivity;
import com.shenhua.nandagy.ui.activity.me.SettingActivity;
import com.shenhua.nandagy.ui.activity.me.UserAccountActivity;
import com.shenhua.nandagy.ui.activity.me.UserZoneActivity;
import com.shenhua.nandagy.utils.bmobutils.UserUtils;
import com.shenhua.nandagy.utils.bmobutils.UserZoneUtils;
import com.shenhua.nandagy.widget.LoadingAlertDialog;
import com.squareup.otto.Subscribe;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 用户
 * Created by Shenhua on 8/28/2016.
 */
public class UserFragment extends BaseDefaultFragment {

    private static final String TAG = "UserFragment";
    public static final int EVENT_TYPE_MESSAGE = 1;
    public static final int EVENT_TYPE_SETTING = 2;
    public static final int EVENT_TYPE_ABOUT = 3;
    @BindView(R.id.iv_user_photo)
    CircleImageView mPhotoImageView;
    @BindView(R.id.tv_user_exper)
    TextView mExperTv;
    @BindView(R.id.tag_tv_message)
    TextView mMessageTag;
    @BindView(R.id.tag_tv_setting)
    TextView mSettingTag;
    @BindView(R.id.tag_tv_about)
    TextView mAboutTag;
    private View rootView;
    private FragUserBinding binding;

    @Override
    public void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.frag_user, container, false);
            rootView = binding.getRoot();
            ButterKnife.bind(this, rootView);
            updateViews(NetworkUtils.isConnectedNet(getContext()));
            // 红点提示
            mMessageTag.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.dot_new_red, 0);
            mAboutTag.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.dot_new_red, 0);
        }
        ViewGroup group = (ViewGroup) rootView.getParent();
        if (group != null)
            group.removeView(rootView);
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        BusProvider.getInstance().unregister(this);
    }

    /**
     * 更新显示信息
     *
     * @param upgrade 是否从网络读取数据
     */
    public void updateViews(boolean upgrade) {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        if (user != null) {
            binding.setUser(user);
            String url = user.getUrl_photo();
            Log.d(TAG, "updateViews: 用户头像：" + url);
            Glide.with(getContext()).load(url)
                    .error(user.getSex() ? R.drawable.img_photo_woman : R.drawable.img_photo_man)
                    .centerCrop().into(mPhotoImageView);
            if (UserUtils.getInstance().isCreateZone()) {
                Log.d(TAG, "updateViews: 空间id：" + UserZoneUtils.getInstance().getUserZoneObjId(getContext()));
                if (upgrade) {
                    String zoneObjId = UserUtils.getInstance().getUserzoneObjId(getContext());
                    BmobQuery<UserZone> query = new BmobQuery<>();
                    query.getObject(zoneObjId, new QueryListener<UserZone>() {
                        @Override
                        public void done(UserZone zone, BmobException e) {
                            binding.setUserZone(zone);
                            UserZoneUtils.getInstance().saveUserZone(getContext(), zone);
                        }
                    });
                } else {
                    binding.setUserZone(UserZoneUtils.getInstance().getUserZone(getContext()));
                }
            }
        }
    }

    /**
     * 红点提示
     *
     * @param event event
     */
    @Subscribe
    public void onMessageReadChanged(NewMessageEventBus event) {
        switch (event.getType()) {
            case EVENT_TYPE_MESSAGE:
                if (event.unShow())
                    mMessageTag.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                break;
            case EVENT_TYPE_SETTING:
                if (event.unShow())
                    mSettingTag.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                break;
            case EVENT_TYPE_ABOUT:
                if (event.unShow())
                    mAboutTag.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                break;
        }
    }

    /**
     * 进入个人账户中心
     */
    private void navToUserAccount() {
        Intent intent;
        if (UserUtils.getInstance().isLogin()) {
            intent = new Intent(getContext(), UserAccountActivity.class);
            sceneTransitionTo(intent, Constants.Code.REQUEST_CODE_NAV_TO_USER_ACCOUNT, rootView, R.id.tag_tv_acconut, "title");
        } else {
            intent = new Intent(getContext(), LoginActivity.class);
            sceneTransitionTo(intent, Constants.Code.REQUEST_CODE_NAV_TO_LOGIN, rootView, R.id.tag_tv_acconut, "content");
        }
    }

    /**
     * 进入用户空间
     */
    private void navToUserZone() {
        Intent intent = new Intent(getContext(), UserZoneActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("isMyself", true);
        intent.putExtra("zoneObjectId", UserUtils.getInstance().getUserzoneObjId(getContext()));
        intent.putExtras(bundle);
        sceneTransitionTo(intent, Constants.Code.REQUEST_CODE_NAV_TO_USER_ZONE, rootView, R.id.iv_user_photo, "photos");
    }

    /**
     * 进入用户空间之前
     */
    private void preNavToUserZone() {
        Intent intent;
        if (!UserUtils.getInstance().isLogin()) {
            intent = new Intent(getContext(), LoginActivity.class);
            sceneTransitionTo(intent, Constants.Code.REQUEST_CODE_NAV_TO_LOGIN, rootView, R.id.tag_tv_acconut, "content");
            return;
        }
        if (!UserUtils.getInstance().isCreateZone()) {// 用户没有空间
            crateUserZone();
            return;
        }
        navToUserZone();
    }

    /**
     * 创建用户主页
     */
    private void crateUserZone() {
        // Unable to add window -- token android.os.BinderProxy@3ce320e7 is not valid; is your activity running?
        LoadingAlertDialog.getInstance(getContext()).showLoadDialog("正在创建用户主页", true);
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        UserZone userZone = new UserZone();
        userZone.setUser(myUser);
        userZone.setLevel(0);
        userZone.setDynamic(0);
        userZone.setMi(0);
        userZone.setExper(0);
        userZone.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                LoadingAlertDialog.getInstance(getContext()).dissmissLoadDialog();
                if (e == null) {
                    // 创建一条空间数据后，查询该数据
                    Log.d(TAG, "空间创建完毕: " + objectId);
                    BmobQuery<UserZone> query = new BmobQuery<>();
                    query.getObject(objectId, new QueryListener<UserZone>() {

                        @Override
                        public void done(UserZone object, BmobException e) {
                            if (e == null) {
                                saveUserZoneToUser(object, objectId);
                            } else {
                                toast("用户空间不存在，请重新创建");
                                CrashReport.postCatchedException(e);
                            }
                        }
                    });
                } else {
                    e.printStackTrace();
                    toast("用户空间创建失败，请重试");
                    CrashReport.postCatchedException(e);
                }
            }

            /**
             * 将zoneId写入User
             * @param object userzone
             * @param objectId id
             */
            private void saveUserZoneToUser(UserZone object, final String objectId) {
                MyUser user = BmobUser.getCurrentUser(MyUser.class);
                user.setUserZone(object);
                user.update(user.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            toast("用户空间创建成功");
                            Log.d(TAG, "done: zoneObjectId;" + objectId);
                            UserUtils.getInstance().updateUserInfo(getContext(), "zoneObjId", objectId);
                            navToUserZone();
                        } else {
                            toast("信息更新失败");
                            CrashReport.postCatchedException(e);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 用户可能修改了某些资料，需要更新界面
        if (requestCode == Constants.Code.REQUEST_CODE_NAV_TO_USER_ZONE || requestCode == Constants.Code.REQUEST_CODE_NAV_TO_USER_ACCOUNT) {
            updateViews(true);
        }
        // 用户登录成功
        if (requestCode == Constants.Code.REQUEST_CODE_NAV_TO_LOGIN && resultCode == Constants.Code.RECULT_CODE_LOGIN_SUCCESS) {
            updateViews(false);
            if (UserUtils.getInstance().isCreateZone()) {// 用户有空间，增加经验值
                UserZone zone = BmobUser.getCurrentUser(MyUser.class).getUserZone();
                UserZoneUtils.getInstance().updateZoneStatis(zone.getObjectId(), "exper", 2);
                BaseThreadHandler.getInstance().sendRunnable(new CommonUiRunnable<String>("个人经验+2") {
                    @Override
                    public void doUIThread() {
                        FloatingText floatingText = new FloatingText.FloatingTextBuilder(getActivity())
                                .textColor(Color.parseColor("#1DBFD8"))
                                .textSize(30)
                                .textContent(getT())
                                .offsetX(0)
                                .offsetY(0)
                                .floatingAnimatorEffect(new TranslateFloatingAnimator())
                                .build();
                        floatingText.attach2Window();
                        floatingText.startFloating(mExperTv);
                        updateViews(true);
                    }
                }, 2000, TimeUnit.MILLISECONDS);
            }
        }
        // 用户退出登录成功
        if (requestCode == Constants.Code.REQUEST_CODE_NAV_TO_USER_ACCOUNT && resultCode == UserAccountActivity.LOGOUT_SUCCESS) {
            binding.setUser(null);
            binding.setUserZone(null);
        }
    }

    @OnClick({R.id.rl_user_zone, R.id.rl_account, R.id.rl_publish, R.id.rl_message, R.id.rl_setting, R.id.rl_about})
    void clicks(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.rl_user_zone:
                preNavToUserZone();
                break;
            case R.id.rl_account:
                navToUserAccount();
                break;
            case R.id.rl_publish:
                intent = new Intent(getContext(), PublishDynamicActivity.class);
                sceneTransitionTo(intent, Constants.Code.REQUEST_CODE_NAV_TO_PUBLISH_DYNAMIC, rootView, R.id.tag_tv_publish, "title");
                break;
            case R.id.rl_message:
                intent = new Intent(getContext(), MessageActivity.class);
                sceneTransitionTo(intent, Constants.Code.REQUEST_CODE_NAV_TO_MESSAGE, rootView, R.id.tag_tv_message, "title");
                break;
            case R.id.rl_setting:
                intent = new Intent(getContext(), SettingActivity.class);
                sceneTransitionTo(intent, Constants.Code.REQUEST_CODE_NAV_TO_SETTING, rootView, R.id.tag_tv_setting, "title");
                break;
            case R.id.rl_about:
                intent = new Intent(getContext(), AboutActivity.class);
                sceneTransitionTo(intent, Constants.Code.REQUEST_CODE_NAV_TO_ABOUT, rootView, R.id.tag_tv_about, "title");
                break;
        }
    }

}
