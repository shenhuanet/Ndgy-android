package com.shenhua.nandagy.ui.fragment.scorequery;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.scorebean.ScoreCETBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 四六级英语结果
 * Created by Shenhua on 9/7/2016.
 */
public class ScoreCetResultFragment extends Fragment {

    private static ScoreCetResultFragment instance = null;
    private View view;
    @BindView(R.id.cet_tv_name)
    TextView mNameTv;
    @BindView(R.id.cet_tv_school)
    TextView mSchoolTv;
    @BindView(R.id.cet_tv_exam_type)
    TextView mExamTypeTv;
    @BindView(R.id.cet_tv_exam_num)
    TextView mExamNumTv;
    @BindView(R.id.cet_tv_exam_time)
    TextView mExamTimeTv;
    @BindView(R.id.cet_tv_reading)
    TextView mReadingTv;
    @BindView(R.id.cet_tv_compos)
    TextView mComposTv;
    @BindView(R.id.cet_tv_listen)
    TextView mListenTv;
    @BindView(R.id.cet_tv_sum)
    TextView mSumTv;
    @BindView(R.id.iv_pass)
    ImageView mPassIv;
    private boolean isInit;

    public static ScoreCetResultFragment newInstance(ScoreCETBean data) {
        if (instance == null) {
            Bundle args = new Bundle();
            args.putSerializable("data", data);
            instance = new ScoreCetResultFragment();
            instance.setArguments(args);
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.score_frag_cet_result, container, false);
            ButterKnife.bind(this, view);
        }
        ViewGroup group = (ViewGroup) view.getParent();
        if (group != null)
            group.removeView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!isInit) {
            Bundle bundle = getArguments();
            ScoreCETBean data = (ScoreCETBean) bundle.getSerializable("data");
            assert data != null;
            mNameTv.setText(String.format(getString(R.string.cet_string_name), data.getName()));
            mSchoolTv.setText(String.format(getString(R.string.cet_string_school), data.getSchool()));
            mExamTypeTv.setText(String.format(getString(R.string.cet_string_exam_type), data.getExamType()));
            mExamNumTv.setText(String.format(getString(R.string.cet_string_exam_num), data.getExamNum()));
            mExamTimeTv.setText(String.format(getString(R.string.cet_string_exam_time), data.getExamTime()));
            mReadingTv.setText(String.format(getString(R.string.cet_string_reading), data.getReading()));
            mComposTv.setText(String.format(getString(R.string.cet_string_compos), data.getCompos()));
            mListenTv.setText(String.format(getString(R.string.cet_string_listen), data.getListen()));
            mSumTv.setText(String.format(getString(R.string.cet_string_sum), data.getSum()));
            mPassIv.setImageResource(Integer.valueOf(data.getSum()) >= 425 ? R.drawable.ic_exam_pass : R.drawable.ic_exam_nopass);
            mPassIv.setVisibility(View.VISIBLE);
            ObjectAnimator.ofFloat(mPassIv, "scaleX", 0f, 1.0f).setDuration(2000).start();
            ObjectAnimator.ofFloat(mPassIv, "scaleY", 0f, 1.0f).setDuration(2000).start();
        }
        isInit = true;
    }

}
