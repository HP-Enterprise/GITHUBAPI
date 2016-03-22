package com.incar.gitApi.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
public class Period {
    private int number;
    private int year;
    private int weekOfYear;
    private int dayOfWeek;
    private int hourOfDay;
    private int minute;
    private int seconds ;
    private boolean isInWork;


    public Period(){

    }

    public Period(Calendar calendar) {
        this.year = calendar.get(Calendar.YEAR);
        this.weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        this.minute = 0;
        this.seconds = 0;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean getIsInWork() {
        return isInWork;
    }

    public void setIsInWork(boolean isInWork) {
        this.isInWork = isInWork;
    }

    public int getWeekOfYear() {
        return weekOfYear;
    }

    public void setWeekOfYear(int weekOfYear) {
        this.weekOfYear = weekOfYear;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public String toString() {
        return "Period{" +
                "dayOfWeek=" + dayOfWeek +
                ", hourOfDay=" + hourOfDay +
                ", number=" + number +
                ", weekOfYear=" + weekOfYear +
                ", isInWork=" + isInWork +
                ", year=" + year +
                '}';
    }


    //生成本周的所有period
    public static List<Period> generatePeriodList(){
        List<Period> periods = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int k = 0;
        for(int i=2;i<7;i++){//周一到周五
            for(int j=0 ; j<8 ; j++){
                Period period = new Period(calendar);
                period.setDayOfWeek(i);
                period.setNumber(k++);
                switch (j){
                    case 0:
                        period.setHourOfDay(9);
                        break;
                    case 1:
                        period.setHourOfDay(10);
                        break;
                    case 2:
                        period.setHourOfDay(11);
                        break;
                    case 3:
                        period.setHourOfDay(13);
                        break;
                    case 4:
                        period.setHourOfDay(14);
                        break;
                    case 5:
                        period.setHourOfDay(15);
                        break;
                    case 6:
                        period.setHourOfDay(16);
                        break;
                    case 7:
                        period.setHourOfDay(17);
                        break;
                    default:
                        throw new IllegalArgumentException("arg is invalid");
                }
                periods.add(period);
            }
        }
        return periods;
    }

}
