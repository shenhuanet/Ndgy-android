package com.shenhua.nandagy;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.shenhua.nandagy.bean.scorebean.GradeScore;
import com.shenhua.nandagy.databinding.ActivityMyUserBinding;

/**
 * Created by shenhua on 2/13/2017.
 * Email shenhuanet@126.com
 */
public class MyUserActivity extends AppCompatActivity {

    ActivityMyUserBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_user);
        binding.setActivity(this);
    }

    public void click() {
        Toast.makeText(this, "点击了", Toast.LENGTH_SHORT).show();
        GradeScore gradeScore = new GradeScore();
        gradeScore.setName("erewfewrewrewr");
        binding.setScore(gradeScore);
    }
}
