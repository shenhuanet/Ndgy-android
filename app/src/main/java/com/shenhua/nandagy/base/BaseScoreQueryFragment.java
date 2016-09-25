package com.shenhua.nandagy.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.bmobbean.ScoreQuery;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 成绩查询基类
 * Created by shenhua on 9/8/2016.
 */
public abstract class BaseScoreQueryFragment extends Fragment {

    protected View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getViewLayoutId(), container, false);
            ButterKnife.bind(this, view);
        }
        ViewGroup group = (ViewGroup) view.getParent();
        if (group != null)
            group.removeView(view);
        return view;
    }

    @OnClick({R.id.btn_query, R.id.layout_score_root})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_query:
                doQuery();
                break;
            case R.id.layout_score_root:
                hideKbTwo();
                break;
        }
    }

    protected abstract int getViewLayoutId();

    protected abstract void doQuery();

    public void toast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    public void hideKbTwo() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getActivity().getCurrentFocus() != null) {
            if (getActivity().getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    protected void sendToDatabase(ScoreQuery data) {
        data.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                System.out.println("shenhua sout:" + "已保存到：" + s);
            }
        });
    }

}
