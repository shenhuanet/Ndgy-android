package com.shenhua.nandagy.adapter;

import com.shenhua.nandagy.BR;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseRecyclerBindingAdapter;
import com.shenhua.nandagy.bean.scorebean.GradeScore;

import java.util.List;

/**
 * Created by shenhua on 4/5/2017.
 * Email shenhuanet@126.com
 */
public class GradeScoreAdapter extends BaseRecyclerBindingAdapter<GradeScore> {

    public GradeScoreAdapter(List<GradeScore> mData) {
        super(mData);
    }

    @Override
    public int getStartMode() {
        return 0;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_grade_score_list_content;
    }

    @Override
    public int getVariableId(int viewType) {
        return BR.gradeScore;
    }

    @Override
    public int getItemTypePosition(int position) {
        return 0;
    }

}
