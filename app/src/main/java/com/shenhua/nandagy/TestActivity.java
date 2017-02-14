package com.shenhua.nandagy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.shenhua.nandagy.bean.StudyListData;
import com.shenhua.nandagy.database.StudyDBDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenhua on 2/13/2017.
 * Email shenhuanet@126.com
 */
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StudyDBDao dbDao = new StudyDBDao(this, 2);
        List<StudyListData> datas = new ArrayList<>();
        datas.add(new StudyListData("222", "254646465555454646546546", "454654564111213213131"));
        datas.add(new StudyListData("222", "254646465555454646546546", "454654564111213213131"));
        datas.add(new StudyListData("222", "254646465555454646546546", "454654564111213213131"));
        datas.add(new StudyListData("222", "254646465555454646546546", "454654564111213213131"));
        dbDao.deleteTable();
        dbDao.addList(datas);
    }
}
