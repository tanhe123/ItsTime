package com.xiayule.itstime.utils;

import android.content.Context;

import com.xiayule.itstime.domain.Memo;
import com.xiayule.itstime.service.MemoService;

import java.util.Iterator;
import java.util.List;

/**
 * Created by tan on 14-5-17.
 */
public class MemoManager {
    public static Memo getMemo(Context context, int id) {
        MemoService service = new MemoService(context);
        return service.find(id);
    }

    public static List<Memo> getAllMemos(Context context) {
        MemoService service = new MemoService(context);
        List<Memo> memos = service.getScrollData();
        return memos;
    }

    public static List<Memo> getAllFinishedMemos(Context context) {
        List<Memo> memos = getAllMemos(context);
        Iterator<Memo> ita = memos.iterator();
        while (ita.hasNext()) {
            Memo memo = ita.next();
            if (!memo.isFinished()) ita.remove();
        }

        return memos;
    }

    public static List<Memo> getAllUnfinishedMemos(Context context) {
        List<Memo> memos = getAllMemos(context);
        Iterator<Memo> ita = memos.iterator();
        while (ita.hasNext()) {
            Memo memo = ita.next();
            if (memo.isFinished()) ita.remove();
        }

        return memos;
    }

    public static void deleteMemo(Context context, int id) {
        MemoService service = new MemoService(context);
        service.delete(id);
    }

    public static void updateMemo(Context context, Memo memo) {
        MemoService service = new MemoService(context);
        service.update(memo);
    }
}
