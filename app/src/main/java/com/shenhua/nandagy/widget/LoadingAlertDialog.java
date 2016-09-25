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

    private static AlertDialog alertDialog;

    public static void showLoadDialog(Context context, String text, boolean cancelable) {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setCancelable(cancelable);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_loading);
        TextView tv = (TextView) window.findViewById(R.id.txt_dialog_message);
        tv.setText(text);
    }

    public static void dissmissLoadDialog() {
        if (alertDialog != null && alertDialog.isShowing())
            alertDialog.dismiss();
    }
}
