package com.shenhua.nandagy.module.more.score.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.callback.TextEnableInputWatcher;
import com.shenhua.commonlibs.utils.DESUtils;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseScoreQueryFragment;
import com.shenhua.nandagy.bean.bmobbean.ScoreQuery;
import com.shenhua.nandagy.bean.scorebean.ScoreMandarinParams;
import com.shenhua.nandagy.callback.OnScoreQueryListener;
import com.shenhua.nandagy.module.more.score.presenter.ScoreQueryTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 普通话水平
 * Created by Shenhua on 9/7/2016.
 */
@ActivityFragmentInject(contentViewId = R.layout.frag_score_mandarin)
public class ScoreMandarinFragment extends BaseScoreQueryFragment {

    @BindView(R.id.til_zkzh)
    TextInputLayout mZkzhLayout;
    @BindView(R.id.til_zjh)
    TextInputLayout mZJHLayout;
    @BindView(R.id.et_zkzh)
    EditText mZkzhEt;
    @BindView(R.id.et_name)
    EditText mNameEt;
    @BindView(R.id.et_zjh)
    EditText mZJHEt;

    public static ScoreMandarinFragment newInstance() {
        return new ScoreMandarinFragment();
    }

    @Override
    public void onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mZkzhEt.addTextChangedListener(new TextEnableInputWatcher(mZkzhLayout));
        mZJHEt.addTextChangedListener(new TextEnableInputWatcher(mZJHLayout));
    }

    @Override
    protected void doQuery() {
        String zkzh = mZkzhEt.getText().toString();
        String name = mNameEt.getText().toString();
        String zjh = mZJHEt.getText().toString();
        if ((!TextUtils.isEmpty(zkzh) && !TextUtils.isEmpty(name))
                || (!TextUtils.isEmpty(zkzh) && !TextUtils.isEmpty(zjh))
                || (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(zjh))) {
            if (zkzh.length() > 0 && zkzh.length() != 15) {
                mZkzhLayout.setError("准考证号输入有误");
            } else if (zjh.length() > 0 && zjh.length() != 13) {
                mZJHLayout.setError("证件编号输入有误");
            } else {
                ScoreQuery query = new ScoreQuery();
                query.setQueryType("mandarin");
                query.setZkzh(DESUtils.getInstance().encrypt(zkzh));
                query.setName(name);
                query.setSfzh(DESUtils.getInstance().encrypt("证件编号:" + zjh));
                sendToDatabase(query);
                ScoreMandarinParams data = new ScoreMandarinParams(zkzh, name, zjh);
                new ScoreQueryTask<>(getActivity(), data, new OnScoreQueryListener() {
                    @Override
                    public void onQuerySuccess(Object result) {
//                System.out.println("shenhua sout:回调结果--->" + ((ScoreMandarinBean) result).getName());
//                Intent intent = new Intent(getActivity(), ScoreQueryResultActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("result", (Serializable) result);
//                intent.putExtras(bundle);
//                intent.putExtra("type", 0);
//                startActivity(intent);
                    }

                    @Override
                    public void onQueryFailed(int errorCode, String msg) {
                        toast(msg);
                    }

                }).execute(2);
            }
        } else toast("请至少输入两项信息！");
    }

}
