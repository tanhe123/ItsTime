package com.xiayule.itstime.utils;

import android.content.Context;

import com.xiayule.itstime.domain.Memo;
import com.xiayule.itstime.service.MemoService;

import java.util.Iterator;
import java.util.List;

/**
 * Created by tan on 14-5-17.
 */
public class PendingAlarmManager {
    public static void fresh(Context context) {
        MemoService service = new MemoService(context);
        List<Memo> memos = service.getScrollData();

        Iterator<Memo> ita = memos.iterator();

        // 删除所有已完成的
        while (ita.hasNext()) {
            Memo memo = ita.next();
            if (memo.isFinished()) {
                ita.remove();
            }
        }

        // 设置待办提醒
        for (Memo memo : memos) {
            AlarmTask.newTask(context, memo.getDate(), memo.getId());
        }
    }
}
