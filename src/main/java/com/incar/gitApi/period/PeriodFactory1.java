package com.incar.gitApi.period;

import com.incar.gitApi.util.DateUtil;

import java.util.*;

/**
 * Created by Administrator on 2016/3/23 0023.
 */
public class PeriodFactory1 {

    public static Period1[] generatePeriods(int year,int weekOfYear){
        Period1[] period1s = new Period1[168];
        int k = 0;
        int[] workTime = {9,10,11,13,14,15,16,17};//工作时间整点
        int[] notWorkTime = {0,1,2,3,4,5,6,7,8,12,18,19,20,21,22,23};//非工作时间整点

        //初始化periods
        for(int i=Calendar.SUNDAY;i<=Calendar.SATURDAY;i++) {
            if(i==Calendar.SUNDAY||i==Calendar.SATURDAY){
                for(int j=0;j<=23;j++){
                    Date start = DateUtil.setPeriodTime(year, weekOfYear, i,j,0, 0);
                    Date end = DateUtil.setPeriodTime(year, weekOfYear, i,j, 59, 59);
                    period1s[k++] = new Period1(k,start,end,false,false);
                }
            }else {
                for(int j=0 ; j<8 ; j++){
                    Date start = DateUtil.setPeriodTime(year, weekOfYear, i, workTime[j], 0, 0);
                    Date end = DateUtil.setPeriodTime(year, weekOfYear, i, workTime[j], 59, 59);
                    period1s[k++] = new Period1(k,start,end,false,true);
                }
                for (int j = 0; j < 16; j++) {
                    Date start = DateUtil.setPeriodTime(year, weekOfYear, i, notWorkTime[j], 0, 0);
                    Date end = DateUtil.setPeriodTime(year, weekOfYear, i, notWorkTime[j], 59, 59);
                    period1s[k++] = new Period1(k,start,end,false,false);
                }
            }
        }
        return period1s;
    }

    public static Period1[] generatePeriods(){
        int year = DateUtil.getYear();
        int weekOfYear = DateUtil.getWeekInYear();
        return generatePeriods(year,weekOfYear);
    }

//    public static void main(String[] args) {
//        long start = System.currentTimeMillis();
//        Period1[] period1s = generatePeriods(2016,25);
//        long end = System.currentTimeMillis();
//        System.out.println("初始化耗时："+(end-start)+" ms");
//        System.out.println(Arrays.toString(period1s));
//    }
}
