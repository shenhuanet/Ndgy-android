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

    // {"_id":"0001","_result":"OK","_title":"\u6807\u9898","_content":"hello world","_date":"2017-04-10"}
}
