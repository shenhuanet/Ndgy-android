package com.shenhua.nandagy.module.jiaowu.ui.adapter;

import com.shenhua.nandagy.BR;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseRecyclerBindingAdapter;
import com.shenhua.nandagy.bean.scorebean.ExamScore;

import java.util.List;

/**
 * 考试成绩适配器
 * Created by shenhua on 4/6/2017.
 * Email shenhuanet@126.com
 */
public class ExamScoreAdapter extends BaseRecyclerBindingAdapter<ExamScore.ExamScoreList> {

    public ExamScoreAdapter(List<ExamScore.ExamScoreList> mData) {
        super(mData);
    }

    @Override
    public int getStartMode() {
        return 0;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return  R.layout.item_exam_score_list_content;
    }

    @Override
    public int getVariableId(int viewType) {
        return BR.examScore;
    }

    @Override
    public int getItemTypePosition(int position) {
        return 0;
    }

}
