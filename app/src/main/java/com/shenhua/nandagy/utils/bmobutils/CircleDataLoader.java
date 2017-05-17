package com.shenhua.nandagy.utils.bmobutils;

import android.content.Context;

import com.shenhua.nandagy.bean.bmobbean.UserZone;
import com.shenhua.nandagy.database.GreatHateFav;
import com.shenhua.nandagy.database.GreatHateFavDao;

import java.util.List;

/**
 * Created by shenhua on 5/12/2017.
 * Email shenhuanet@126.com
 */
public class CircleDataLoader {

    private static CircleDataLoader sInstance = null;

    public static CircleDataLoader getInstance() {
        synchronized (CircleDataLoader.class) {
            if (sInstance == null) {
                sInstance = new CircleDataLoader();
            }
            return sInstance;
        }
    }

    public boolean isFav(Context mContext, String pObj, GreatHateFavDao dao) {
        try {
            UserZone uz = UserZoneUtils.getInstance().getUserZone(mContext);
            if (uz == null) {
                return false;
            } else {
                List<GreatHateFav> fav = dao.queryBuilder().where(
                        GreatHateFavDao.Properties.UserzoneId.eq(uz.getObjectId()), GreatHateFavDao.Properties.PostId.eq(pObj)
                ).build().list();
                return fav != null && fav.size() != 0 && fav.get(0).getIsFav();
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isGreat(Context mContext, String pObj, GreatHateFavDao dao) {
        try {
            UserZone uz = UserZoneUtils.getInstance().getUserZone(mContext);
            if (uz == null) {
                return false;
            } else {
                List<GreatHateFav> fav = dao.queryBuilder().where(
                        GreatHateFavDao.Properties.UserzoneId.eq(uz.getObjectId()), GreatHateFavDao.Properties.PostId.eq(pObj)
                ).build().list();
                return fav != null && fav.size() != 0 && fav.get(0).getIsGreat();
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isHate(Context mContext, String pObj, GreatHateFavDao dao) {
        try {
            UserZone uz = UserZoneUtils.getInstance().getUserZone(mContext);
            if (uz == null) {
                return false;
            } else {
                List<GreatHateFav> fav = dao.queryBuilder().where(
                        GreatHateFavDao.Properties.UserzoneId.eq(uz.getObjectId()), GreatHateFavDao.Properties.PostId.eq(pObj)
                ).build().list();
                return fav != null && fav.size() != 0 && fav.get(0).getIsHate();
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static String formatNumber(Integer integer) {
        if (integer == null) {
            return "0";
        }
        return "" + integer;
    }
}
