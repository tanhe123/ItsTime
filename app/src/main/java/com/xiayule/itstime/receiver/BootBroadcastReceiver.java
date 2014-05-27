package com.xiayule.itstime.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.xiayule.itstime.service.LocalService;
import com.xiayule.itstime.ui.MainActivity;

/**
 * Created by tan on 14-5-15.
 */
public class BootBroadcastReceiver extends BroadcastReceiver{
    private static final String TAG = "BootBroadcastReceiver";

    static final String action_boot="android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(action_boot)) {
       /*     Intent ootStartIntent = new Intent(context, MainActivity.class);
            ootStartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(ootStartIntent);*/
            Intent service = new Intent(context, LocalService.class);
            context.startService(service);
            Log.d(TAG, "开机自动服务启动");
        }
    }
}
