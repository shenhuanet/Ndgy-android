package com.shenhua.nandagy.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shenhua on 2/13/2017.
 * Email shenhuanet@126.com
 */
public abstract class BaseSQLiteOpenHelper extends SQLiteOpenHelper {

    // SQLite没有单独的布尔存储类型，它使用INTEGER作为存储类型，0为false，1为true
    public static final String TYPE_NULL = " null ";// 值是NULL
    public static final String TYPE_INT = " integer ";// 值是有符号整形，根据值的大小以1,2,3,4,6或8字节存放
    public static final String TYPE_STRING = " text ";// 值是文本字符串，使用数据库编码（UTF-8，UTF-16BE或者UTF-16LE）存放
    public static final String TYPE_REAL = " real ";// 值是浮点型值，以8字节IEEE浮点数存放
    public static final String TYPE_BLOB = " blob ";// 只是一个数据块，完全按照输入存放（即没有准换）

    private String tableName;

    public BaseSQLiteOpenHelper(Context context, String dbName, String tableName, int version) {
        super(context, dbName, null, version);
        this.tableName = tableName;
    }

    public abstract void createDB(SQLiteDatabase db);

    public abstract void upgradeDB(SQLiteDatabase db, int oldVersion, int newVersion);

    public String buildCreateSQL(String[] columns, String[] types) throws Exception {
        if (columns.length <= 0) return "";
        if (columns.length != types.length) {
            throw new Exception("please sure the columns size is same as types...");
        }
        String sql = "create table " + tableName + "(_id integer primary key autoincrement,";
        for (int i = 0; i < columns.length; i++) {
            sql = sql + columns[i] + types[i] + ",";
        }
        StringBuffer sb = new StringBuffer(sql);
        sb.deleteCharAt(sql.length() - 1);
        sb = sb.append(");");
        System.out.println("shenhua sout:" + sb);
        return sb.toString();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        upgradeDB(db, oldVersion, newVersion);
    }

}
