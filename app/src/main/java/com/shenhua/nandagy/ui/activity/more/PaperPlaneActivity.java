package com.shenhua.nandagy.ui.activity.more;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.ui.fragment.more.PagerFriendsFragment;
import com.shenhua.nandagy.ui.fragment.more.PagerMessageFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 纸飞机
 * Created by Shenhua on 9/7/2016.
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_more_pager,
        toolbarId = R.id.common_toolbar,
        toolbarHomeAsUp = true
)
public class PaperPlaneActivity extends BaseActivity {

    private int current;

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        ButterKnife.bind(this);
        PagerMessageFragment fragment = PagerMessageFragment.getInstance();
        setFragment(fragment);
        current = 1;
    }

    @OnClick({R.id.button21, R.id.button22})
    void clicks(View v) {
        switch (v.getId()) {
            case R.id.button21:
                if (current == 1) return;
                PagerMessageFragment fragment = PagerMessageFragment.getInstance();
                setFragment(fragment);
                current = 1;
                break;
            case R.id.button22:
                if (current == 2) return;
                PagerFriendsFragment fragment2 = PagerFriendsFragment.getInstance();
                setFragment(fragment2);
                current = 2;
                break;
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        Fragment oldFrag = manager.findFragmentById(R.id.fragment_pager);
        if (oldFrag != null) {
            manager.beginTransaction().remove(oldFrag).commit();
        }
        manager.beginTransaction().replace(R.id.fragment_pager, fragment).commit();
    }
}
