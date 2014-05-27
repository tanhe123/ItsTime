package com.xiayule.itstime.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.xiayule.itstime.utils.PendingAlarmManager;

import java.util.ArrayList;
import java.util.ServiceConfigurationError;

/**
 * Created by Administrator on 2014/5/20.
 */
public class LocalService extends Service {
    private static final String TAG = "LocalService";

    private IBinder binder=new LocalService.LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "local service has create");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        PendingAlarmManager.freshAllAlarm(this);
        Log.d(TAG, "开启 start");

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }

    //定义内容类继承Binder
    public class LocalBinder extends Binder {
        //返回本地服务
        LocalService getService(){
            return LocalService.this;
        }
    }
}
