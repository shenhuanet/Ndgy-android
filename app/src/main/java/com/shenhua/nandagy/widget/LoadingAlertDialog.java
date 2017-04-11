package com.shenhua.nandagy.widget;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.widget.TextView;

import com.shenhua.nandagy.R;

/**
 * loading dialog
 * Created by shenhua on 6/21/2016.
 */
public class LoadingAlertDialog {

    private static LoadingAlertDialog sInatance = null;
    private AlertDialog alertDialog;

    public static LoadingAlertDialog getInstance(Context context) {
        if (sInatance == null) {
            synchronized (LoadingAlertDialog.class) {
                if (sInatance == null) {
                    sInatance = new LoadingAlertDialog(context);
                }
            }
        }
        return sInatance;
    }

    private LoadingAlertDialog(Context context) {
        alertDialog = new AlertDialog.Builder(context).create();
    }

    public void showLoadDialog(String text, boolean cancelable) {
        alertDialog.setCancelable(cancelable);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_loading);
            TextView tv = (TextView) window.findViewById(R.id.txt_dialog_message);
            tv.setText(text);
        }
    }

    public void dissmissLoadDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }
}
