package com.incar.gitApi.period;

import com.incar.gitApi.util.DateUtil;

import java.util.*;

/**
 * Created by Administrator on 2016/3/23 0023.
 */
public class PeriodFactory {

    public static Period[] generatePeriods(int year,int weekOfYear){
        Period[] periods = new Period[168];//每周168个时间片
        int k = 0;
        //初始化periods
        for(int i=Calendar.SUNDAY;i<=Calendar.SATURDAY;i++) {//calendar.sunday=1,calendar.saturday=7.
            if(i==Calendar.SUNDAY||i==Calendar.SATURDAY){//周末的工作时间设置isWorkTime属性为false
                for(int j=0;j<=23;j++){
                    Date start = DateUtil.setPeriodTime(year, weekOfYear, i,j,0, 0);//
                    Date end = DateUtil.setPeriodTime(year, weekOfYear, i,j, 59, 59);
                    periods[k++] = new Period(k,start,end,false,false);
                }
            }
            else {
                for(int j=0 ; j<=23 ; j++){
                    Date start = DateUtil.setPeriodTime(year, weekOfYear, i,j, 0, 0);
                    Date end = DateUtil.setPeriodTime(year, weekOfYear, i, j, 59, 59);
                    if((j >= 9 && j <= 11)||(j >= 13 && j < 18)){//工作时间设置isWorkTime属性为true
                        periods[k++] = new Period(k,start,end,false,true);
                    }else {
                        periods[k++] = new Period(k, start, end, false, false);
                    }
                }
            }
        }
        return periods;
    }

    public static Period[] generatePeriods(){
        int year = DateUtil.getYear();
        int weekOfYear = DateUtil.getWeekInYear();
        return generatePeriods(year,weekOfYear);
    }

//    public static void main(String[] args) {
//        long start = System.currentTimeMillis();
//        Period[] period1s = generatePeriods(2016,25);
//        long end = System.currentTimeMillis();
//        System.out.println("初始化耗时："+(end-start)+" ms");
//        System.out.println(Arrays.toString(period1s));
//        System.out.println(DateUtil.getWeekInYear());
//    }

    /**
     *
     * @param startTime issue创建时间
     * @param endTime   issue关闭时间
     * @return  issue的实际花费时间
     */
    public static int  getHours(Date startTime,Date  endTime) {
        Calendar start = Calendar.getInstance();
        start.setTime(startTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        int hours=0;
//        int  minute= start.get(Calendar.MINUTE);
        while (start.compareTo(end) <= 0) {
            int day = start.get(Calendar.DAY_OF_WEEK);
            int  hour= start.get(Calendar.HOUR_OF_DAY);
            if (day != Calendar.SUNDAY && day != Calendar.SATURDAY) {
                if(hour >=9&&hour <=18) {
                    if(hour <12 || hour >13){
                        hours++;

                    }
                }
            }
            start.set(Calendar.HOUR, start.get(Calendar.HOUR) + 1);
        }
//        if(minute>30){//去掉后不满一小时按一小时算
//            hours--;
//        }
        return hours;
    }


}
