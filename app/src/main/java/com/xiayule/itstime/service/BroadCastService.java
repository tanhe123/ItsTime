package com.xiayule.itstime.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.xiayule.itstime.R;

/**
 * Created by tan on 14-5-20.
 */
public class BroadCastService {
    public static void sendBroadCastUpdate(Context context) {
        // 发送更新广播
        Intent intent = new Intent(context.getResources().getString(R.string.broadcast_update_action));
        context.sendBroadcast(intent);
    }

    public static void registerBroadCastUpdate(Context context, BroadcastReceiver br) {
        // 注册广播
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(context.getResources().getString(R.string.broadcast_update_action));
        context.registerReceiver(br, myIntentFilter);
    }
}
