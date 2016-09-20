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
    public static int getMonth(){
        calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.MONTH);
    }
    public static int getQuarter(){
        calendar = Calendar.getInstance();
        calendar.setTime(new Date());

      if(calendar.get(Calendar.MONTH)==1||calendar.get(Calendar.MONTH)==2||calendar.get(Calendar.MONTH)==3){
          return 1;
      }else if(calendar.get(Calendar.MONTH)==4||calendar.get(Calendar.MONTH)==5||calendar.get(Calendar.MONTH)==6){
          return 2;
      }else if(calendar.get(Calendar.MONTH)==7||calendar.get(Calendar.MONTH)==8||calendar.get(Calendar.MONTH)==9){
          return 3;
      }else{
          return 4;
      }
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
    public static int getMonthStartWeek(int year,int month){
        calendar = Calendar.getInstance();
        calendar.set(year,month,1);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
    public static int getMonthEndWeek(int year,int month){
        calendar = Calendar.getInstance();
        if(month==1||month==3||month==5||month==7||month==8||month==10||month==12) {
            calendar.set(year, month, 31);
        }else if(month==4||month==6||month==9||month==11){
            calendar.set(year, month, 30);
        }else {
            calendar.set(year, month, 28);
        }
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
    public static int getQuarterStartWeek(int year,int quarter){
        calendar = Calendar.getInstance();
        switch (quarter){
            case 1:  calendar.set(year,1,1);
                break;
            case 2:  calendar.set(year,4,1);
                break;
            case 3:  calendar.set(year,7,1);
                break;
            case 4:  calendar.set(year,10,1);
                break;
        }
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
    public static int getQuarterEndWeek(int year,int quarter){
        calendar = Calendar.getInstance();
        switch (quarter){
            case 1:  calendar.set(year,3,31);
                break;
            case 2:  calendar.set(year,6,30);
                break;
            case 3:  calendar.set(year,9,30);
                break;
            case 4:  calendar.set(year,12,31);
                break;
        }
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
}
