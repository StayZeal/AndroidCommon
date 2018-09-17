package cn.stayzeal.andr.common.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;


/**
 * Created by Vinctor on 16/4/14.
 */
public class ToastUtil {

    private static Context mContext;

    public static final int TOASTUTIL_SHORT = Toast.LENGTH_SHORT;
    public static final int TOASTUTIL_LONG = Toast.LENGTH_LONG;
    public static final int TOASTUTIL_CENTER = Gravity.CENTER;

    public static void show(String msg){
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static void show(String msg, int duration) {

        if (duration == TOASTUTIL_LONG) {
            Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
        }else if(duration == TOASTUTIL_SHORT){
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static Toast show(String msg, int duration, int gravity){
        Toast toast;
        if (duration == TOASTUTIL_LONG) {
            toast = Toast.makeText(mContext,msg, Toast.LENGTH_LONG);
            if(gravity == TOASTUTIL_CENTER){
                toast.setGravity(Gravity.CENTER,0,0);
            }
            toast.show();
        }else{  // if(duration == TOASTUTIL_SHORT){
            toast = Toast.makeText(mContext,msg, Toast.LENGTH_SHORT);
            if(gravity == TOASTUTIL_CENTER){
                toast.setGravity(Gravity.CENTER,0,0);
            }
            toast.show();
        }
        return toast;
    }

    public static void show(int stringId) {
        show(mContext.getString(stringId));
    }

    public static void init(Context mContext) {
        ToastUtil.mContext = mContext;
    }
}
