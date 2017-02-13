package com.shenhua.nandagy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.shenhua.nandagy.database.StudyDBDao;

/**
 * Created by shenhua on 2/13/2017.
 * Email shenhuanet@126.com
 */
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        StudyDBDao dbDao = new StudyDBDao(this);
    }
}
