package com.incar.gitApi.util;

import org.springframework.util.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/2/25 0025.
 */
public class DateUtil {
    public static Date date = null;

    public static DateFormat dateFormat = null;

    public static Calendar calendar = null;

    /**
     * 功能描述：格式化日期
     *
     * @param dateStr  字符型日期
     * @param format   格式
     * @return Date 日期
     */
    public static Date parseDate(String dateStr, String format) {
        try {
            dateFormat = new SimpleDateFormat(format);
            String dt = dateStr.replaceAll("-", "/");
            if ((!dt.equals("")) && (dt.length() < format.length())) {
                dt += format.substring(dt.length()).replaceAll("[YyMmDdHhSs]",
                        "0");
            }
            date = (Date) dateFormat.parse(dt);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }

    public static Date setWeekStart(int weekYear,int weekOfYear){
        calendar = Calendar.getInstance();
        calendar.setWeekDate(weekYear, weekOfYear, Calendar.SUNDAY);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTime();
    }

    public static Date setWeekEnd(int weekYear,int weekOfYear){
        calendar = Calendar.getInstance();
        calendar.setWeekDate(weekYear, weekOfYear, Calendar.SATURDAY);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        return calendar.getTime();
    }


}
