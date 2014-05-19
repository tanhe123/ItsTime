package com.xiayule.itstime.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.xiayule.itstime.ui.MainActivity;

/**
 * Created by tan on 14-5-16.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent it=new Intent(context, MainActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(it);
        //收到广播后启动Activity,简单起见，直接就跳到了设置alarm的Activity
        //intent必须加上Intent.FLAG_ACTIVITY_NEW_TASK flag

        Toast.makeText(context, "闹钟启动", Toast.LENGTH_SHORT).show();
    }
}
