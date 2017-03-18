package com.takeoffandroid.shimmercontactsview.utils;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;

import permissions.dispatcher.PermissionRequest;

/**
 * Created by f22labs on 18/03/17.
 */

public class Utils {


    public static boolean isDismissDialog(BottomSheetDialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            return true;
        }

        return false;
    }


    public static final void showSnack(View view, String message) {

        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).setAction("Done", new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        }).show();
    }


    public static boolean isReadContactsPermissionEnabled(Context context) {

        PackageManager pm = context.getPackageManager();
        int hasPerm = pm.checkPermission(
                Manifest.permission.READ_CONTACTS,
                context.getPackageName());

        return (hasPerm == PackageManager.PERMISSION_GRANTED) ;

    }



}
