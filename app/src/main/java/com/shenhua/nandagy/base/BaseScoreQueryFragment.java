package com.shenhua.nandagy.base;

import android.view.View;

import com.shenhua.commonlibs.base.BaseFragment;
import com.shenhua.nandagy.App;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.bmobbean.ScoreQuery;

import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 成绩查询基类
 * Created by shenhua on 9/8/2016.
 */
public abstract class BaseScoreQueryFragment extends BaseFragment {

    @OnClick({R.id.btn_query, R.id.layout_score_root})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_query:
                doQuery();
                break;
            case R.id.layout_score_root:
                hideKeyboard();
                break;
        }
    }

    protected abstract void doQuery();

    protected void sendToDatabase(ScoreQuery data) {
        if (App.DEBUG_MODE) {
            data.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                }
            });
        }
    }

}
