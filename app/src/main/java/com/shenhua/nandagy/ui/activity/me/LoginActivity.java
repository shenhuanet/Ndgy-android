package com.shenhua.nandagy.ui.activity.me;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseActivity;
import com.shenhua.nandagy.bean.bmobbean.MyUser;
import com.shenhua.nandagy.callback.NewMessageEventBus;
import com.shenhua.nandagy.ui.fragment.more.UserFragment;
import com.shenhua.nandagy.utils.Rotate3dAnimation;
import com.shenhua.nandagy.utils.ShareUtils;
import com.shenhua.nandagy.utils.bmobutils.UserUtils;
import com.shenhua.nandagy.utils.bmobutils.UserZoneUtils;
import com.shenhua.nandagy.widget.ClearEditText;
import com.shenhua.nandagy.widget.LoadingAlertDialog;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import de.greenrobot.event.EventBus;

/**
 * 注册登录界面
 * Created by shenhua on 9/9/2016.
 */
public class LoginActivity extends BaseActivity {

    public static final int LOGIN_SUCCESS = 100;
    @Bind(R.id.layout_sign_content)
    RelativeLayout mSignContent;
    @Bind(R.id.view_signin)
    View mSigninView;
    @Bind(R.id.view_signup)
    View mSignupView;
    private int centerX;
    private int centerY;
    private int depthZ = 400;
    private int duration = 200;
    private Rotate3dAnimation openAnimation;
    private Rotate3dAnimation closeAnimation;
    private boolean isSignup = false;// 一开始显示登录页

    @Bind(R.id.signup_et_username)
    ClearEditText mUsernameEt;
    @Bind(R.id.signup_et_password)
    ClearEditText mPasswordEt;
    @Bind(R.id.radiogroup)
    RadioGroup mSexRadioGroup;
    private boolean gender = false;//默认性别 男

    @Bind(R.id.signin_et_username)
    ClearEditText mUsernameSigninEt;
    @Bind(R.id.signin_et_password)
    ClearEditText mPasswordSigninEt;

    private Tencent mTencent;
    private String openID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTencent = Tencent.createInstance(ShareUtils.QQ_APPID, this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setupActionBar("登录注册", true);
        initView();
    }

