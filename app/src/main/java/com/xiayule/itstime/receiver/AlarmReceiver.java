package com.xiayule.itstime.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.xiayule.itstime.domain.Memo;
import com.xiayule.itstime.ui.MainActivity;
import com.xiayule.itstime.utils.MemoManager;

/**
 * Created by tan on 14-5-16.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra("_id", -1);

        if (id == -1) {
            Log.i(TAG, "AlarmReceiver 收到的消息中参数 _id 有误");
            return ;
        }

        Intent it=new Intent(context, MainActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(it);
        //收到广播后启动Activity,简单起见，直接就跳到了设置alarm的Activity
        //intent 必须加上 Intent.FLAG_ACTIVITY_NEW_TASK flag

        Memo memo = MemoManager.getMemo(context, id);
        Toast.makeText(context, memo.toString(), Toast.LENGTH_SHORT).show();
    }
}
