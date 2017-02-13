package com.shenhua.nandagy.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.shenhua.nandagy.base.BaseSQLiteOpenHelper;

import static com.shenhua.nandagy.service.Constants.DATABASE_NAME;

/**
 * Created by shenhua on 2/13/2017.
 * Email shenhuanet@126.com
 */
public class StudyDBHelper extends BaseSQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "table_study";

    public StudyDBHelper(Context context) {
        super(context, DATABASE_NAME, TABLE_NAME, DB_VERSION);
    }

    @Override
    public void createDB(SQLiteDatabase db) {
        try {
            String sql = buildCreateSQL(
                    new String[]{
                            "title", "description", "href", "time", "content"
                    }, new String[]{
                            TYPE_STRING, TYPE_STRING, TYPE_STRING, TYPE_STRING, TYPE_STRING
                    });
            System.out.println("shenhua sout:222:" + sql);
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void upgradeDB(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
