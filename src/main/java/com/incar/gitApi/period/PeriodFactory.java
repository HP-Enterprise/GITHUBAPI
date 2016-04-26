package com.incar.gitApi.period;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2016/3/23 0023.
 */
public class PeriodFactory {

    //生成某年某周的period数列
    public static List<Period> generatePeriodList(int year,int weekOfYear){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        if(weekOfYear<0 || weekOfYear>calendar.getActualMaximum(Calendar.WEEK_OF_YEAR)){
            throw new IllegalArgumentException("week of year is out of bound");
        }
        List<Period> periods = new ArrayList<>();
        int k = 0;
        for(int i=2;i<7;i++){
            for(int j=0 ; j<8 ; j++){
                Period period = new Period(year,weekOfYear);
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

    //生成今年某周的period数列
    public static List<Period> generatePeriodList(int weekOfYear){
        Calendar calendar = Calendar.getInstance();
        return generatePeriodList(calendar.get(Calendar.YEAR),weekOfYear);
    }

    //生成今年本周的period数列
    public static List<Period> generatePeriodList(){
        Calendar calendar = Calendar.getInstance();
        return generatePeriodList(calendar.get(Calendar.WEEK_OF_YEAR));
    }
}
