package com.shenhua.nandagy;

import android.app.Application;
import android.content.Context;

/**
 * Application
 * Created by shenhua on 8/29/2016.
 */
public class App extends Application {

    private static Context mApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = this;
//        CrashReport.initCrashReport(getApplicationContext(), "9096160264", true);
    }

    public static Context getContext() {
        return mApplicationContext;
    }
}
