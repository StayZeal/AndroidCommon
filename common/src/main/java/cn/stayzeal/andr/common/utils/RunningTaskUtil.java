package cn.stayzeal.andr.common.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

public class RunningTaskUtil {
    public static boolean isTopOrBottomActivity(Context context, String classname) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> lists = manager.getRunningTasks(10);
        for(int i = 0; i < lists.size(); i++){
            String baseAct = lists.get(i).baseActivity.getClassName();
            String topAct = lists.get(i).topActivity.getClassName();
            if (baseAct.equals(classname) || topAct.equals(classname)){
                return true;
            }
        }
        return false;
//        ComponentName name = manager.getRunningTasks(10).get(0).topActivity;
//        if (classname.equals(name.getClassName())) {
//            return true;
//        } else {
//            return false;
//        }
    }
}
