package com.shenhua.nandagy.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.shenhua.nandagy.base.BaseSQLiteDao;
import com.shenhua.nandagy.bean.module.StudyListData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenhua on 2/13/2017.
 * Email shenhuanet@126.com
 */
public class StudyDBDao extends BaseSQLiteDao<StudyListData> {

    private int type;

    public StudyDBDao(Context context, int type) {
        super(new StudyDBHelper(context, type));
        this.type = type;
    }

    public void addList(List<StudyListData> datas) {
        for (StudyListData list : datas) {
            add(list);
        }
    }

    @Override
    public long add(StudyListData item) {
        ContentValues cv = new ContentValues();
        cv.put("title", item.getTitle());
        cv.put("description", item.getDescription());
        cv.put("href", item.getHref());
        cv.put("time", item.getTime());
        cv.put("content", item.getContent());
        return dbHelper.getWritableDatabase().insert(StudyDBHelper.getTableName(type), null, cv);
    }

    /**
     * 删除表后重建表，使自增_id重新计数
     */
    @Override
    public void deleteTable() {
        String sql = "drop table if exists " + StudyDBHelper.getTableName(type) + ";";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sql);
        dbHelper.createDB(db);
        db.close();
    }

    @Override
    public void update(StudyListData item, int _id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("time", item.getTime());
        cv.put("content", item.getContent());
        db.update(StudyDBHelper.getTableName(type), cv, "_id =" + Integer.toString(_id), null);
        db.close();
    }

    @Override
    public List<StudyListData> listAll() {
        String sql = "select * from " + StudyDBHelper.getTableName(type) + ";";
        List<StudyListData> listDatasList = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                StudyListData datas = new StudyListData(cursor.getString(1), cursor.getString(3), cursor.getString(2));
                listDatasList.add(datas);
            }
            cursor.close();
            db.close();
            return listDatasList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public StudyListData query(int _id) {
        String sql = "select * from " + StudyDBHelper.getTableName(type) + " where _id = " + Integer.toString(_id);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(sql, null);
            StudyListData datas = null;
            while (cursor.moveToNext()) {
                datas = new StudyListData(cursor.getString(4), cursor.getString(5));
            }
            cursor.close();
            db.close();
            return datas;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
