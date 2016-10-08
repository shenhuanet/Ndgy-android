package com.shenhua.nandagy.frag.score;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.activity.ScoreQueryResultActivity;
import com.shenhua.nandagy.base.BaseScoreQueryFragment;
import com.shenhua.nandagy.bean.bmobbean.ScoreQuery;
import com.shenhua.nandagy.bean.scorebean.ScoreCETBean;
import com.shenhua.nandagy.bean.scorebean.ScoreCETParams;
import com.shenhua.nandagy.callback.OnScoreQueryListener;
import com.shenhua.nandagy.callback.TextInputWatcher;
import com.shenhua.nandagy.presenter.QueryTask;
import com.shenhua.nandagy.utils.DESUtils;

import java.io.Serializable;

import butterknife.Bind;

/**
 * 四六级英语
 * Created by Shenhua on 9/7/2016.
 */
public class ScoreCetFragment extends BaseScoreQueryFragment {

    private static ScoreCetFragment instance = null;
    @Bind(R.id.til_zkzh)
    TextInputLayout mZkzhLayout;
    @Bind(R.id.til_name)
    TextInputLayout mNameLayout;
    @Bind(R.id.et_zkzh)
    EditText mZkzhEt;
    @Bind(R.id.et_name)
    EditText mNameEt;

    public static ScoreCetFragment newInstance() {
        if (instance == null) {
            instance = new ScoreCetFragment();
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
        mZkzhEt.addTextChangedListener(new TextInputWatcher(mZkzhLayout));
        mNameEt.addTextChangedListener(new TextInputWatcher(mNameLayout));
    }

    @Override
    protected void doQuery() {
        String zkzh = mZkzhEt.getText().toString();
        String name = mNameEt.getText().toString();
        if (zkzh.length() != 15) {
            mZkzhLayout.setError("准考证号输入错误");
            return;
        }
        if (TextUtils.isEmpty(name)) {
            mNameLayout.setError("请输入姓名");
            return;
        }
        ScoreQuery query = new ScoreQuery();
        query.setQueryType("cet");
        query.setZkzh(DESUtils.getInstance().encrypt(zkzh));
        query.setName(name);
        sendToDatabase(query);
        ScoreCETParams data = new ScoreCETParams(name, zkzh);
        new QueryTask<>(getActivity(), data, new OnScoreQueryListener() {
            @Override
            public void onQuerySuccess(Object result) {
                System.out.println("shenhua sout:回调结果--->" + ((ScoreCETBean) result).getName());
                Intent intent = new Intent(getActivity(), ScoreQueryResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("result", (Serializable) result);
                intent.putExtras(bundle);
                intent.putExtra("type", 0);
                startActivity(intent);
            }

            @Override
            public void onQueryFailed(int errorCode, String msg) {
                toast(msg);
            }

        }).execute(1);
    }
}
