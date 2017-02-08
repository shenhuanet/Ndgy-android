package com.shenhua.nandagy.ui.activity.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseActivity;
import com.shenhua.nandagy.bean.bmobbean.UserZone;
import com.shenhua.nandagy.widget.LoadingAlertDialog;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.util.ConvertUtils;

/**
 * 用户主页编辑界面
 * Created by Shenhua on 9/4/2016.
 */
public class UserZoneEditActivity extends BaseActivity {

    @Bind(R.id.et_edit_zone_name)
    EditText mNameEt;
    @Bind(R.id.et_edit_zone_sign)
    EditText mSignEt;
    @Bind(R.id.tv_edit_zone_brith)
    TextView mBrithTv;
    @Bind(R.id.tv_edit_zone_locate)
    TextView mLocateTv;
    @Bind(R.id.tv_edit_zone_love)
    TextView mLoveTv;
    @Bind(R.id.tv_edit_zone_depart)
    TextView mDepartTv;
    @Bind(R.id.tv_edit_zone_qual)
    TextView mQualTv;
    @Bind(R.id.et_edit_zone_school)
    EditText mSchoolEt;
    private UserZone userZone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_zone_edit);
        ButterKnife.bind(this);
        setupActionBar("编辑空间", true);

        initView();
    }

    private void initView() {
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
                hideKbTwo();
                break;
            case R.id.layout_edit_zone_sign:
                hideKbTwo();
                break;
            case R.id.layout_edit_zone_brith:
                hideKbTwo();
                selectBrith();
                break;
            case R.id.layout_edit_zone_locate:
                hideKbTwo();
                selectLocate();
                break;
            case R.id.layout_edit_zone_love:
                hideKbTwo();
                items = getResources().getStringArray(R.array.dialog_choice_love_items);
                showSelectStateDialog(items, mLoveTv);
                break;
            case R.id.layout_edit_zone_depart:
                hideKbTwo();
                items = getResources().getStringArray(R.array.dialog_choice_depart_items);
                showSelectStateDialog(items, mDepartTv);
                break;
            case R.id.layout_edit_zone_qual:
                hideKbTwo();
                items = getResources().getStringArray(R.array.dialog_choice_qual_items);
                showSelectStateDialog(items, mQualTv);
                break;
            case R.id.btn_edit_save:
                doUpdateInfo();
                break;
            case R.id.layout_edit_zone_rootview:
                hideKbTwo();
                break;
        }
    }

    private void selectBrith() {
        DatePicker picker = new DatePicker(this, DatePicker.YEAR_MONTH_DAY);
        picker.setRangeStart(1980, 1, 1);//开始范围
        picker.setRangeEnd(2020, 1, 1);//结束范围
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                mBrithTv.setText(year + "-" + month + "-" + day);
            }
        });
        picker.show();
    }

    private void selectLocate() {
        ArrayList<AddressPicker.Province> data = new ArrayList<AddressPicker.Province>();
        String json = null;
        try {
            json = ConvertUtils.toString(getAssets().open("city.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        data.addAll(JSON.parseArray(json, AddressPicker.Province.class));
        AddressPicker picker = new AddressPicker(this, data);
//        picker.setHideProvince(true);//加上此句举将只显示地级及县级
        picker.setHideCounty(true);//加上此句举将只显示省级及地级
        picker.setSelectedItem("江西省", "九江市", "");
        picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
            @Override
            public void onAddressPicked(AddressPicker.Province province, AddressPicker.City city, AddressPicker.County county) {
                mLocateTv.setText(province.getAreaName() + "-" + city.getAreaName());
            }
        });
        picker.show();
    }

    private void showSelectStateDialog(final String[] items, final TextView targetTv) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("请选择状态");
//        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                targetTv.setText(items[which]);
//                dialog.dismiss();
//            }
//        });
//        builder.setNegativeButton("取消", null);
//        builder.show();
        OptionPicker picker = new OptionPicker(this, items);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int position, String option) {
                targetTv.setText(option);
            }
        });
        picker.show();
    }

    private void doUpdateInfo() {
        LoadingAlertDialog.showLoadDialog(this, "资料更新中，请稍后...", true);
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
                LoadingAlertDialog.dissmissLoadDialog();
                if (e != null) {
                    toast("数据更新成功！");
                } else {
                    toast("数据更新失败：" + e.getMessage());
                }
            }
        });
    }
}
