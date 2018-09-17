package cn.stayzeal.andr.common.utils;

import android.graphics.Color;
import android.text.TextUtils;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {

    private static final String TAG = "DateUtil";
    public static String[] weekName = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    /**
     * 判断是否是同一天
     *
     * @param date1
     * @param date2
     * @return
     */

    public static final int NUMBER_MON = 1;
    public static final int NUMBER_TUE = 2;
    public static final int NUMBER_WED = 3;
    public static final int NUMBER_THU = 4;
    public static final int NUMBER_FRI = 5;
    public static final int NUMBER_SAT = 6;
    public static final int NUMBER_SUN = 7;

    private static final SimpleDateFormat sdfYMDHM = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
    private static final SimpleDateFormat sdfYMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    private static final SimpleDateFormat sdfHMS = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
    private static final SimpleDateFormat sdfHM = new SimpleDateFormat("HH:mm", Locale.CHINA);
    private static final SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    private static final SimpleDateFormat sdfYMD_Dot = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
    private static final SimpleDateFormat sdfE = new SimpleDateFormat("E", Locale.CHINA);
    private static final SimpleDateFormat numberYMD = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
    private static final SimpleDateFormat nyrsf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.CHINA);
    private static final SimpleDateFormat sdfYMDHM_Dot = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.CHINA);
    private static final SimpleDateFormat nyr = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
    private static final SimpleDateFormat sdfYR = new SimpleDateFormat("M月d日", Locale.CHINA);

    public static boolean isSameDay(Date date1, Date date2) {

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);
        return isSameDate;
    }


    public static boolean isYesterday(long millis) {
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);
        Calendar dt = Calendar.getInstance();
        dt.setTimeInMillis(millis);

        return ((yesterday.get(Calendar.YEAR) == dt.get(Calendar.YEAR)) &&
                (yesterday.get(Calendar.DAY_OF_YEAR) == dt.get(Calendar.DAY_OF_YEAR)));
    }

    public static String getYMDHMSTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        Date date = new Date((Long.parseLong(time)) * 1000);
        String formattedTime = sdfYMDHMS.format(date);
        return formattedTime;
    }

    public static String getDate(String timestamp, SimpleDateFormat sdf) {
        if (TextUtils.isEmpty(timestamp)) {
            return "";
        }
        Date date = new Date((Long.parseLong(timestamp)) * 1000);
        String formattedTime = sdf.format(date);
        return formattedTime;
    }


    public static String getYMDHMTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        Date date = new Date((Long.parseLong(time)) * 1000);
        String formattedTime = sdfYMDHM.format(date);
        return formattedTime;
    }

    public static String getHMSTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        Date date = new Date((Long.parseLong(time)) * 1000);
        String formattedTime = sdfHMS.format(date);
        return formattedTime;
    }

    public static String getYMDTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        Date date = new Date((Long.parseLong(time)) * 1000);
        String formattedTime = sdfYMD.format(date);
        return formattedTime;
    }

    public static String getDotYMDTime(long tsSecond) {
        Date date = new Date(tsSecond * 1000);
        String formattedTime = sdfYMD_Dot.format(date);
        return formattedTime;
    }

    public static String getDotYMDTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        Date date = new Date((Long.parseLong(time)) * 1000);
        String formattedTime = sdfYMD_Dot.format(date);
        return formattedTime;
    }

    public static String getNYRTime(long tsSecond) {
        Date date = new Date(tsSecond * 1000);
        String formattedTime = nyr.format(date);
        return formattedTime;
    }

    public static String getYRTime(long tsSecond) {
        Date date = new Date(tsSecond * 1000);
        String formattedTime = sdfYR.format(date);
        return formattedTime;
    }

    public static String getYMDTime(long time) {
        Date date = new Date(time);
        String formattedTime = sdfYMD.format(date);
        return formattedTime;
    }

    public static String getHMTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        Date date = new Date((Long.parseLong(time)) * 1000);
        String formattedTime = sdfHM.format(date);
        return formattedTime;
    }

    public static String getDotYMDHMTime(long tsSecond) {
        Date date = new Date(tsSecond * 1000);
        String formattedTime = sdfYMDHM_Dot.format(date);
        return formattedTime;
    }

    public static boolean isToday(String time) {

        if (TextUtils.isEmpty(time)) {
            return false;
        }
        Date date = new Date((Long.parseLong(time)) * 1000);
        Date today = new Date();

        return isSameDay(date, today);

    }

    public static String getTodayYMDTime() {
        Date date = new Date();
        String formattedTime = numberYMD.format(date);
        return formattedTime;
    }

    public static String getYMDTime(Date date) {
        String formattedTime = numberYMD.format(date);
        return formattedTime;
    }

    public static int getWeekday(String time) {
        if (TextUtils.isEmpty(time)) {
            return -1;
        }
        Date date = null;
        try {
            date = sdfYMD.parse(sdfYMD.format(new Date((Long.parseLong(time)) * 1000)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int weekday = date.getDay();
        if (weekday == 1) {
            return NUMBER_MON;
        } else if (weekday == 2) {
            return NUMBER_TUE;
        } else if (weekday == 3) {
            return NUMBER_WED;
        } else if (weekday == 4) {
            return NUMBER_THU;
        } else if (weekday == 5) {
            return NUMBER_FRI;
        } else if (weekday == 6) {
            return NUMBER_SAT;
        } else if (weekday == 0) {
            return NUMBER_SUN;
        } else {
            return NUMBER_MON;
        }
    }

    public static int getWeekday(Date date) {

        int weekday = date.getDay();
        if (weekday == 1) {
            return NUMBER_MON;
        } else if (weekday == 2) {
            return NUMBER_TUE;
        } else if (weekday == 3) {
            return NUMBER_WED;
        } else if (weekday == 4) {
            return NUMBER_THU;
        } else if (weekday == 5) {
            return NUMBER_FRI;
        } else if (weekday == 6) {
            return NUMBER_SAT;
        } else if (weekday == 0) {
            return NUMBER_SUN;
        } else {
            return NUMBER_MON;
        }
    }

    public static void getFormattedDate(String gTime, String nTime, TextView tv) {
        String now = getYMDTime(nTime);
        String get = getYMDTime(gTime);
        if (now.equals(get)) {
            tv.setTextColor(Color.parseColor("#d64e14"));
            long nowMs = Long.valueOf(nTime);
            long getMs = Long.valueOf(gTime);
            long differ = nowMs - getMs;
            if (differ < 3600L) {
                tv.setText("刚刚");
            } else {
                int hours = (int) differ / 60 / 60;
                tv.setText(hours + "小时前");
            }
        } else {
            tv.setTextColor(Color.parseColor("#b4b4b5"));
            tv.setText(get);
        }
    }

    public static long getCurrentDate() {
        return System.currentTimeMillis();
    }

    /**
     * 1-7 代表周日到周六
     *
     * @return
     */
    public static int getCurrentWeekDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.DAY_OF_WEEK);
    }


    public static long getBeiJingTime() {
        long time;
        Date date = new Date();
        time = date.getTime();
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8:00");
        Calendar calendar = Calendar.getInstance();
        TimeZone localTimeZone = calendar.getTimeZone();
//        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        time = time - localTimeZone.getRawOffset() + timeZone.getRawOffset();
        return time;
    }

    public static long getTimeStamp(String time) {
        try {
            Date date = nyrsf.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            return 0;
        }
    }


    public static int getMonthDays(int year, int month) {
        if (month > 12) {
            month = 1;
            year += 1;
        } else if (month < 1) {
            month = 12;
            year -= 1;
        }
        int[] arr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int days = 0;

        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            arr[1] = 29; // 闰年2月29天
        }

        try {
            days = arr[month - 1];
        } catch (Exception e) {
            e.getStackTrace();
        }

        return days;
    }

    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getCurrentMonthDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static int getWeekDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    public static int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

