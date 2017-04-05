package com.shenhua.nandagy.ui.fragment.me;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseFragment;
import com.shenhua.commonlibs.handler.BaseThreadHandler;
import com.shenhua.commonlibs.handler.CommonRunnable;
import com.shenhua.commonlibs.handler.CommonUiRunnable;
import com.shenhua.commonlibs.utils.BusBooleanEvent;
import com.shenhua.commonlibs.utils.BusProvider;
import com.shenhua.floatingtextview.Animator.TranslateFloatingAnimator;
import com.shenhua.floatingtextview.base.FloatingText;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.bmobbean.MyUser;
import com.shenhua.nandagy.bean.bmobbean.UserZone;
import com.shenhua.nandagy.callback.NewMessageEventBus;
import com.shenhua.nandagy.service.Constants;
import com.shenhua.nandagy.service.ExceptionMessage;
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

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 用户
 * Created by Shenhua on 8/28/2016.
 */
@ActivityFragmentInject(
        contentViewId = R.layout.frag_user,
        useBusEvent = true
)
public class UserFragment extends BaseFragment {

    private static final String TAG = "UserFragment";
    @BindView(R.id.iv_user_photo)
    ImageView mUserPhotoIv;
    @BindView(R.id.tv_user_name)
    TextView mUserNameTv;
    @BindView(R.id.tv_user_level)
    TextView mLevelTv;
    @BindView(R.id.tv_user_dynamic)
    TextView mDynamicTv;
    @BindView(R.id.tv_user_mi)
    TextView mMiTv;
    @BindView(R.id.tv_user_exper)
    TextView mExperTv;
    @BindView(R.id.rl_account)
    RelativeLayout mAccountLayout;
    @BindView(R.id.tag_tv_message)
    TextView mMessageTag;
    @BindView(R.id.tag_tv_setting)
    TextView mSettingTag;
    @BindView(R.id.tag_tv_about)
    TextView mAboutTag;
    public static final int EVENT_TYPE_MESSAGE = 1;
    public static final int EVENT_TYPE_SETTING = 2;
    public static final int EVENT_TYPE_ABOUT = 3;

