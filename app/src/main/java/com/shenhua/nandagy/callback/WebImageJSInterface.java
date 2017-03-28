package com.shenhua.nandagy.callback;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.shenhua.commonlibs.callback.JSInterface;

/**
 * Created by shenhua on 3/28/2017.
 * Email shenhuanet@126.com
 */
public class WebImageJSInterface extends JSInterface {

    public WebImageJSInterface(Context context) {
        super(context);
    }

    @Override
    @JavascriptInterface // must add
    public void openImage(String url) {
        Log.d("JSInterface", "openImage: ------");
        Toast.makeText(context, url, Toast.LENGTH_SHORT).show();
    }
}
