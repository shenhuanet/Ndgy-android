package com.shenhua.nandagy.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;

import okhttp3.internal.Util;

/**
 * Created by shenhua on 4/6/2017.
 * Email shenhuanet@126.com
 */
public class CacheUrlUtils {

    private static CacheUrlUtils sInstance = null;
    private static final String PREF_JIAOWU_URL = "pref_url";

    public static CacheUrlUtils getInstance() {
        synchronized (CacheUrlUtils.class) {
            if (sInstance == null) {
                sInstance = new CacheUrlUtils();
            }
            return sInstance;
        }
    }

    public synchronized void setJiaowuMainUrl(Context context, String url) {
        SharedPreferences pref = context.getSharedPreferences(PREF_JIAOWU_URL, Context.MODE_PRIVATE);
        pref.edit().putString("main_url", url).apply();
    }

    public synchronized void setJiaowuExamScoreUrl(Context context, String url) {
        SharedPreferences pref = context.getSharedPreferences(PREF_JIAOWU_URL, Context.MODE_PRIVATE);
        pref.edit().putString("exam_score_url", url).apply();
    }

    public synchronized void setJiaowuGradeScoreUrl(Context context, String url) {
        SharedPreferences pref = context.getSharedPreferences(PREF_JIAOWU_URL, Context.MODE_PRIVATE);
        pref.edit().putString("grade_score_url", url).apply();
    }

    /**
     * 从缓存文件中获取html数据
     *
     * @param context context
     * @param charset charset
     * @return cache
     * @throws Exception
     */
    public String getJiaowuMainCache(Context context, String charset) throws Exception {
        SharedPreferences pref = context.getSharedPreferences(PREF_JIAOWU_URL, Context.MODE_PRIVATE);
        String url = pref.getString("main_url", null);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        String fileName = Util.md5Hex(url) + ".1";
        File file = new File(context.getExternalCacheDir() + "/myCache" + File.separator + fileName);
        FileInputStream fis = new FileInputStream(file);
        byte[] readBytes = new byte[fis.available()];
        fis.read(readBytes);
        fis.close();
        return new String(readBytes, charset);
    }

}
