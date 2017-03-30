package com.shenhua.nandagy;

import android.app.Application;
import android.content.Context;

import com.shenhua.nandagy.service.BombService;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;

/**
 * Application
 * Created by shenhua on 8/29/2016.
 */
public class App extends Application {

    private static Context mApplicationContext;

    public static final boolean DEBUG_MODE = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, BombService.APP_KEY);
        mApplicationContext = this;
        if (DEBUG_MODE) {
            LeakCanary.install(this);
            CrashReport.initCrashReport(getApplicationContext(), "9096160264", true);
        } else {
            CrashReport.initCrashReport(getApplicationContext(), "9096160264", false);
        }
    }

    public static Context getContext() {
        return mApplicationContext;
    }

}
