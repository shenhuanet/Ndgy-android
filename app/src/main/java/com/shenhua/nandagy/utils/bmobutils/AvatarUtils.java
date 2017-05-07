package com.shenhua.nandagy.utils.bmobutils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.bmobbean.MyUser;
import com.shenhua.nandagy.bean.bmobbean.UserZone;

/**
 * Created by Shenhua on 5/7/2017.
 * Email:shenhuanet@126.com
 */
public class AvatarUtils {

    public static void loadUserAvatar(Context context, MyUser user, ImageView view) {
        if (user == null) {
            Glide.with(context).load(R.drawable.img_photo_man).centerCrop().into(view);
            return;
        }
        if (user.getAvatar() != null) {
            String url = user.getAvatar().getFileUrl();
            if (!TextUtils.isEmpty(url)) {
                Glide.with(context).load(url).centerCrop().into(view);
            } else {
                setErrorAvatar(context, user, view);
            }
        } else {
            setErrorAvatar(context, user, view);
        }
    }

    private static void setErrorAvatar(Context context, MyUser user, ImageView view) {
        boolean sex = user.getSex();
        Glide.with(context).load(sex ? R.drawable.img_photo_woman : R.drawable.img_photo_man)
                .centerCrop().into(view);
    }

    public static String getOtherUserAvatar(UserZone userZone) {
        try {
            if (userZone.getUser().getAvatar() == null) {
                return null;
            } else {
                String url = userZone.getUser().getAvatar().getFileUrl();
                if (!TextUtils.isEmpty(url)) {
                    return url;
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
