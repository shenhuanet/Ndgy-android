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

    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME_YINGYU = "table_study_yingyu";
    private static final String TABLE_NAME_JISUANJI = "table_study_jisuanji";
    private static final String TABLE_NAME_DIANZI = "table_study_dianzi";
    private static final String TABLE_NAME_JIXIE = "table_study_jixie";
    private static final String TABLE_NAME_JINGGUAN = "table_study_jingguan";
    private static final String TABLE_NAME_JIANZHU = "table_study_jianzhu";
    private static final String TABLE_NAME_CAIKUAI = "table_study_caikuai";
    private int type;

    StudyDBHelper(Context context, int type) {
        super(context, DATABASE_NAME, getTableName(type), DB_VERSION);
        this.type = type;
    }

    static String getTableName(int type) {
        switch (type) {
            case 0:
                return TABLE_NAME_YINGYU;
            case 1:
                return TABLE_NAME_JISUANJI;
            case 2:
                return TABLE_NAME_DIANZI;
            case 3:
                return TABLE_NAME_JIXIE;
            case 4:
                return TABLE_NAME_JINGGUAN;
            case 5:
                return TABLE_NAME_JIANZHU;
            case 6:
                return TABLE_NAME_CAIKUAI;
        }
        return null;
    }

    @Override
    public void createDB(SQLiteDatabase db) {
        createTable(db);
    }

    @Override
    public void upgradeDB(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createTable(SQLiteDatabase db) {
        try {
            String sql = buildCreateSQL(
                    new String[]{
                            "title", "description", "href", "time", "content"
                    }, new String[]{
                            TYPE_STRING, TYPE_STRING, TYPE_STRING, TYPE_STRING, TYPE_STRING
                    });
            db.execSQL(sql);
            System.out.println("shenhua sout:" + getTableName(type) + ":创建成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
