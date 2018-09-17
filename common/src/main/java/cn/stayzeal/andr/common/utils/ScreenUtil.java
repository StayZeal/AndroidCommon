package cn.stayzeal.andr.common.utils;

import android.content.Context;
import android.provider.Settings;
import android.util.DisplayMetrics;


/**
 */
public class ScreenUtil {

    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    /**
     * 判断屏幕旋转开关是否开启
     *
     * @param context
     * @return
     */
    public static boolean isScreenAutoRotate(Context context) {
        int gravity = 0;
        try {
            gravity = Settings.System.getInt(context.getContentResolver(),
                    Settings.System.ACCELEROMETER_ROTATION);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        return gravity == 1;
    }

    public static DisplayMetrics getScreenSize() {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
//        int w_screen = dm.widthPixels;
//        int h_screen = dm.heightPixels;
        return dm;
    }
}
