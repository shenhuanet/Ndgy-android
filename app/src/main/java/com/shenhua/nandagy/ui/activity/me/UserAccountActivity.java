package com.shenhua.nandagy.ui.activity.me;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.commonlibs.utils.CheckUtils;
import com.shenhua.commonlibs.utils.DESUtils;
import com.shenhua.commonlibs.widget.ClearEditText;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.bmobbean.MyUser;
import com.shenhua.nandagy.bean.bmobbean.UserZone;
import com.shenhua.nandagy.utils.bmobutils.UserUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 我的账号界面
 * Created by Shenhua on 9/4/2016.
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_user_account,
        toolbarId = R.id.common_toolbar,
        toolbarHomeAsUp = true,
        toolbarTitle = R.string.toolbar_title_user_account,
        toolbarTitleId = R.id.toolbar_title
)
public class UserAccountActivity extends BaseActivity {

    public static final int LOGOUT_SUCCESS = 101;
    @BindView(R.id.account_tv_id)
    TextView mIdTv;
    @BindView(R.id.account_tv_nick)
    TextView mNickTv;
    @BindView(R.id.radiogroup)
    RadioGroup mSexRadioGroup;
    @BindView(R.id.rb_man)
    RadioButton mGenderMan;
    @BindView(R.id.rb_woman)
    RadioButton mGenderWoman;
    @BindView(R.id.account_tv_phone)
    TextView mPhoneTv;
    @BindView(R.id.account_tv_mail)
    TextView mMailTv;
    @BindView(R.id.account_tv_name)
    TextView mNameTv;
    @BindView(R.id.account_tv_num)
    TextView mNumTv;
    private static final int TYPE_NICK = 1;
    private static final int TYPE_PHONE = 2;
    private static final int TYPE_EMAIL = 3;
    private MyUser myUser;

