package com.xiayule.itstime.utils;

import android.content.Context;

import com.xiayule.itstime.comp.MNotification;
import com.xiayule.itstime.domain.Memo;
import com.xiayule.itstime.service.MemoManager;
import com.xiayule.itstime.service.MemoService;

import java.util.Iterator;
import java.util.List;

/**
 * Created by tan on 14-5-17.
 */
public class PendingAlarmManager {
    public static void freshAllAlarm(Context context) {
        List<Memo> memos = MemoManager.getAllUnfinishedMemos(context);

        // 提示有任务要完成，但不提醒具体内容
        MNotification.shwoNotification(context,
                "有"+memos.size()+"个任务要去处理哦", memos.size());
    }
}