    private void initView() {
        mSexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonId = group.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(radioButtonId);
                assert rb != null;
                switch (rb.getText().toString()) {
                    case "男":
                        gender = false;
                        break;
                    case "女":
                        gender = true;
                        break;
                }
            }
        });
    }

    @OnClick({R.id.sign_switch_to_signup, R.id.sign_switch_to_signin, R.id.sign_layout_root, R.id.sign_btn_signup, R.id.sign_btn_signin,
            R.id.signin_btn_qq, R.id.signin_btn_mm, R.id.signin_btn_wb, R.id.signin_btn_fb})
    void onSwitch(View v) {
        switch (v.getId()) {
            case R.id.sign_switch_to_signup:
                onSwitchView();
                break;
            case R.id.sign_switch_to_signin:
                onSwitchView();
                break;
            case R.id.sign_layout_root:
                hideKbTwo();
                break;
            case R.id.sign_btn_signup:
                hideKbTwo();
                String username = mUsernameEt.getText().toString();
                String password = mPasswordEt.getText().toString();
                if (!isReady(username, password))
                    return;
                doSignup(username, password);
                break;
            case R.id.sign_btn_signin:
                hideKbTwo();
                String username1 = mUsernameSigninEt.getText().toString();
                String password1 = mPasswordSigninEt.getText().toString();
                if (!isReady(username1, password1))
                    return;
                doSignin(username1, password1);
                break;
            case R.id.signin_btn_qq:
                mTencent.login(this, "all", loginListener);
                break;
            case R.id.signin_btn_mm:
                break;
            case R.id.signin_btn_wb:
                break;
            case R.id.signin_btn_fb:
                break;
        }
    }

    private boolean isReady(String username, String password) {
        if (TextUtils.isEmpty(username)) {
            toast("请填写注册账号");
            mUsernameEt.requestFocus();
            mUsernameEt.setAnimation(ClearEditText.shakeAnimation(5));
            return false;
        }
        if (TextUtils.isEmpty(password) || password.length() != 6) {
            toast("密码为6位数字");
            mPasswordEt.requestFocus();
            mPasswordEt.setAnimation(ClearEditText.shakeAnimation(5));
            return false;
        }
        return true;
    }

    private void doSignup(final String username, final String password) {
        LoadingAlertDialog.showLoadDialog(LoginActivity.this, "注册中，请稍后", false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyUser user = new MyUser();
                user.setUsername(username);
                user.setPassword(password);
                user.setSex(gender);
                user.signUp(new SaveListener<MyUser>() {
                    @Override
                    // s.toString() (一个实体)          com.shenhua.nandagy.bean.bmobbean.MyUser@ae27bd1
                    // e.toString()  (BmobException)   errorCode:202,errorMsg:username 'sendi' already taken.
                    public void done(MyUser s, BmobException e) {
                        if (e == null) {
                            LoadingAlertDialog.dissmissLoadDialog();
                            toast("注册成功！");
                            doSignin(username, password);
                        } else {
                            LoadingAlertDialog.dissmissLoadDialog();
                            if (e.getErrorCode() == 202)
                                toast("注册失败，\"" + username + "\"已被注册！");
                            else toast("注册失败，错误码：" + e.getErrorCode());
                        }
                    }
                });
            }
        }, 2000);
    }

    private void doSignin(final String username, final String password) {
        LoadingAlertDialog.showLoadDialog(LoginActivity.this, "登录中，请稍后", false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyUser user = new MyUser();
                user.setUsername(username);
                user.setPassword(password);
                user.login(new SaveListener<MyUser>() {

                    @Override
                    public void done(MyUser bmobUser, BmobException e) {
                        if (e == null) {
                            LoadingAlertDialog.dissmissLoadDialog();
                            toast("登录成功！");
                            onLoginSuccess();
                            //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                            //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
                        } else {
                            LoadingAlertDialog.dissmissLoadDialog();
                            if (e.getErrorCode() == 101)
                                toast("登录失败，用户名或密码错误");
                            else toast("登录失败，错误代码" + e.getErrorCode());
                        }
                    }
                });
            }
        }, 2000);
    }

    private void onLoginSuccess() {
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        MyUser user = new MyUser();
        user.setUserId(myUser.getObjectId());
        user.setUserName(myUser.getUsername());
        user.setPhone(myUser.getMobilePhoneNumber());
        user.seteMail(myUser.getEmail());
        user.setNick(myUser.getNick());
        user.setName_num(myUser.getName_num());
        user.setName(myUser.getName());
        user.setName_id(myUser.getName_id());
        user.setInfo(myUser.getInfo());
        user.setUrl_photo(myUser.getUrl_photo());
        user.setSex(myUser.getSex());
        user.setUserZoneObjID(myUser.getUserZoneObjID());
        UserUtils.getInstance().setUser(this, user);
        if (!TextUtils.isEmpty(myUser.getUserZoneObjID())) {
            UserZoneUtils.getInstance().updateZoneStatis(myUser.getUserZoneObjID(), "exper", 2);
            EventBus.getDefault().post(new NewMessageEventBus(true, UserFragment.EVENT_TYPE_EXPER_ADD_2));
        }
        setResult(LOGIN_SUCCESS);
        this.finish();
    }

    public void onSwitchView() {
        centerX = mSignContent.getWidth() / 2;
        centerY = mSignContent.getHeight() / 2;
        if (openAnimation == null) {
            initOpenAnim();
            initCloseAnim();
        }
        if (openAnimation.hasStarted() && !openAnimation.hasEnded()) return;
        if (closeAnimation.hasStarted() && !closeAnimation.hasEnded()) return;
        if (isSignup)
            mSignContent.startAnimation(closeAnimation);
        else
            mSignContent.startAnimation(openAnimation);
        isSignup = !isSignup;
    }

    private IUiListener loginListener = new IUiListener() {

        @Override
        public void onCancel() {

        }

        @Override
        public void onComplete(Object value) {
            if (value == null) return;
            try {
                JSONObject jo = (JSONObject) value;
                openID = jo.getString("openid");
                String accessToken = jo.getString("access_token");
                String expiresIn = jo.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken, expiresIn);
                getUserInfo();
            } catch (Exception e) {
                LoginActivity.this.toast("登录时产生错误");
            }
        }

        private void getUserInfo() {
            if (mTencent.getQQToken() != null) {
                UserInfo userInfo = new UserInfo(LoginActivity.this, mTencent.getQQToken());
                userInfo.getUserInfo(userInfoListener);
            }
        }

        @Override
        public void onError(UiError arg0) {

        }

    };

    private IUiListener userInfoListener = new IUiListener() {

        @Override
        public void onCancel() {

        }

        @Override
        public void onComplete(Object arg0) {
            if (arg0 == null) return;
            try {
                JSONObject jo = (JSONObject) arg0;
                int ret = jo.getInt("ret");
                if (ret == 100030) {
                    // 权限不够，需要增量授权
                    LoginActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTencent.reAuth(LoginActivity.this, "all", new IUiListener() {

                                @Override
                                public void onError(UiError arg0) {

                                }

                                @Override
                                public void onComplete(Object arg0) {

                                }

                                @Override
                                public void onCancel() {

                                }
                            });
                        }
                    });
                } else {
                    String nickName = jo.getString("nickname");
                    String sex = jo.getString("gender");
                    if (sex.equals("女")) gender = true;
                    String qqPhoimgUrl = "";
                    try {
                        qqPhoimgUrl = jo.getString("figureurl_qq_2");
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    doSignup(openID, "123456", qqPhoimgUrl, nickName);


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError arg0) {

        }

    };

    private void doSignup(final String username, final String password,
                          final String imgUrl, final String nick) {
        LoadingAlertDialog.showLoadDialog(LoginActivity.this, "授权验证中...", false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyUser user = new MyUser();
                user.setUsername(username);
                user.setPassword(password);
                user.setSex(gender);
                user.setUrl_photo(imgUrl);
                user.setNick(nick);
                user.signUp(new SaveListener<MyUser>() {
                    @Override
                    public void done(MyUser s, BmobException e) {
                        if (e == null) {
                            LoadingAlertDialog.dissmissLoadDialog();
                            toast("授权成功！");
                            doSignin(username, password);
                        } else {
                            LoadingAlertDialog.dissmissLoadDialog();
                            if (e.getErrorCode() == 202) {
                                System.out.println("shenhua sout:" + username);
                                doSignin(username, password);
                            }
                        }
                    }
                });
            }
        }, 2000);
    }

    private void initOpenAnim() {
        openAnimation = new Rotate3dAnimation(0, 90, centerX, centerY, depthZ, true);
        openAnimation.setDuration(duration);
        openAnimation.setFillAfter(true);
        openAnimation.setInterpolator(new AccelerateInterpolator());
        openAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mSigninView.setVisibility(View.GONE);
                mSignupView.setVisibility(View.VISIBLE);
                Rotate3dAnimation rotateAnimation = new Rotate3dAnimation(270, 360, centerX, centerY, depthZ, false);
                rotateAnimation.setDuration(duration);
                rotateAnimation.setFillAfter(true);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());
                mSignContent.startAnimation(rotateAnimation);
            }
        });
    }

    private void initCloseAnim() {
        closeAnimation = new Rotate3dAnimation(360, 270, centerX, centerY, depthZ, true);
        closeAnimation.setDuration(duration);
        closeAnimation.setFillAfter(true);
        closeAnimation.setInterpolator(new AccelerateInterpolator());
        closeAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mSigninView.setVisibility(View.VISIBLE);
                mSignupView.setVisibility(View.GONE);
                Rotate3dAnimation rotateAnimation = new Rotate3dAnimation(90, 0, centerX, centerY, depthZ, false);
                rotateAnimation.setDuration(duration);
                rotateAnimation.setFillAfter(true);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());
                mSignContent.startAnimation(rotateAnimation);
            }
        });
    }
}
