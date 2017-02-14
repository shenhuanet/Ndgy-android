package com.shenhua.nandagy.base;

import java.util.List;

/**
 * Created by shenhua on 2/13/2017.
 * Email shenhuanet@126.com
 */
public abstract class BaseSQLiteDao<T> {

    protected BaseSQLiteOpenHelper dbHelper;

    /**
     * must add egg: super(new StudyDBHelper(context));
     *
     * @param dbHelper dbHelper that extents BaseSQLiteOpenHelper.
     */
    public BaseSQLiteDao(BaseSQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public abstract long add(T t);

    public abstract void deleteTable();

    public abstract void update(T t, int _id);

    public abstract List<T> listAll();

    public abstract T query(int _id);

}
