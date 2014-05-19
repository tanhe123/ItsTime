package com.xiayule.itstime.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tan on 14-5-11.
 */
public class Time {
    public static String getDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        return sd.format(c.getTime());
    }

    /**
     * 将 2014-05-03 这样的字符串解析为 Date
     */
    public static Date parseDate(String date) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date d = df.parse(date);
        return d;
    }
}
