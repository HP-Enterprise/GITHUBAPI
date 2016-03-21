package com.incar.gitApi.util;

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



}
