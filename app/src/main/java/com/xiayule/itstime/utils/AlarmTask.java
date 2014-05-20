package com.xiayule.itstime.utils;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.xiayule.itstime.receiver.AlarmReceiver;

import org.apache.http.impl.cookie.DateParseException;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tan on 14-5-16.
 */
public class AlarmTask {
    private static final String TAG = "AlarmTask";

    public static void newTask(Context context, long millis, int alarmId) {
        Intent intent = new Intent(context, AlarmReceiver.class);

        intent.putExtra("_id", alarmId);

        PendingIntent pi= PendingIntent.getBroadcast(context, alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //设置一个PendingIntent对象，发送广播
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        //获取AlarmManager对象

        Log.i(TAG, "收到 newTask");
        Toast.makeText(context, "有新的待办要处理哦", Toast.LENGTH_SHORT).show();


        // 指定事件启动
        am.set(AlarmManager.RTC_WAKEUP, millis, pi);

        //时间到时，执行PendingIntent，只执行一次
        //AlarmManager.RTC_WAKEUP休眠时会运行，如果是AlarmManager.RTC,在休眠时不会运行
        //am.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 10000, pi);
        //如果需要重复执行，使用上面一行的setRepeating方法，倒数第二参数为间隔时间,单位为毫秒
    }

    public static void newTask(Context context, Calendar calendar, int alarmId) {
        newTask(context, calendar.getTimeInMillis(), alarmId);
    }

    public static void newTask(Context context, Date date, int alarmId) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        newTask(context, c, alarmId);
    }

    public static void newTask(Context context, String date, int alarmId) {
        try {
            Date d = Time.parseDate(date);
            newTask(context, d, alarmId);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void newTask(Context context, int year, int month, int day, int hour, int minute, int second, int alarmId) {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month-1); //也可以填数字，0-11,一月为0 或者用 类似Calenday.MAY
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);

        newTask(context, c.getTimeInMillis(), alarmId);
    }


    // TODO: 取消待办提醒
    public static void cancel(Context context, int alarmId) {
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        //am.cancel();
    }
}
