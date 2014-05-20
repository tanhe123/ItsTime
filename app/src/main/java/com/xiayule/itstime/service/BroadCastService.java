package com.xiayule.itstime.service;

import android.content.Context;
import android.content.Intent;

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
}
