package com.shenhua.nandagy;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Application
 * Created by shenhua on 8/29/2016.
 */
public class App extends Application {

    private static Context mApplicationContext;

    public static final boolean DEBUG_MODE = true;

    @Override
    public void onCreate() {
        super.onCreate();
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
