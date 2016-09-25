package com.shenhua.nandagy.utils.bmobutils;

import android.content.Context;
import android.content.SharedPreferences;

import com.shenhua.nandagy.bean.bmobbean.MyUser;

/**
 * 用户工具类，提供本地用户数据存储和访问, 全局用户数据统一访问入口，以及其他辅助功能
 * Created by shenhua on 9/9/2016.
 */
public class UserUtils {

    private static final String PREF_USER = "pref_user";
    private static UserUtils userUtils = new UserUtils();

    public static UserUtils getInstance() {
        return userUtils;
    }

    public MyUser getUser(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE);
        String userid = pref.getString("userid", null);
        String username = pref.getString("username", null);
        if (username != null && userid != null) {
            return new MyUser(userid, username);
        }
        return null;
    }

    private MyUser getBanding(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE);
        String nameNum = pref.getString("name_num", null);
        String name = pref.getString("name", null);
        String nameId = pref.getString("name_id", null);
        if (nameNum != null && nameId != null) {
            return new MyUser(nameNum, nameId, name);
        }
        return null;
    }

    public synchronized void setUser(Context context, MyUser user) {
        SharedPreferences pref = context.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE);
        pref.edit()
                .putString("userid", user.getUserId())
                .putString("username", user.getUserName())
                .putString("nick", user.getNick())
                .putString("name_num", user.getName_num())
                .putString("name", user.getName())
                .putString("name_id", user.getName_id())
                .putString("info", user.getInfo())
                .putString("url_photo", user.getUrl_photo())
                .putString("email", user.geteMail())
                .putString("phone", user.getPhone())
                .putBoolean("gender", user.getSex())
                .putString("userzoneobjid", user.getUserZoneObjID())
                .apply();
    }

    public MyUser getUserInfo(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE);
        return new MyUser(pref.getString("email", ""), pref.getString("info", ""), pref.getString("name", ""),
                pref.getString("name_id", ""), pref.getString("name_num", ""), pref.getString("nick", ""),
                pref.getString("phone", ""), pref.getBoolean("gender", false), pref.getString("url_photo", ""),
                pref.getString("userid", ""), pref.getString("username", ""), pref.getString("userzoneobjid", ""));
    }

    public synchronized void updateUserInfo(Context context, String key, String value) {
        SharedPreferences pref = context.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE);
        pref.edit().putString(key, value).apply();
    }

    public synchronized void updateUserInfo(Context context, String key, boolean gender) {
        SharedPreferences pref = context.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE);
        pref.edit().putBoolean(key, gender).apply();
    }

    // 用户登出
    public synchronized void logout(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE);
        pref.edit().clear().apply();
    }

    /**
     * 用户是否登录
     *
     * @param context 上下文
     * @return true 已登录，flase 未登录
     */
    public boolean isLogin(Context context) {
        return getUser(context) != null;
    }

    /**
     * 用户是否记忆到教务
     *
     * @param context 上下文
     * @return true 已记忆，flase 未记忆
     */
    public boolean isBinding(Context context) {
        return getBanding(context) != null;
    }

}
