package com.shenhua.nandagy.ui.fragment.more;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shenhua.floatingtextview.Animator.TranslateFloatingAnimator;
import com.shenhua.floatingtextview.base.FloatingText;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.ui.activity.me.AboutActivity;
import com.shenhua.nandagy.ui.activity.me.LoginActivity;
import com.shenhua.nandagy.ui.activity.me.MessageActivity;
import com.shenhua.nandagy.ui.activity.me.PublishDynamicActivity;
import com.shenhua.nandagy.ui.activity.me.SettingActivity;
import com.shenhua.nandagy.ui.activity.me.UserAccountActivity;
import com.shenhua.nandagy.ui.activity.me.UserZoneActivity;
import com.shenhua.nandagy.bean.bmobbean.MyUser;
import com.shenhua.nandagy.bean.bmobbean.UserZone;
import com.shenhua.nandagy.callback.NewMessageEventBus;
import com.shenhua.nandagy.callback.ProgressEventBus;
import com.shenhua.nandagy.utils.bmobutils.UserUtils;
import com.shenhua.nandagy.widget.LoadingAlertDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import de.greenrobot.event.EventBus;

/**
 * 用户
 * Created by Shenhua on 8/28/2016.
 */
public class UserFragment extends Fragment {

    @Bind(R.id.iv_user_photo)
    ImageView mUserPhotoIv;
    @Bind(R.id.tv_user_name)
    TextView mUserNameTv;
    @Bind(R.id.tv_user_level)
    TextView mLevelTv;
    @Bind(R.id.tv_user_dynamic)
    TextView mDynamicTv;
    @Bind(R.id.tv_user_mi)
    TextView mMiTv;
    @Bind(R.id.tv_user_exper)
    TextView mExperTv;
    @Bind(R.id.rl_account)
    RelativeLayout mAccountLayout;
    @Bind(R.id.tag_tv_message)
    TextView mMessageTag;
    @Bind(R.id.tag_tv_setting)
    TextView mSettingTag;
    @Bind(R.id.tag_tv_about)
    TextView mAboutTag;
    private View view;
    public static final int EVENT_TYPE_MESSAGE = 1;
    public static final int EVENT_TYPE_SETTING = 2;
    public static final int EVENT_TYPE_ABOUT = 3;
    public static final int EVENT_TYPE_EXPER_ADD_2 = 4;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.frag_user, container, false);
            ButterKnife.bind(this, view);
        }
        ViewGroup group = (ViewGroup) view.getParent();
        if (group != null)
            group.removeView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 红点提示
        mMessageTag.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.dot_new_red, 0);
        mAboutTag.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.dot_new_red, 0);
        updateUserView();
    }

    private void updateUserView() {
        // 用户展示区
        EventBus.getDefault().post(new ProgressEventBus(true));
        UserUtils instance = UserUtils.getInstance();
        if (instance.isLogin(getActivity())) {// 已登录
            MyUser user = instance.getUserInfo(getActivity());
            System.out.println("shenhua sout:QQ photo:" + user.getUrl_photo());
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
                EventBus.getDefault().post(new ProgressEventBus(false));
                return;
            }
            zone.getObject(user.getUserZoneObjID(), new QueryListener<UserZone>() {
                @Override
                public void done(UserZone zone, BmobException e) {
                    if (e == null) {
                        mLevelTv.setText(Integer.toString(zone.getLevel()));
                        mDynamicTv.setText(Integer.toString(zone.getDynamic()));
                        mMiTv.setText(Integer.toString(zone.getMi()));
                        mExperTv.setText(Integer.toString(zone.getExper()));
                    } else {
                        toast("个人主页信息获取失败：" + e.getMessage());
                        mLevelTv.setText("0");
                        mDynamicTv.setText("0");
                        mMiTv.setText("0");
                        mExperTv.setText("0");
                    }
                }
            });
            EventBus.getDefault().post(new ProgressEventBus(false));
        } else {
            Glide.with(this).load("").centerCrop().placeholder(R.drawable.img_photo_man)
                    .error(R.drawable.img_photo_man).into(mUserPhotoIv);
            mUserNameTv.setText("未登录");
            mLevelTv.setText("-");
            mDynamicTv.setText("-");
            mMiTv.setText("-");
            mExperTv.setText("-");
            EventBus.getDefault().post(new ProgressEventBus(false));
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(NewMessageEventBus event) {
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
            case EVENT_TYPE_EXPER_ADD_2:
                mExperTv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FloatingText floatingText = new FloatingText.FloatingTextBuilder(getActivity())
                                .textColor(Color.parseColor("#1DBFD8"))
                                .textSize(30)
                                .textContent("个人经验+2")
                                .offsetX(0)
                                .offsetY(0)
                                .floatingAnimatorEffect(new TranslateFloatingAnimator())
                                .build();
                        floatingText.attach2Window();
                        floatingText.startFloating(mExperTv);
                    }
                }, 2000);
                break;
        }
    }

    @OnClick({R.id.rl_user_zone, R.id.rl_account, R.id.rl_publish, R.id.rl_message, R.id.rl_setting, R.id.rl_about})
    void clicks(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.rl_user_zone:
                navtoUserZone();
                break;
            case R.id.rl_account:
                navtoUserAccount();
                break;
            case R.id.rl_publish:
                intent = new Intent(getActivity(), PublishDynamicActivity.class);
                sceneTransitionTo(intent, 4, R.id.tag_tv_publish, "title");
                break;
            case R.id.rl_message:
                intent = new Intent(getActivity(), MessageActivity.class);
                sceneTransitionTo(intent, 5, R.id.tag_tv_message, "title");
                break;
            case R.id.rl_setting:
                intent = new Intent(getActivity(), SettingActivity.class);
                sceneTransitionTo(intent, 6, R.id.tag_tv_setting, "title");
                break;
            case R.id.rl_about:
                intent = new Intent(getActivity(), AboutActivity.class);
                sceneTransitionTo(intent, 7, R.id.tag_tv_about, "title");
                break;
        }
    }

    /**
     * 进入用户主页
     */
    private void navtoUserZone() {
        Intent intent;
        if (UserUtils.getInstance().isLogin(getActivity())) {
            MyUser user = UserUtils.getInstance().getUserInfo(getActivity());
            if (TextUtils.isEmpty(user.getUserZoneObjID())) {
                crateUserZone();
            } else {
                intent = new Intent(getActivity(), UserZoneActivity.class);
                intent.putExtra("isMySelf", true);
                intent.putExtra("zoneObjectId", user.getUserZoneObjID());
                intent.putExtra("userObjectId", user.getUserId());
                intent.putExtra("photo", user.getUrl_photo());
                intent.putExtra("sex", user.getSex());
                sceneTransitionTo(intent, 1, R.id.iv_user_photo, "photos");
            }
        } else {
            intent = new Intent(getActivity(), LoginActivity.class);
            sceneTransitionTo(intent, 3, R.id.tag_tv_acconut, "content");
        }
    }

    /**
     * 进入个人账户中心
     */
    private void navtoUserAccount() {
        Intent intent;
        if (UserUtils.getInstance().isLogin(getContext())) {
            intent = new Intent(getActivity(), UserAccountActivity.class);
            sceneTransitionTo(intent, 2, R.id.tag_tv_acconut, "title");
        } else {
            intent = new Intent(getActivity(), LoginActivity.class);
            sceneTransitionTo(intent, 3, R.id.tag_tv_acconut, "content");
        }
    }

    private void crateUserZone() {
        LoadingAlertDialog.showLoadDialog(getActivity(), "正在创建用户主页",true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UserZone userZone = new UserZone();
                MyUser user = UserUtils.getInstance().getUserInfo(getActivity());
                userZone.setLevel(0);
                userZone.setDynamic(0);
                userZone.setMi(0);
                userZone.setExper(0);
                userZone.setPhotoUrl(user.getUrl_photo());
                System.out.println("shenhua sout:" + user.getUrl_photo());
                userZone.setSex(user.getSex());
                userZone.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if (e == null) {
                            LoadingAlertDialog.dissmissLoadDialog();
                            System.out.println("shenhua sout:creatUserZone   " + objectId);
                            updateUserInfo(objectId);
                            Intent intent = new Intent(getActivity(), UserZoneActivity.class);
                            sceneTransitionTo(intent, 1, R.id.iv_user_photo, "photos");
                        } else {
                            LoadingAlertDialog.dissmissLoadDialog();
                            toast("用户空间创建失败：" + e.getMessage());
                        }
                    }
                });
            }
        }, 1000);
    }

    private void updateUserInfo(String qobjectId) {
        MyUser user = new MyUser();
        user.setUserZoneObjID(qobjectId);
        user.update(UserUtils.getInstance().getUserInfo(getContext()).getUserId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {

            }
        });
        UserUtils.getInstance().updateUserInfo(getContext(), "userzoneobjid", qobjectId);
    }

    private void sceneTransitionTo(Intent intent, int resquestCode, int viewId, String sharedElementName) {
        if (Build.VERSION.SDK_INT > 21) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                    view.findViewById(viewId), sharedElementName);
            startActivityForResult(intent, resquestCode, options.toBundle());
        } else {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(view,
                    view.getWidth() / 2, view.getHeight() / 2, 0, 0);
            startActivityForResult(intent, resquestCode, options.toBundle());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("shenhua sout: UserFragment  requestCode ---->" + requestCode);
        System.out.println("shenhua sout: UserFragment  resultCode ---->" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && resultCode == LoginActivity.LOGIN_SUCCESS) {
            updateUserView();
        }
        if (requestCode == 1 || requestCode == 2) {
            updateUserView();
        }
    }

    public void toast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
