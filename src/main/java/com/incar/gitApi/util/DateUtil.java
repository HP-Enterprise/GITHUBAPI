package com.incar.gitApi.util;

import com.incar.gitApi.period.Period;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Administrator on 2016/2/25 0025.
 */
public class DateUtil {
    public static Date date = null;

    public static DateFormat dateFormat = null;

    public static Calendar calendar = null;

    private DateUtil(){}


    public static String formatDate(Date date){
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        return dateFormat.format(date);
    }

    public static Date getWeekStart(int weekYear,int weekOfYear){
        calendar = Calendar.getInstance();
        calendar.setWeekDate(weekYear, weekOfYear, Calendar.SUNDAY);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getWeekEnd(int weekYear,int weekOfYear){
        calendar = Calendar.getInstance();
        calendar.setWeekDate(weekYear, weekOfYear, Calendar.SATURDAY);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    public static int getWeekInYear(){
        calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
    public static int getIssueWeek(Date due){
        calendar = Calendar.getInstance();
        calendar.setTime(due);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
    public static int getIssueYear(Date due){
        calendar = Calendar.getInstance();
        calendar.setTime(due);
        return calendar.get(Calendar.YEAR);
    }
    public static int getIssueMonth(Date due){
        calendar = Calendar.getInstance();
        calendar.setTime(due);
        return calendar.get(Calendar.MONTH);
    }

    public static int getYear(){
        calendar = Calendar.getInstance();
        calendar.setTime(new Date());
       return calendar.get(Calendar.YEAR);
    }


    public static Date getWeekEnd(){
        calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }


    public static Date setPeriodTime(int year,int weekOfYear,int dayOfWeek,int hourOfDay,int minute,int second){
        calendar = Calendar.getInstance();
        calendar.setWeekDate(year, weekOfYear, dayOfWeek);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND,second);
        return calendar.getTime();
    }

    public static int compareDate(Date date1,Date date2){
        calendar = Calendar.getInstance();
        calendar.setTime(date1);
        calendar.set(Calendar.MILLISECOND,0);
        date1 = calendar.getTime();
        calendar.setTime(date2);
        calendar.set(Calendar.MILLISECOND,0);
        date2 = calendar.getTime();
        return date1.compareTo(date2);
    }

}
