package com.shenhua.nandagy.ui.activity.me;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.nandagy.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发布动态界面
 * Created by Shenhua on 9/4/2016.
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_publish_dynamic,
        toolbarId = R.id.common_toolbar,
        toolbarHomeAsUp = true,
        toolbarTitle = R.string.toolbar_title_publish_dynamic,
        toolbarTitleId = R.id.toolbar_title
)
public class PublishDynamicActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.et_publish)
    EditText mPublishEt;
    @BindView(R.id.btn_publish)
    Button mPublishBtn;

    @Override
    protected void initView(BaseActivity baseActivity) {
        ButterKnife.bind(this);
        mPublishBtn.setEnabled(false);
        mPublishBtn.setClickable(false);
        mPublishEt.addTextChangedListener(this);
        mPublishEt.setText("http://docs.bmob.cn/data/Android/g_errorcode/doc/index.html#RESTAPI错误码列表");
    }

    @OnClick({R.id.rootview, R.id.btn_publish})
    void clicks(View v) {
        switch (v.getId()) {
            case R.id.rootview:
                hideKeyboard();
                break;
            case R.id.btn_publish:
                hideKeyboard();
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
