package com.shenhua.nandagy.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by shenhua on 5/8/2017.
 * Email shenhuanet@126.com
 */
public class RelativeDateFormat {

    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;
    private static final String ONE_JUSTNOW = "刚刚";
    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_YESTODAY = "昨天";
    private static final String ONE_BEFORE_YESTODAY = "前天";

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendly_time(String sdate) {
        Date time = toDate(sdate);
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();
        //判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / ONE_HOUR);
            if (hour == 0) {
                long minute = (cal.getTimeInMillis() - time.getTime()) / ONE_MINUTE;
                if (minute > 0) {
                    ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / ONE_MINUTE, 1) + ONE_MINUTE_AGO;
                } else if (minute == 0) {
                    ftime = getSecond(sdate, dateFormater.get().format(cal.getTime())) + ONE_SECOND_AGO;
                } else {
                    ftime = ONE_JUSTNOW;
                }
            } else
                ftime = hour + ONE_HOUR_AGO;
            return ftime;
        }

        long lt = time.getTime() / ONE_DAY;
        long ct = cal.getTimeInMillis() / ONE_DAY;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / ONE_HOUR);
            if (hour == 0) {
                long minute = (cal.getTimeInMillis() - time.getTime()) / ONE_MINUTE;
                if (minute > 0) {
                    ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / ONE_MINUTE, 1) + ONE_MINUTE_AGO;
                } else if (minute == 0) {
                    ftime = getSecond(sdate, dateFormater.get().format(cal.getTime())) + ONE_SECOND_AGO;
                } else {
                    ftime = ONE_JUSTNOW;
                }
            } else {
                ftime = hour + ONE_HOUR_AGO;
            }
        } else if (days == 1) {
            ftime = ONE_YESTODAY + getHourMinute(sdate);
        } else if (days == 2) {
            ftime = ONE_BEFORE_YESTODAY + getHourMinute(sdate);
        } else if (days > 2 && days <= 10) {
            ftime = days + ONE_DAY_AGO;
        } else if (days > 10) {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        }
    };

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    public static String getHourMinute(String sdate) {
        return sdate.substring(sdate.indexOf(" "), sdate.lastIndexOf(":"));
    }

    public static String getSecond(String d1, String d2) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(dateFormater.get().parse(d1));
            long a = c.getTimeInMillis() / 1000L;
            c.setTime(dateFormater.get().parse(d2));
            long b = c.getTimeInMillis() / 1000L;
            long result = b - a;
            if (result < 0) {
                return "0";
            }
            return String.valueOf(result);
        } catch (ParseException e) {
            e.printStackTrace();
            return "0";
        }
    }

}
