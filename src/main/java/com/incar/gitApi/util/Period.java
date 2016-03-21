package com.incar.gitApi.util;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
public class Period {
    private int dayOfWeek;
    private int hourOfDay;
    private int number;
    private boolean isInWork;

    public Period(){}

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

    @Override
    public String toString() {
        return "\\n Period{" +
                "dayOfWeek=" + dayOfWeek +"\n"+
                ", hourOfDay=" + hourOfDay +"\n"+
                ", number=" + number +"\n"+
                ", isInWork=" + isInWork +"\n"+
                '}';
    }
}
