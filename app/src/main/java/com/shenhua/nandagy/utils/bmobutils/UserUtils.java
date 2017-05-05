package com.shenhua.nandagy.utils.bmobutils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.shenhua.nandagy.bean.bmobbean.MyUser;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 用户工具类，提供本地用户数据存储和访问, 全局用户数据统一访问入口，以及其他辅助功能
 * Created by shenhua on 9/9/2016.
 */
public class UserUtils {

    private static final String PREF_USER = "pref_user";
    private static UserUtils sInstance = null;

    public static UserUtils getInstance() {
        synchronized (UserUtils.class) {
            if (sInstance == null) {
                sInstance = new UserUtils();
            }
            return sInstance;
        }
    }

    public synchronized void saveUser(Context context, MyUser user) {
        SharedPreferences pref = context.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE);
        pref.edit()
                .putString("objectId", user.getObjectId())
                .putString("username", user.getUsername())
                .putString("nick", user.getNick())
                .putString("name_num", user.getName_num())
                .putString("name", user.getName())
                .putString("name_id", user.getName_id())
                .putString("info", user.getInfo())
                .putString("url_photo", user.getAvatar() == null ? "" : user.getAvatar().getFileUrl())
                .putString("email", user.getEmail())
                .putString("phone", user.getMobilePhoneNumber())
                .putBoolean("gender", user.getSex())
                .putString("zoneObjId", user.getUserZone() == null ? "" : user.getUserZone().getObjectId())
                .apply();
    }

    public synchronized void updateUserInfo(Context context, String key, String value) {
        SharedPreferences pref = context.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE);
        pref.edit().putString(key, value).apply();
    }

    public synchronized void updateUserInfo(Context context, String key, boolean gender) {
        SharedPreferences pref = context.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE);
        pref.edit().putBoolean(key, gender).apply();
    }

    public synchronized void setBinding(Context context, String[] numId) {
        SharedPreferences pref = context.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE);
        pref.edit().putString("name_num", numId[0]).putString("name_id", numId[1]).apply();
    }

    /**
     * 用户登出
     *
     * @param context context
     */
    public synchronized void logout(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE);
        pref.edit().clear().apply();
    }

    /**
     * 用户是否登录
     *
     * @return true 已登录，flase 未登录
     */
    public boolean isLogin() {
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        return myUser != null;
    }

    /**
     * 用户是否更新过教务信息
     *
     * @param context 上下文
     * @return true 已更新，flase 未更新
     */
    public boolean isBinding(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE);
        String nameNum = pref.getString("name_num", null);
        String nameId = pref.getString("name_id", null);
        return !(TextUtils.isEmpty(nameNum) && TextUtils.isEmpty(nameId));
    }

    /**
     * 用户是否创建了空间
     *
     * @return true是, false否
     */
    public boolean isCreateZone() {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        return user.getUserZone() != null && !TextUtils.isEmpty(user.getUserZone().getObjectId());
    }

    /**
     * 获取用户空间objeId
     *
     * @param context context
     * @return null is not
     */
    public String getUserzoneObjId(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE);
        return pref.getString("zoneObjId", null);
    }

    /**
     * 获取基本用户信息，用于空间跳转
     *
     * @param context context
     * @return MyUser
     */
    public MyUser getBasicUser(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE);
        MyUser myUser = new MyUser();
        myUser.setNick(pref.getString("nick", null));
        myUser.setSex(pref.getBoolean("gender", false));
        myUser.setAvatar(new BmobFile("fileName", "group", pref.getString("url_photo", null)));
        return myUser;
    }

}
