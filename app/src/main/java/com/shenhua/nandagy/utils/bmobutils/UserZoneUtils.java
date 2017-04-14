package com.shenhua.nandagy.utils.bmobutils;

import android.content.Context;
import android.content.SharedPreferences;

import com.shenhua.nandagy.bean.bmobbean.UserZone;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 用户空间工具类
 * Created by shenhua on 9/10/2016.
 */
public class UserZoneUtils {

    private static final String PREF_USERZONE = "pref_user_zone";

    public static UserZoneUtils getInstance() {
        return new UserZoneUtils();
    }

    public void updateZoneStatis(String objid, String key, final int increment) {
        UserZone zone = new UserZone();
        zone.increment(key, increment);
        zone.update(objid, new UpdateListener() {
            @Override
            public void done(BmobException e) {
            }
        });
    }

    public synchronized void saveUserZone(Context context, UserZone userZone) {
        SharedPreferences pref = context.getSharedPreferences(PREF_USERZONE, Context.MODE_PRIVATE);
        pref.edit()
                .putString("objectId", userZone.getObjectId())
                .putString("depart", userZone.getDepart())
                .putInt("dynamic", userZone.getDynamic())
                .putString("dynamicStr", userZone.getDynamicStr())
                .putInt("exper", userZone.getExper())
                .putString("highSchool", userZone.getHighSchool())
                .putInt("level", userZone.getLevel())
                .putString("locate", userZone.getLocate())
                .putString("love", userZone.getLove())
                .putInt("mi", userZone.getMi())
                .putString("name", userZone.getName())
                .putString("qual", userZone.getQual())
                .putString("sign", userZone.getSign())
                .putString("birth", userZone.getBirth())
                .putString("userObjId", userZone.getUser() == null ? "" : userZone.getUser().getObjectId())
                .apply();
    }

    public UserZone getUserZone(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_USERZONE, Context.MODE_PRIVATE);
        UserZone userZone = new UserZone();
        userZone.setDepart(pref.getString("depart", null));
        userZone.setDynamic(pref.getInt("dynamic", 0));
        userZone.setDynamicStr(pref.getString("dynamicStr", null));
        userZone.setExper(pref.getInt("exper", 0));
        userZone.setHighSchool(pref.getString("highSchool", null));
        userZone.setLevel(pref.getInt("level", 0));
        userZone.setLocate(pref.getString("locate", null));
        userZone.setLove(pref.getString("love", null));
        userZone.setMi(pref.getInt("mi", 0));
        userZone.setName(pref.getString("name", null));
        userZone.setQual(pref.getString("qual", null));
        userZone.setSign(pref.getString("sign", null));
        userZone.setBirth(pref.getString("birth", null));
        userZone.setUser(UserUtils.getInstance().getBasicUser(context));
        return userZone;
    }
}
