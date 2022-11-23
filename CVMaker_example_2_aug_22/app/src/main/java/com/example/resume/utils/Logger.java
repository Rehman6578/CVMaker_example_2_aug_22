package com.example.resume.utils;

import android.util.Log;

/**
 * Project Name : CVMaker
 * Created by   : Ummer Siddique
 * Created on   : July 21, 2017
 * Created at   : 10:48 AM
 */

public class Logger {

    private static boolean LOG_FLAG = true;

    public static void i(String TAG, String msg) {
        if (LOG_FLAG)
            Log.i(TAG, msg);
    }

    public static void i(String TAG, String msg, Throwable tr) {
        if (LOG_FLAG)
            Log.i(TAG, msg, tr);
    }

    public static void d(String TAG, String msg) {
        if (LOG_FLAG)
            Log.d(TAG, msg);
    }

    public static void d(String TAG, String msg, Throwable tr) {
        if (LOG_FLAG)
            Log.d(TAG, msg, tr);
    }
}
