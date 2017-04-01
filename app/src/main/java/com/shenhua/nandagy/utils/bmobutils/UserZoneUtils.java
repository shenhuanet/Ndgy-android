package com.shenhua.nandagy.utils.bmobutils;

import com.shenhua.nandagy.bean.bmobbean.UserZone;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 用户空间工具类
 * Created by shenhua on 9/10/2016.
 */
public class UserZoneUtils {

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
}
