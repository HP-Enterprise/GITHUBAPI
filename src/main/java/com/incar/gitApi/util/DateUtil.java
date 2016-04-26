package com.incar.gitApi.util;

import com.incar.gitApi.period.Period;

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

    public static Calendar calendar = Calendar.getInstance();

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

    public static Date getWeekStart(int weekYear,int weekOfYear){
        calendar.setWeekDate(weekYear, weekOfYear, Calendar.SUNDAY);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTime();
    }

    public static Date getWeekEnd(int weekYear,int weekOfYear){
        calendar.setWeekDate(weekYear, weekOfYear, Calendar.SATURDAY);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    public static int getWeekInYear(){
        calendar.setTime(new Date());
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getYear(){
        calendar.setTime(new Date());
       return calendar.get(Calendar.YEAR);
    }

    public static Calendar getCalendar(){
        return calendar;
    }

    public static Date getWeekEnd(){
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        return calendar.getTime();
    }

    public static Date getPeriodStart(Period period){
        calendar.setWeekDate(period.getYear(), period.getWeekOfYear(), period.getDayOfWeek());
        calendar.set(Calendar.HOUR_OF_DAY, period.getHourOfDay());
        calendar.set(Calendar.MINUTE, period.getMinute());
        calendar.set(Calendar.SECOND,period.getSeconds());
        return calendar.getTime();
    }

    public static Date getPeriodEnd(Period period){
        calendar.setWeekDate(period.getYear(), period.getWeekOfYear(), period.getDayOfWeek());
        calendar.set(Calendar.HOUR_OF_DAY, period.getHourOfDay());
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND,59);
        return calendar.getTime();
    }
}