//    public static CustomDate getNextSunday() {
//
//        Calendar c = Calendar.getInstance();
//        c.add(Calendar.DATE, 7 - getWeekDay() + 1);
//        CustomDate date = new CustomDate(c.get(Calendar.YEAR),
//                c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
//        return date;
//    }

    public static int[] getWeekSunday(int year, int month, int day, int pervious) {
        int[] time = new int[3];
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.add(Calendar.DAY_OF_MONTH, pervious);
        time[0] = c.get(Calendar.YEAR);
        time[1] = c.get(Calendar.MONTH) + 1;
        time[2] = c.get(Calendar.DAY_OF_MONTH);
        return time;

    }

    public static int getWeekDayFromDate(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDateFromString(year, month));
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index <= 0) {
            week_index = 7;
        }
//        Log.i(TAG, "" + week_index);
        return week_index;
    }

    /**
     * 获取某个月第一天的Date
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getDateFromString(int year, int month) {
        String m = month > 9 ? String.valueOf(month) : "0" + String.valueOf(month);
        String dateString = String.valueOf(year) + "-" + m + "-01";
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * @return -1代表过去的月份，0代表当前月，1代表未来的月份
     */
//    public static int isCurrentMonth(CustomDate date) {
//
//        if (date.year < DateUtil.getYear()) {
//            return -1;
//        }
//        if (date.year > DateUtil.getYear()) {
//            return 1;
//        }
//
//        if (date.month < DateUtil.getMonth()) {
//            return -1;
//        }
//
//        if (date.month > DateUtil.getMonth()) {
//            return 1;
//        }
//
//        return 0;
//
//    }
    public static String getNYRTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        Date date = new Date((Long.parseLong(time)) * 1000);
        String formattedTime = nyrsf.format(date);
        return formattedTime;
    }

    public static int getDaysOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_YEAR);
    }
}
