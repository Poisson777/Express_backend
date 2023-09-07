package com.example.express_end.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
    //获取当前时间戳
    public static long timeStamp() {
        return System.currentTimeMillis();
    }

    //获取当前时间戳与day的相加
    public static long getAddTimeStamp(int  day) {
        //当前时间日历
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, day);
        return calendar.getTime().getTime();
    }

    public static long getTomorrow0TimeStamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) +1, 0, 0, 0);
        return calendar.getTime().getTime();
    }
    public String ChangeTime(String timeStamp , String format){
        SimpleDateFormat sdf=  new SimpleDateFormat( format );
        String sd = sdf.format(  new Date(Long.parseLong(String.valueOf(timeStamp))));        // 时间戳转换成时间
        return sd;
    }
    /**
     * @param seconds 精确到毫秒的字符串
     * @param format  日期格式字符串
     * @return 日期字符串
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.length() == 0) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.parseLong(seconds)));
    }
    /**
     * @param date_str 字符串日期
     * @param format   如：yyyy-MM-dd HH:mm:ss
     * @return 时间戳
     */
    public static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
