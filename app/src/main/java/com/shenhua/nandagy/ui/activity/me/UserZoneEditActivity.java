package com.shenhua.nandagy.ui.activity.me;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.commonlibs.utils.ConvertUtils;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.bmobbean.UserZone;
import com.shenhua.nandagy.widget.LoadingAlertDialog;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;

/**
 * 用户主页编辑界面
 * Created by Shenhua on 9/4/2016.
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_user_zone_edit,
        toolbarId = R.id.common_toolbar,
        toolbarHomeAsUp = true,
        toolbarTitle = R.string.toolbar_title_user_zone_edit,
        toolbarTitleId = R.id.toolbar_title
)
public class UserZoneEditActivity extends BaseActivity {

    @BindView(R.id.et_edit_zone_name)
    EditText mNameEt;
    @BindView(R.id.et_edit_zone_sign)
    EditText mSignEt;
    @BindView(R.id.tv_edit_zone_brith)
    TextView mBrithTv;
    @BindView(R.id.tv_edit_zone_locate)
    TextView mLocateTv;
    @BindView(R.id.tv_edit_zone_love)
    TextView mLoveTv;
    @BindView(R.id.tv_edit_zone_depart)
    TextView mDepartTv;
    @BindView(R.id.tv_edit_zone_qual)
    TextView mQualTv;
    @BindView(R.id.et_edit_zone_school)
    EditText mSchoolEt;
    private UserZone userZone;

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        ButterKnife.bind(this);
        userZone = (UserZone) getIntent().getSerializableExtra("userZoneInfo");
        mNameEt.setText(userZone.getName());
        mSignEt.setText(userZone.getSign());
        mBrithTv.setText(userZone.getBirth());
        mLocateTv.setText(userZone.getLocate());
        mLoveTv.setText(userZone.getLove());
        mDepartTv.setText(userZone.getDepart());
        mQualTv.setText(userZone.getQual());
        mSchoolEt.setText(userZone.getHighSchool());
    }

    @OnClick({R.id.layout_edit_zone_name, R.id.layout_edit_zone_sign, R.id.layout_edit_zone_brith,
            R.id.layout_edit_zone_locate, R.id.layout_edit_zone_love,
            R.id.layout_edit_zone_depart, R.id.layout_edit_zone_qual, R.id.btn_edit_save,
            R.id.layout_edit_zone_rootview})
    void clicks(View v) {
        String[] items;
        switch (v.getId()) {
            case R.id.layout_edit_zone_name:
                hideKeyboard();
                break;
            case R.id.layout_edit_zone_sign:
                hideKeyboard();
                break;
            case R.id.layout_edit_zone_brith:
                hideKeyboard();
                selectBirth();
                break;
            case R.id.layout_edit_zone_locate:
                hideKeyboard();
                selectLocate();
                break;
            case R.id.layout_edit_zone_love:
                hideKeyboard();
                items = getResources().getStringArray(R.array.dialog_choice_love_items);
                showSelectStateDialog(items, mLoveTv);
                break;
            case R.id.layout_edit_zone_depart:
                hideKeyboard();
                items = getResources().getStringArray(R.array.dialog_choice_depart_items);
                showSelectStateDialog(items, mDepartTv);
                break;
            case R.id.layout_edit_zone_qual:
                hideKeyboard();
                items = getResources().getStringArray(R.array.dialog_choice_qual_items);
                showSelectStateDialog(items, mQualTv);
                break;
            case R.id.btn_edit_save:
                doUpdateInfo();
                break;
            case R.id.layout_edit_zone_rootview:
                hideKeyboard();
                break;
        }
    }

    private void selectBirth() {
        DatePicker picker = new DatePicker(this, DatePicker.YEAR_MONTH_DAY);
        picker.setRangeStart(1980, 1, 1);
        picker.setRangeEnd(2020, 1, 1);
        picker.setOnDatePickListener((DatePicker.OnYearMonthDayPickListener) (year, month, day) ->
                mBrithTv.setText(year + "-" + month + "-" + day));
        picker.show();
    }

    private void selectLocate() {
        String json = null;
        try {
            json = ConvertUtils.toString(getAssets().open("city.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (json == null) {
            CrashReport.postCatchedException(new Throwable("com.shenhua.nandagy.ui.activity.me.UserZoneEditActivity city.json is null."));
            return;
        }

        Gson gson = new Gson();
        ArrayList<AddressPicker.Province> data = gson.fromJson(json, new TypeToken<List<AddressPicker.Province>>() {
        }.getType());
        AddressPicker picker = new AddressPicker(this, data);
        picker.setHideCounty(true);
        picker.setSelectedItem("江西省", "九江市", "");
        picker.setOnAddressPickListener((province, city, county) ->
                mLocateTv.setText(province.getAreaName() + "-" + city.getAreaName()));
        picker.show();
    }

    private void showSelectStateDialog(final String[] items, final TextView targetTv) {
        OptionPicker picker = new OptionPicker(this, items);
        picker.setOnOptionPickListener((position, option) -> targetTv.setText(option));
        picker.show();
    }

    private void doUpdateInfo() {
        LoadingAlertDialog.getInstance(this).showLoadDialog("资料更新中，请稍后...", true);
        userZone.setName(mNameEt.getText().toString());
        userZone.setSign(mSignEt.getText().toString());
        userZone.setBirth(mBrithTv.getText().toString());
        userZone.setLocate(mLocateTv.getText().toString());
        userZone.setLove(mLoveTv.getText().toString());
        userZone.setDepart(mDepartTv.getText().toString());
        userZone.setQual(mQualTv.getText().toString());
        userZone.setHighSchool(mSchoolEt.getText().toString());
        userZone.update(userZone.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                LoadingAlertDialog.getInstance(UserZoneEditActivity.this).dissmissLoadDialog();
                if (e != null) {
                    toast("数据更新成功！");
                } else {
                    toast("数据更新失败：" + e.getMessage());
                }
            }
        });
    }
}
