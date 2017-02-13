package com.shenhua.nandagy.base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenhua on 2/13/2017.
 * Email shenhuanet@126.com
 */
public abstract class BaseSQLiteDao<T> {

    private BaseSQLiteOpenHelper dbHelper;
    private Context context;

    public BaseSQLiteDao(Context context, BaseSQLiteOpenHelper dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.createDB(db);
    }

    public void insertAll(List<T> ts, ContentValues cv) {
        for (T list : ts) {
            insert(setTableName(), null, cv);
        }
    }

    public abstract String setTableName();

    public long insert(String table, String nullColumnHack, ContentValues values) {
        long ret = 0;
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();
        try {
            ret = database.insert(table, nullColumnHack, values);
            database.setTransactionSuccessful();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
        return ret;
    }

    public <T> List<T> query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, Integer limit) {
        List<T> results = new ArrayList<>();
        Cursor cursor = null;
        try {
            if (limit != null) {
                cursor = dbHelper.getReadableDatabase().query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit + "");
            } else {
                cursor = dbHelper.getReadableDatabase().query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
            }
            results = queryResult(cursor);
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return results;
    }

    public <T> List<T> queryResult(Cursor cursor) {
        throw new RuntimeException("Please overwrite method.");
    }

    public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        int ret = 0;
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();
        try {
            ret = database.update(table, values, whereClause, whereArgs);
            database.setTransactionSuccessful();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
        return ret;
    }

    public int delete(String table, String whereClause, String[] whereArgs) {
        int ret = 0;
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();
        try {
            ret = database.delete(table, whereClause, whereArgs);
            database.setTransactionSuccessful();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
        return ret;
    }

    /**
     * 删除表后重建表，使自增_id重新计数
     */
    public void deleteAll() {
        String sql = "drop table if exists tb_listItem;";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sql);
        db.close();
        dbHelper.createDB(db);
    }
}
