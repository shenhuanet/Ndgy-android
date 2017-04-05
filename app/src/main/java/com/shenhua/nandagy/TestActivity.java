package com.shenhua.nandagy;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.shenhua.nandagy.adapter.ExamScoreAdapter;
import com.shenhua.nandagy.bean.scorebean.ExamScore;
import com.shenhua.nandagy.bean.scorebean.GradeScore;
import com.shenhua.nandagy.databinding.ActivityTestBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenhua on 2/13/2017.
 * Email shenhuanet@126.com
 */
public class TestActivity extends AppCompatActivity {

    ActivityTestBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        ExamScoreAdapter adapter = new ExamScoreAdapter(getList());
        binding.recyclerView.setAdapter(adapter);

//        BaseRecyclerBindingAdapter.BaseRecyclerModel model = adapter.new BaseRecyclerModel<ExamScore.Overview>
//                (R.layout.item_exam_score_list_head, BR.overView, getHeader());
//        adapter.addHeadView(model);
        adapter.setItemClickListener((view, position) -> {
            Toast.makeText(this, "position:" + position, Toast.LENGTH_SHORT).show();
        });
//        adapter.setHeaderClickListener((view, position) -> {
//            Toast.makeText(this, "heihei", Toast.LENGTH_SHORT).show();
//        });
    }

    private ExamScore.Overview getHeader() {
        ExamScore.Overview overview = new ExamScore().new Overview();
        overview.setRequestCredit("110");
        overview.setGainCredit("-10.0");
        overview.setTotalPeople("159");
        return overview;
    }

    private List<GradeScore> getList() {
        List<GradeScore> lists = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            GradeScore list = new GradeScore();
            list.setGrade("100" + i);
            lists.add(list);
        }
        return lists;
    }
}
