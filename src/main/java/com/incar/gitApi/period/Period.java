package com.incar.gitApi.period;

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

    public Period(int year,int weekOfYear) {
        this.year = year;
        this.weekOfYear = weekOfYear;
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


}
