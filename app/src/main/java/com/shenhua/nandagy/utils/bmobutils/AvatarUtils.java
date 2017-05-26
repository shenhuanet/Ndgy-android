package com.shenhua.nandagy.utils.bmobutils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
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

    /**
     * 未使用
     */
    private static void displayUserAvatar(Context context, UserZone userZone, ImageView view) {
        String result = getOtherUserAvatarUrl(userZone);
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

    public static String getOtherUserAvatarUrl(UserZone userZone) {
        if (userZone.getUser() == null) {
            return "error";
        } else if (userZone.getUser().getAvatar() == null) {
            if (userZone.getUser().getSex()) {
                return "woman";
            } else {
                return "man";
            }
        } else {
            return userZone.getUser().getAvatar().getFileUrl();
        }
    }

    public static void loadUserNick(UserZone userZone, TextView view) {
        try {
            String nick = userZone.getUser().getNick();
            Log.d("shenhuaLog -- " + AvatarUtils.class.getSimpleName(), "loadUserNick: " + nick);
            if (TextUtils.isEmpty(nick)) {
                view.setText("未设置昵称");
            } else {
                view.setText(nick);
            }
        } catch (Exception e) {
            view.setText("未设置昵称");
        }
    }

    public static void loadUserNick(MyUser user, TextView view) {
        try {
            String nick = user.getNick();
            if (TextUtils.isEmpty(nick)) {
                view.setText("未设置昵称");
            } else {
                view.setText(nick);
            }
        } catch (Exception e) {
            view.setText("未设置昵称");
        }
    }
}
