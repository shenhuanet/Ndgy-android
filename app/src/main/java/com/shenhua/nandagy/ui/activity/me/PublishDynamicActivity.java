package com.shenhua.nandagy.ui.activity.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发布动态界面
 * Created by Shenhua on 9/4/2016.
 */
public class PublishDynamicActivity extends BaseActivity implements TextWatcher {

    @Bind(R.id.et_publish)
    EditText mPublishEt;
    @Bind(R.id.btn_publish)
    Button mPublishBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_dynamic);
        ButterKnife.bind(this);
        setupActionBar("发布动态", true);
        mPublishBtn.setEnabled(false);
        mPublishBtn.setClickable(false);
        mPublishEt.addTextChangedListener(this);
        mPublishEt.setText("http://docs.bmob.cn/data/Android/g_errorcode/doc/index.html#RESTAPI错误码列表");
    }

    @OnClick({R.id.rootview, R.id.btn_publish})
    void clicks(View v) {
        switch (v.getId()) {
            case R.id.rootview:
                hideKbTwo();
                break;
            case R.id.btn_publish:
                hideKbTwo();
                doPublish();
                break;
        }
    }

    private void doPublish() {
        toast("发布消息：" + mPublishEt.getText().toString());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mPublishEt.getText().length() <= 0) {
            mPublishBtn.setEnabled(false);
            mPublishBtn.setClickable(false);
        } else {
            mPublishBtn.setEnabled(true);
            mPublishBtn.setClickable(true);
        }
    }
}
