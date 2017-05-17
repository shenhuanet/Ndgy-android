package com.shenhua.nandagy;

import android.app.Application;
import android.content.Context;

import com.shenhua.nandagy.service.BmobService;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;

/**
 * Application
 * Created by shenhua on 8/29/2016.
 */
public class App extends Application {

    private static Context mApplicationContext;

    public static final boolean DEBUG_MODE = true;// 发布时更改版本号

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, BmobService.APP_KEY);
        BmobInstallation.getCurrentInstallation().save();
        mApplicationContext = this;
        CrashReport.initCrashReport(getApplicationContext(), "9096160264", DEBUG_MODE);
        if (DEBUG_MODE) {
            LeakCanary.install(this);
        }
    }

    public static Context getContext() {
        return mApplicationContext;
    }

}
