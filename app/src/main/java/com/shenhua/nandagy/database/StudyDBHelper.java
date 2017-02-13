package com.shenhua.nandagy.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shenhua on 2/13/2017.
 * Email shenhuanet@126.com
 */
public class StudyDBHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 2;
    public static final String TABLE_NAME = "table_study";
    // SQLite没有单独的布尔存储类型，它使用INTEGER作为存储类型，0为false，1为true
    public static final String TYPE_NULL = " null ";// 值是NULL
    public static final String TYPE_INT = " integer ";// 值是有符号整形，根据值的大小以1,2,3,4,6或8字节存放
    public static final String TYPE_STRING = " text ";// 值是文本字符串，使用数据库编码（UTF-8，UTF-16BE或者UTF-16LE）存放
    public static final String TYPE_REAL = " real ";// 值是浮点型值，以8字节IEEE浮点数存放
    public static final String TYPE_BLOB = " blob ";// 只是一个数据块，完全按照输入存放（即没有准换）

    public StudyDBHelper(Context context) {
        super(context, TABLE_NAME, null, DB_VERSION);
        System.out.println("shenhua sout:" + "StudyDBHelper.init");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String sql = buildCreateSQL(
                    new String[]{
                            "title", "description", "href", "time", "content"
                    }, new String[]{
                            TYPE_STRING, TYPE_STRING, TYPE_STRING, TYPE_STRING, TYPE_STRING
                    });
            System.out.println("shenhua sout:222:" + sql);
            db.execSQL(sql);
            System.out.println("shenhua sout:" + "创建成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private String buildCreateSQL(String[] columns, String[] types) throws Exception {
        if (columns.length <= 0) return "";
        if (columns.length != types.length) {
            throw new Exception("please sure the columns size is same as types...");
        }
        String sql = "create table " + TABLE_NAME + "(_id integer primary key autoincrement,";
        for (int i = 0; i < columns.length; i++) {
            sql = sql + columns[i] + types[i] + ",";
        }
        StringBuffer sb = new StringBuffer(sql);
        sb.deleteCharAt(sql.length() - 1);
        sb = sb.append(");");
        System.out.println("shenhua sout:" + sb);
        return sb.toString();
    }
}
