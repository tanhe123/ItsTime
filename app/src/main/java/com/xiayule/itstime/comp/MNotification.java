package com.xiayule.itstime.comp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import com.xiayule.itstime.R;
import com.xiayule.itstime.ui.MainActivity;

/**
 * Created by tan on 14-5-16.
 */
public class MNotification {
    public static Notification mNotification;
    private static Context mContext;

    // mId allows you to update the notification later on.
    public static final int notifyId = 1;

    public static void shwoNotification(Context context, String contentText) {
        mContext = context;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Its time")
                        .setContentText(contentText)
                        .setTicker("有新的任务要去完成喽")
                        //.setContentInfo("10") 2.x 好像不支持
                        .setAutoCancel(false);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, MainActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

   //     mBuilder.setNumber(++number);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(notifyId, mBuilder.build());
    }

    public static void clearNotification() {
        if (mContext == null) return;

        // 启动后删除之前我们定义的通知
        NotificationManager notificationManager = (NotificationManager)
                mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notifyId);
    }
}