    // R.id.account_layout_id,R.id.account_layout_nick,R.id.account_layout_password,R.id.account_layout_phone,R.id.account_layout_mail,R.id.account_layout_name,R.id.account_layout_num

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        ButterKnife.bind(this);
        myUser = BmobUser.getCurrentUser(MyUser.class);
        mIdTv.setText(myUser.getUsername());
        mNickTv.setText(myUser.getNick());
        if (myUser.getSex()) mGenderWoman.setChecked(true);
        else mGenderMan.setChecked(true);
        mPhoneTv.setText(myUser.getMobilePhoneNumber());
        mMailTv.setText(myUser.getEmail());
        mNameTv.setText(DESUtils.getInstance().decrypt(myUser.getName()));
        mNumTv.setText(DESUtils.getInstance().decrypt(myUser.getName_num()));
        updateSex();
    }

    @OnClick({R.id.btn_logout, R.id.account_layout_id, R.id.account_layout_nick, R.id.account_layout_password,
            R.id.account_layout_phone, R.id.account_layout_mail, R.id.account_layout_name, R.id.account_layout_num})
    void onClicks(View v) {
        switch (v.getId()) {
            case R.id.btn_logout:
                BmobUser.logOut();
                UserUtils.getInstance().logout(this);
                toast("退出成功");
                setResult(LOGOUT_SUCCESS);
                this.finish();
                break;
            case R.id.account_layout_id:
                toast("用户名可用于登录，暂不支持修改，如需修改请联系管理员");
                break;
            case R.id.account_layout_nick:
                showEditDialog("设置昵称", "请输入昵称", mNickTv.getText().toString(), TYPE_NICK);
                break;
            case R.id.account_layout_password:
                showResetPwdDialog();
                break;
            case R.id.account_layout_phone:
                showEditDialog("设置手机号码", "请输入手机号码", mPhoneTv.getText().toString(), TYPE_PHONE);
                break;
            case R.id.account_layout_mail:
                showEditDialog("设置邮箱", "请输入手邮箱", mMailTv.getText().toString(), TYPE_EMAIL);
                break;
            case R.id.account_layout_name:

                break;
            case R.id.account_layout_num:

                break;
        }
    }

    private void showResetPwdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_account_resetpwd, null);
        final ClearEditText conYEv = (ClearEditText) view.findViewById(R.id.editdialog_et_y);
        final ClearEditText conNEv = (ClearEditText) view.findViewById(R.id.editdialog_et_n);
        TextView cancelTv = (TextView) view.findViewById(R.id.dialog_cancel);
        TextView doneTv = (TextView) view.findViewById(R.id.dialog_done);
        builder.setView(view);
        final Dialog dialog = builder.show();
        cancelTv.setOnClickListener(v -> dialog.dismiss());
        doneTv.setOnClickListener(v -> {
            String oldP = conYEv.getText().toString();
            String newP = conNEv.getText().toString();
            if (oldP.length() != 6 || newP.length() != 6) {
                toast("请确认密码为6位数");
                return;
            }
            BmobUser.updateCurrentUserPassword(oldP, newP, new UpdateListener() {

                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        toast("密码修改成功，请使用新密码进行登录！");
                        BmobUser.logOut();
                        UserUtils.getInstance().logout(UserAccountActivity.this);
                        setResult(LOGOUT_SUCCESS);
                        UserAccountActivity.this.finish();
                    } else {
                        toast("密码修改失败:" + e.getMessage());
                    }
                }

            });
            dialog.dismiss();
        });
    }

    private void showEditDialog(String title, String hint, final String str, final int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_account_edit, null);
        TextView titleTv = (TextView) view.findViewById(R.id.editdialog_title);
        final ClearEditText conEv = (ClearEditText) view.findViewById(R.id.editdialog_et);
        switch (type) {
            case TYPE_NICK:
                conEv.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                break;
            case TYPE_PHONE:
                conEv.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                break;
            case TYPE_EMAIL:
                conEv.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
                break;
        }
        TextView cancelTv = (TextView) view.findViewById(R.id.dialog_cancel);
        TextView doneTv = (TextView) view.findViewById(R.id.dialog_done);
        builder.setView(view);
        final Dialog dialog = builder.show();
        titleTv.setText(title);
        conEv.setText(str);
        conEv.setHint(hint);
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        doneTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String value = conEv.getText().toString();
                if (!value.equals(str)) {
                    switch (type) {
                        case TYPE_NICK:
                            if (updateNick(value)) return;
                            break;
                        case TYPE_PHONE:
                            if (updatePhone(value)) return;
                            break;
                        case TYPE_EMAIL:
                            if (updateEmail(value)) return;
                            break;
                    }

                }
                dialog.dismiss();
            }
        });
    }

    private void updateSex() {
        mSexRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            boolean gender = false;
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
            myUser.setSex(gender);
            final boolean finalGender = gender;
            myUser.update(myUser.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e != null) toast(e.getMessage());
                    else {
                        toast("信息更新成功");
                        UserUtils.getInstance().updateUserInfo(UserAccountActivity.this, "gender", finalGender);
                    }
                }
            });
            String zoneObjId = myUser.getUserZone().getObjectId();
            if (!TextUtils.isEmpty(zoneObjId)) {
                UserZone p2 = new UserZone();
//                p2.setSex(gender);
                p2.update(zoneObjId, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {

                    }
                });
            }
        });
    }

    // TODO: 4/11/2017 return type
    private boolean updateEmail(final String value) {
        if (CheckUtils.isEmail(value)) {
            myUser.setEmail(value);
            mMailTv.setText(value);
            myUser.update(myUser.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e != null) toast(e.getMessage());
                    else {
                        toast("信息更新成功");
                        UserUtils.getInstance().updateUserInfo(UserAccountActivity.this, "email", value);
                    }
                }
            });
        } else {
            toast("邮箱格式有误");
            return true;
        }
        return false;
    }

    private boolean updatePhone(final String value) {
        if (CheckUtils.isPhone(value)) {
            myUser.setMobilePhoneNumber(value);
            mPhoneTv.setText(value);
            myUser.update(myUser.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e != null) toast(e.getMessage());
                    else {
                        toast("信息更新成功");
                        UserUtils.getInstance().updateUserInfo(UserAccountActivity.this, "phone", value);
                    }
                }
            });
        } else {
            toast("手机号码格式有误");
            return true;
        }
        return false;
    }

    private boolean updateNick(final String value) {
        if (TextUtils.isEmpty(value)) return true;
        myUser.setNick(value);
        mNickTv.setText(value);
        myUser.update(myUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e != null) toast(e.getMessage());
                else {
                    toast("信息更新成功");
                    UserUtils.getInstance().updateUserInfo(UserAccountActivity.this, "nick", value);
                }
            }
        });
        return false;
    }
}
