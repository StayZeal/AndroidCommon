package cn.stayzeal.andr.common.utils;

/**
 */
public class TimeCompare {

    public static boolean isTimeOut(String curTime, String classTime) {
        String[] curTimeList = curTime.split(":");
        String[] classTimeList = classTime.split(":");
        if (curTimeList.length == 2 && classTimeList.length == 2) {
            int nowHour = Integer.parseInt(curTimeList[0]);
            int classHour = Integer.parseInt(classTimeList[0]);
            int nowMinute = Integer.parseInt(curTimeList[1]);
            int classMinute = Integer.parseInt(classTimeList[1]);
            if (nowHour > classHour) {
                return true;
            } else if (nowHour < classHour) {
                return false;
            } else {
                if (nowMinute > classMinute) {
                    return true;
                } else if (nowMinute < classMinute) {
                    return false;
                } else {
                    return true;
                }
            }
        } else {
            return false;
        }
    }

}
