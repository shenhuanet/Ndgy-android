package com.shenhua.nandagy.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 设置相关的工具类，非wifi下不加载图片，推送相关配置
 * Created by Shenhua on 9/17/2016.
 */
public class SettingUtils {

    private static final String PREF_SETTING = "pref_setting";

    public static SettingUtils getInstance() {
        return new SettingUtils();
    }

    public Config getConfig(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_SETTING, Context.MODE_PRIVATE);
        boolean nanWifi = pref.getBoolean("nanWifi", false);
        boolean nanPush = pref.getBoolean("nanPush", true);
        return new Config(nanWifi, nanPush);
    }

    public synchronized void setConfig(Context context, String key, boolean value) {
        SharedPreferences pref = context.getSharedPreferences(PREF_SETTING, Context.MODE_PRIVATE);
        pref.edit().putBoolean(key, value).apply();
    }

    public class Config {
        private boolean nanWifi;
        private boolean nanPush;

        public Config(boolean nanWifi, boolean nanPush) {
            this.nanWifi = nanWifi;
            this.nanPush = nanPush;
        }

        public boolean isNanPush() {
            return nanPush;
        }

        public void setNanPush(boolean nanPush) {
            this.nanPush = nanPush;
        }

        public boolean isNanWifi() {
            return nanWifi;
        }

        public void setNanWifi(boolean nanWifi) {
            this.nanWifi = nanWifi;
        }
    }

}
