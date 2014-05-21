package com.xiayule.itstime.application;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.xiayule.itstime.service.BroadCastService;
import com.xiayule.itstime.utils.PendingAlarmManager;


/*
TODO:

* 每次启动应用 更新 待办提醒
* 让　notification 删不掉


1. 新建笔记添加导航
0.日历视图， 同步 google 日历, 定时任务，每周固定时间
4. 邮件通知
* 如果有多条要合并，并显示条数（或者合并，单击 展开)
6. 完成积分 排行
8. 要兼容弹出输入法的布局
9. 美化 listview


已解决:
1. Navigation (actionbar 显示 indacator)
2. listview
3. 开机启动
4. 数据库增加字段 finished
5. 配置文件读取
6. listview item position 错误
7. 一般的 memo 不用设置日期(日期默认当天), 重要memo可设定日期，同时要有通知功能
8. 清除所有已完成　通过 broadcast 实现

删除:
1. Notification notification 显示 现在去做（稍后会继续提醒）， 已完成 两个选项， 如果第二次显示则显示 正在做和已完成
2. 动态修改 actionbar， 如长按 list item， 然后可以删除，可以标记为已完成
3. 待办提醒(用每个待办的数据库id作为 通知id，防止相同)

*/
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    private MyBroadcastReceiver br;

    @Override
    public void onCreate() {
        super.onCreate();

        br = new MyBroadcastReceiver();
        BroadCastService.registerBroadCastUpdate(this, br);

        Log.d(TAG, "application 已经启动");
    }

    @Override
    public void onTerminate() {
        this.unregisterReceiver(br);
        super.onTerminate();
    }

    //广播接收器
    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            PendingAlarmManager.freshAllAlarm(context);
        }
    }
}
