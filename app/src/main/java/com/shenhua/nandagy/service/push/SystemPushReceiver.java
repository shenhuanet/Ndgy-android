package com.shenhua.nandagy.service.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cn.bmob.push.PushConstants;

/**
 * 用于系统间的系统推送服务
 * Created by Shenhua on 4/9/2017.
 * Email:shenhuanet@126.com
 */
public class SystemPushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
            Log.d("bmob", "客户端收到推送内容：" + intent.getStringExtra("msg"));
        }
    }

    /*
     * {
     "aps": {
     "sound": "cheering.caf",
     "alert": "这个是通知栏上显示的内容",
     "badge": 0
     },
     "xx" : "json的key-value对，你可以根据情况添加更多的，客户端进行解析获取",
     }
     */

}
