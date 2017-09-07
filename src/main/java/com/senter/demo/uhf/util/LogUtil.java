package com.senter.demo.uhf.util;

import android.util.Log;

public class LogUtil {

    private static boolean ENABLE_LOG = true;
    private static String TAG = "12";
    public static void e(String msg) {
        if (ENABLE_LOG) {
            Log.e(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (ENABLE_LOG) {
            Log.w(TAG, msg);
        }
    }

    public static void i(String msg) {
        if (ENABLE_LOG) {
            Log.i(TAG, msg);
        }
    }

    public static void v(String msg) {
        if (ENABLE_LOG) {
            Log.v(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (ENABLE_LOG) {
            Log.d(TAG, msg);
        }
    }


}
