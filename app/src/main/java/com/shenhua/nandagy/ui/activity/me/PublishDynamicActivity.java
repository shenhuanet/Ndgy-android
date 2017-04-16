package com.shenhua.nandagy.ui.activity.me;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.nandagy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    private boolean mCanPublish;

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        ButterKnife.bind(this);
//        mPublishBtn.setEnabled(false);
//        mPublishBtn.setClickable(false);
        mPublishEt.addTextChangedListener(this);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_menu_send).setEnabled(mCanPublish);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.publish_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_menu_send) {
            // TODO sth.
            hideKeyboard();
            toast("99999999999");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mCanPublish = mPublishEt.getText().length() > 0;
        invalidateOptionsMenu();
    }
}
