package com.shenhua.nandagy.database;

import android.content.Context;

import com.shenhua.nandagy.base.BaseSQLiteDao;
import com.shenhua.nandagy.bean.StudyListData;

import static com.shenhua.nandagy.database.StudyDBHelper.TABLE_NAME;

/**
 * Created by shenhua on 2/13/2017.
 * Email shenhuanet@126.com
 */
public class StudyDBDao extends BaseSQLiteDao<StudyListData> {

    public StudyDBDao(Context context) {
        super(context, new StudyDBHelper(context));
        System.out.println("shenhua sout:" + "StudyDBDao.init");
    }

    @Override
    public String setTableName() {
        return TABLE_NAME;
    }


}
