package com.xiayule.itstime.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by tan on 14-5-11.
 */
public class Time {
    public static String getDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        return sd.format(c.getTime());
    }
}
