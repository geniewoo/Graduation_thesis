package com.sungwoo.boostcamp.photoalbumfilter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * Created by psw10 on 2017-10-17.
 */

public class CommonUtils {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 789;
    private static final String TAG = CommonUtils.class.getSimpleName();

    public static boolean CheckRead_External_Permission(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                String[] permissionString = {Manifest.permission.READ_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions((Activity)context, permissionString, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showReadPermissionDialog("External storage", context, Manifest.permission.READ_EXTERNAL_STORAGE);
                }
            } else {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    public static boolean CheckWrite_External_Permission(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                String[] permissionString = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions((Activity)context, permissionString, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    showWritePermissionDialog("External storage", context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
            } else {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    private static void showReadPermissionDialog(final String msg, final Context context, final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{permission},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    private static void showWritePermissionDialog(final String msg, final Context context, final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{permission},
                                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    public static void showDeleteDialog(final Context context, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setTitle("경고");
        alertBuilder.setMessage("정말 삭제 하시겠습니까 ?");
        alertBuilder.setPositiveButton(android.R.string.yes, onClickListener);
        alertBuilder.setNegativeButton(android.R.string.no, null);
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    public static void showCheckRemainingDialog(final Context context, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setMessage("지금 삭제 하지 않은 사진들을 다음에는 자동으로 평가하지 않으시겠습니까?");
        alertBuilder.setPositiveButton(android.R.string.yes, onClickListener);
        alertBuilder.setNegativeButton(android.R.string.no, null);
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
}
