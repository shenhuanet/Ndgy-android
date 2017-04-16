package com.shenhua.nandagy.ui.activity.me;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
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
import com.shenhua.commonlibs.widget.ClearEditText;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.bmobbean.MyUser;
import com.shenhua.nandagy.databinding.ActivityUserAccountBinding;
import com.shenhua.nandagy.utils.bmobutils.UserUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 我的账号界面，用户可直接更改信息
 * Created by Shenhua on 9/4/2016.
 */
@ActivityFragmentInject(
        toolbarId = R.id.common_toolbar,
        toolbarHomeAsUp = true,
        toolbarTitle = R.string.toolbar_title_user_account,
        toolbarTitleId = R.id.toolbar_title
)
public class UserAccountActivity extends BaseActivity {

    public static final int LOGOUT_SUCCESS = 101;
    private static final int TYPE_NICK = 1;
    private static final int TYPE_PHONE = 2;
    private static final int TYPE_EMAIL = 3;

    @BindView(R.id.radiogroup)
    RadioGroup mSexRadioGroup;
    @BindView(R.id.rb_man)
    RadioButton mGenderMan;
    @BindView(R.id.rb_woman)
    RadioButton mGenderWoman;

    private MyUser myUser;
    private ActivityUserAccountBinding binding;

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_account);
        ButterKnife.bind(this);
        initToolbar();
        myUser = BmobUser.getCurrentUser(MyUser.class);
        binding.setUser(myUser);
        updateSex();
    }

    @OnClick({R.id.btn_logout, R.id.account_layout_id, R.id.account_layout_nick, R.id.account_layout_password,
            R.id.account_layout_phone, R.id.account_layout_mail})
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
                showEditDialog("设置昵称", "请输入昵称", myUser.getNick(), TYPE_NICK);
                break;
            case R.id.account_layout_password:
                showResetPwdDialog();
                break;
            case R.id.account_layout_phone:
                showEditDialog("设置手机号码", "请输入手机号码", myUser.getMobilePhoneNumber(), TYPE_PHONE);
                break;
            case R.id.account_layout_mail:
                showEditDialog("设置邮箱", "请输入手邮箱", myUser.getEmail(), TYPE_EMAIL);
                break;
        }
    }

    /**
     * 显示修改密码对话框
     */
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

    /**
     * 显示编辑对话框
     *
     * @param title 标题
     * @param hint  提示文本
     * @param str   预设内容
     * @param type  更改类别
     */
    private void showEditDialog(String title, String hint, final String str, final int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_account_edit, null);
        TextView titleTv = (TextView) view.findViewById(R.id.editdialog_title);
        ClearEditText conEv = (ClearEditText) view.findViewById(R.id.editdialog_et);
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
        cancelTv.setOnClickListener(v -> dialog.dismiss());
        doneTv.setOnClickListener(v -> {
            final String value = conEv.getText().toString();
            if (!value.equals(str)) {
                switch (type) {
                    case TYPE_NICK:
                        if (updateNick(value))
                            return;
                        break;
                    case TYPE_PHONE:
                        if (updatePhone(value))
                            return;
                        break;
                    case TYPE_EMAIL:
                        if (updateEmail(value))
                            return;
                        break;
                }
            }
            dialog.dismiss();
        });
    }

    /**
     * 设置性别显示状态
     */
    private void updateSex() {
        if (myUser.getSex()) {
            mGenderWoman.setChecked(true);
        } else {
            mGenderMan.setChecked(true);
        }
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
                    if (e == null) {
                        toast("信息更新成功");
                        UserUtils.getInstance().updateUserInfo(UserAccountActivity.this, "gender", finalGender);
                    } else {
                        toast("信息更新失败");
                    }
                }
            });
        });
    }

    /**
     * 更新邮箱
     *
     * @param value 新值
     * @return true 不合理
     */
    private boolean updateEmail(final String value) {
        if (!CheckUtils.isEmail(value)) {
            toast("邮箱格式有误");
            return true;
        }
        myUser.setEmail(value);
        binding.tvUseraccountEmail.setText(value);
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
        return false;
    }

    /**
     * 更新手机号
     *
     * @param value 新值
     * @return true 不合理
     */
    private boolean updatePhone(final String value) {
        if (!CheckUtils.isPhone(value)) {
            toast("手机号码格式有误");
            return true;
        }
        myUser.setMobilePhoneNumber(value);
        binding.tvUseraccountPhone.setText(value);
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
        return false;
    }

    /**
     * 更新昵称
     *
     * @param value 新值
     * @return true 不合理
     */
    private boolean updateNick(final String value) {
        if (TextUtils.isEmpty(value)) {
            toast("不能设置空内容");
            return true;
        }
        myUser.setNick(value);
        binding.tvUseraccountNick.setText(value);
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
