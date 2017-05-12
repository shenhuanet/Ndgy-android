package com.shenhua.nandagy.utils.bmobutils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

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
        try {
            boolean sex = user.getSex();
            Glide.with(context).load(sex ? R.drawable.img_photo_woman : R.drawable.img_photo_man)
                    .centerCrop().into(view);
        } catch (Exception e) {
            Glide.with(context).load(R.drawable.img_photo_woman).centerCrop().into(view);
        }
    }

    public static void displayUserAvatar(Context context, UserZone userZone, ImageView view) {
        String result = getOtherUserAvatar(userZone);
        switch (result) {
            case "error":
            case "man":
                view.setImageResource(R.drawable.img_photo_man);
                break;
            case "woman":
                view.setImageResource(R.drawable.img_photo_woman);
                break;
            default:
                Glide.with(context).load(result).centerCrop().into(view);
                break;
        }
    }

    public static String getOtherUserAvatar(UserZone userZone) {
        try {
            if (userZone.getUser().getAvatar() == null) {
                return "error";
            } else {
                String url = userZone.getUser().getAvatar().getFileUrl();
                if (!TextUtils.isEmpty(url)) {
                    return url;
                } else {
                    if (userZone.getUser().getSex()) {
                        return "woman";
                    } else {
                        return "man";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public static void loadUserNick(UserZone userZone, TextView view) {
        try {
            String nick = userZone.getUser().getNick();
            view.setText(nick);
        } catch (Exception e) {
            e.printStackTrace();
            view.setText("佚名");
        }
    }
}
