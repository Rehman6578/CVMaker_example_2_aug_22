package com.example.resume;

/**
 * Created by Bilal on 12/28/2016.
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

@TargetApi(23)
public class Permissions {



    public static boolean hasStoragePermissions(Context context) {
        String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public static void getStoragePermissions(Context context){
        String[] permissions = { "android.permission.WRITE_EXTERNAL_STORAGE" };
        ((Activity)context).requestPermissions(permissions, 13);
    }





}