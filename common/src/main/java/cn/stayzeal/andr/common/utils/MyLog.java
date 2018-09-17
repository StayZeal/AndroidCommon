package cn.stayzeal.andr.common.utils;

import android.util.Log;


public class MyLog {

    public static void init(boolean isDebug) {
        IS_DEBUG = isDebug;
    }


    private static boolean IS_DEBUG;
    private static final int STAGE_I = 1;

    public static void i(String msg) {
        i("test", msg);

    }

    public static void i(String tag, String msg) {
        if (IS_DEBUG == true) {
            Log.i(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (IS_DEBUG == true) {
            Log.e(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (IS_DEBUG == true) {
            Log.w(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (IS_DEBUG == true) {
            Log.d(tag, msg);
        }
    }

}
