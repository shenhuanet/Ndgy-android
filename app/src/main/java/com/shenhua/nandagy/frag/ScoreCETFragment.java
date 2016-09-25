package com.shenhua.nandagy.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseScoreQueryFragment;
import com.shenhua.nandagy.bean.ScoreCETParams;
import com.shenhua.nandagy.bean.bmobbean.ScoreQuery;
import com.shenhua.nandagy.callback.OnEditTextChange;
import com.shenhua.nandagy.callback.OnScoreQueryListener;
import com.shenhua.nandagy.presenter.QueryTask;
import com.shenhua.nandagy.utils.DESUtils;

import butterknife.Bind;

/**
 * 四六级英语
 * Created by Shenhua on 9/7/2016.
 */
public class ScoreCETFragment extends BaseScoreQueryFragment {

    private static ScoreCETFragment instance = null;
    @Bind(R.id.til_zkzh)
    TextInputLayout mZkzhLayout;
    @Bind(R.id.til_name)
    TextInputLayout mNameLayout;
    @Bind(R.id.et_zkzh)
    EditText mZkzhEt;
    @Bind(R.id.et_name)
    EditText mNameEt;

    public static ScoreCETFragment newInstance() {
        if (instance == null) {
            instance = new ScoreCETFragment();
        }
        return instance;
    }

    @Override
    protected int getViewLayoutId() {
        return R.layout.frag_score_cet;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mZkzhEt.addTextChangedListener(new OnEditTextChange(mZkzhLayout));
        mNameEt.addTextChangedListener(new OnEditTextChange(mNameLayout));
    }

    @Override
    protected void doQuery() {
        String zkzh = mZkzhEt.getText().toString();
        String name = mNameEt.getText().toString();
        if (zkzh.length() != 15) {
            mZkzhLayout.setError("准考证号输入错误");
            mZkzhEt.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            return;
        }
        if (TextUtils.isEmpty(name)) {
            mNameLayout.setError("请输入姓名");
            mNameEt.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            return;
        }
//        sendToDatabase(zkzh, name);

        ScoreQuery query = new ScoreQuery();
        query.setQueryType("cet");
        query.setZkzh(DESUtils.getInstance().encrypt(zkzh));
        query.setName(name);
        sendToDatabase(query);

        ScoreCETParams data = new ScoreCETParams(name, zkzh);
        new QueryTask<>(getActivity(), data, new OnScoreQueryListener() {
            @Override
            public void onQuerySuccess(Object result) {
                System.out.println("shenhua sout:回调结果--->" + result);
            }

            @Override
            public void onQueryFailed(int errorCode) {
                toast("查询错误！");
            }

        }).execute(1);
    }

}
