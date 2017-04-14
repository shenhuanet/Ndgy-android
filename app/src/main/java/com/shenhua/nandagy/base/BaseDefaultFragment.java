package com.shenhua.nandagy.base;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

/**
 * Created by shenhua on 4/11/2017.
 * Email shenhuanet@126.com
 */
public class BaseDefaultFragment extends Fragment {

    public void toast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void toast(@StringRes int resId) {
        Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT).show();
    }

    public void sceneTransitionTo(Intent intent, int requestCode, View view, int viewId, String sharedElementName) {
        Activity activity = getActivity();
        if (activity != null) {
            if (Build.VERSION.SDK_INT > 21) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity,
                        view.findViewById(viewId), sharedElementName);
                startActivityForResult(intent, requestCode, options.toBundle());
            } else {
                ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(view,
                        view.getWidth() / 2, view.getHeight() / 2, 0, 0);
                ActivityCompat.startActivityForResult(activity, intent, requestCode, options.toBundle());
            }
        }
    }
}