    @Override
    public void onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, View rootView) {
        ButterKnife.bind(this, rootView);
        // 红点提示
        mMessageTag.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.dot_new_red, 0);
        mAboutTag.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.dot_new_red, 0);

        updateUserView();
    }

    private void updateUserView() {
        BusProvider.getInstance().post(new BusBooleanEvent(true));
        UserUtils instance = UserUtils.getInstance();
        if (instance.isLogin(getContext())) {// 已登录
            MyUser user = instance.getUserInfo(getContext());
            String imgUrl = user.getUrl_photo();
            boolean sex = user.getSex();
            setPhotoView(imgUrl, sex);
            String n = user.getNick();
            mUserNameTv.setText(TextUtils.isEmpty(n) ? "未设置昵称" : n);
            BmobQuery<UserZone> zone = new BmobQuery<>();
            if (TextUtils.isEmpty(user.getUserZoneObjID())) {
                mLevelTv.setText("-");
                mDynamicTv.setText("-");
                mMiTv.setText("-");
                mExperTv.setText("-");
                BusProvider.getInstance().post(new BusBooleanEvent(false));
                return;
            }
            zone.getObject(user.getUserZoneObjID(), new QueryListener<UserZone>() {
                @Override
                public void done(UserZone zone, BmobException e) {
                    if (e == null) {
                        mLevelTv.setText(String.format(getString(R.string.common_single_num_format), zone.getLevel()));
                        mDynamicTv.setText(String.format(getString(R.string.common_single_num_format), zone.getDynamic()));
                        mMiTv.setText(String.format(getString(R.string.common_single_num_format), zone.getMi()));
                        mExperTv.setText(String.format(getString(R.string.common_single_num_format), zone.getExper()));
                    } else {
                        toast("个人主页信息获取失败：" + e.getMessage());
                        mLevelTv.setText("0");
                        mDynamicTv.setText("0");
                        mMiTv.setText("0");
                        mExperTv.setText("0");
                    }
                }
            });
            BusProvider.getInstance().post(new BusBooleanEvent(false));
        } else {
            Glide.with(this).load("").centerCrop().placeholder(R.drawable.img_photo_man)
                    .error(R.drawable.img_photo_man).into(mUserPhotoIv);
            mUserNameTv.setText("未登录");
            mLevelTv.setText("-");
            mDynamicTv.setText("-");
            mMiTv.setText("-");
            mExperTv.setText("-");
            BusProvider.getInstance().post(new BusBooleanEvent(false));
        }
    }

    private void setPhotoView(String imgUrl, boolean sex) {
        if (TextUtils.isEmpty(imgUrl)) {
            if (sex) {
                Glide.with(this).load(R.drawable.img_photo_woman).centerCrop().into(mUserPhotoIv);
            } else {
                Glide.with(this).load(R.drawable.img_photo_man).centerCrop().into(mUserPhotoIv);
            }
        } else {
            Glide.with(this).load(imgUrl).centerCrop().into(mUserPhotoIv);
        }
    }

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
        if (UserUtils.getInstance().isLogin(getContext())) {
            intent = new Intent(getContext(), UserAccountActivity.class);
            sceneTransitionTo(intent, Constants.Code.REQUEST_CODE_NAV_TO_USER_ACCOUNT, rootView, R.id.tag_tv_acconut, "title");
        } else {
            intent = new Intent(getContext(), LoginActivity.class);
            sceneTransitionTo(intent, Constants.Code.REQUEST_CODE_NAV_TO_LOGIN, rootView, R.id.tag_tv_acconut, "content");
        }
    }

    /**
     * 进入用户主页
     */
    private void navToUserZone() {
        Intent intent;
        if (UserUtils.getInstance().isLogin(getContext())) {
            MyUser user = UserUtils.getInstance().getUserInfo(getContext());
            if (TextUtils.isEmpty(user.getUserZoneObjID())) {
                crateUserZone();
            } else {
                intent = new Intent(getContext(), UserZoneActivity.class);
                intent.putExtra("isMySelf", true);
                intent.putExtra("zoneObjectId", user.getUserZoneObjID());
                intent.putExtra("userObjectId", user.getUserId());
                intent.putExtra("photo", user.getUrl_photo());
                intent.putExtra("sex", user.getSex());
                sceneTransitionTo(intent, Constants.Code.REQUEST_CODE_NAV_TO_USER_ZONE, rootView, R.id.iv_user_photo, "photos");
            }
        } else {
            intent = new Intent(getContext(), LoginActivity.class);
            sceneTransitionTo(intent, Constants.Code.REQUEST_CODE_NAV_TO_LOGIN, rootView, R.id.tag_tv_acconut, "content");
        }
    }

    /**
     * 创建用户主页
     */
    private void crateUserZone() {
        LoadingAlertDialog.showLoadDialog(getContext(), "正在创建用户主页", true);
        BaseThreadHandler.getInstance().sendRunnable(new CommonRunnable<String>() {
            @Override
            public String doChildThread() {
                UserZone userZone = new UserZone();
                final MyUser user = UserUtils.getInstance().getUserInfo(getContext());
                userZone.setLevel(0);
                userZone.setDynamic(0);
                userZone.setMi(0);
                userZone.setExper(0);
                userZone.setPhotoUrl(user.getUrl_photo());
                userZone.setSex(user.getSex());
                userZone.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        LoadingAlertDialog.dissmissLoadDialog();
                        if (e == null) {
                            doUiThread(objectId);
                        } else {
                            doUiThread(ExceptionMessage.MSG_ERROR);
                        }
                    }
                });
                return null;
            }

            @Override
            public void doUiThread(String objectId) {
                if (objectId.equals(ExceptionMessage.MSG_ERROR)) {
                    toast("用户空间创建失败，请重试");
                } else {
                    MyUser user = new MyUser();
                    user.setUserZoneObjID(objectId);
                    user.update(UserUtils.getInstance().getUserInfo(getContext()).getUserId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                        }
                    });
                    UserUtils.getInstance().updateUserInfo(getContext(), "userzoneobjid", objectId);
                    toast("用户空间创建成功");
                    Intent intent = new Intent(getContext(), UserZoneActivity.class);
                    intent.putExtra("isMySelf", true);
                    intent.putExtra("zoneObjectId", objectId);
                    intent.putExtra("userObjectId", user.getUserId());
                    intent.putExtra("photo", user.getUrl_photo());
                    intent.putExtra("sex", user.getSex());
                    sceneTransitionTo(intent, 1, rootView, R.id.iv_user_photo, "photos");
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 用户修改了某些资料，需要更新界面
        if (requestCode == Constants.Code.REQUEST_CODE_NAV_TO_USER_ZONE || requestCode == Constants.Code.REQUEST_CODE_NAV_TO_USER_ACCOUNT) {
            updateUserView();
        }
        // 用户登录成功
        if (requestCode == Constants.Code.REQUEST_CODE_NAV_TO_LOGIN && resultCode == Constants.Code.RECULT_CODE_LOGIN_SUCCESS) {
            updateUserView();
            MyUser myUser = UserUtils.getInstance().getUser(getContext());
            if (!TextUtils.isEmpty(myUser.getUserZoneObjID())) { // 用户有空间
                UserZoneUtils.getInstance().updateZoneStatis(myUser.getUserZoneObjID(), "exper", 2);
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
                    }
                }, 2000, TimeUnit.MILLISECONDS);
            }

        }
    }

    @OnClick({R.id.rl_user_zone, R.id.rl_account, R.id.rl_publish, R.id.rl_message, R.id.rl_setting, R.id.rl_about})
    void clicks(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.rl_user_zone:
                navToUserZone();
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
