package com.shenhua.lib.boxing.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * 作者：nodlee/1516lee@gmail.com
 * 时间：2016年12月29日
 * 说明：6.0及更高版本权限处理帮助类
 */
public class RuntimePermissionHelper {

    private static RuntimePermissionHelper sHelper;
    private String[] permissionGroup;

    private RuntimePermissionHelper(String[] permissionGroup) {
        this.permissionGroup = permissionGroup;
    }

    public static RuntimePermissionHelper getInstance(String[] permissionGroup) {
        if (sHelper == null) {
            sHelper = new RuntimePermissionHelper(permissionGroup);
        }
        return sHelper;
    }

    /**
     * 检查权限有无授予
     *
     * @param context
     */
    public void checkSelfPermission(Activity context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;

        if (context == null || !(context instanceof Activity)) {
            throw new IllegalArgumentException("参数错误");
        }

        // 主权限
        String mainPermission = permissionGroup[0];

        int permissionState = ContextCompat.checkSelfPermission(context, mainPermission);
        switch (permissionState) {
            case PackageManager.PERMISSION_GRANTED:
                Log.d("xxx", "权限已被授予");
                break;
            case PackageManager.PERMISSION_DENIED:
            default:
                boolean showShowRationale = ActivityCompat.shouldShowRequestPermissionRationale(context, mainPermission);
                if (showShowRationale) {
                    showRequestPermissionRationale(context);
                } else {
                    requestPermission(context);
                }
                break;
        }
    }

    /**
     * 请求权限
     *
     * @param activity
     */
    private void requestPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, permissionGroup, 0);
    }

    /**
     * 解释权限授予原因
     *
     * @param context
     */
    private void showRequestPermissionRationale(final Activity context) {
        new AlertDialog.Builder(context)
                .setTitle("权限说明")
                .setMessage("用户没有授权应用权限，无法正常使用")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.finish();
                    }
                })
                .setPositiveButton("授予", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermission(context);
                    }
                }).create().show();
    }
}
