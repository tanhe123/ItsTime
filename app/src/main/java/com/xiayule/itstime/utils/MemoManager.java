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

    /**
     * 获得所有的 memo, 顺序是先未完成，再完成
     * @param context
     * @return
     */
    public static List<Memo> getAllMemos(Context context) {
        MemoService service = new MemoService(context);
        return service.getAllMemos();
    }

    public static List<Memo> getAllFinishedMemos(Context context) {
/*        List<Memo> memos = getAllMemos(context);
        Iterator<Memo> ita = memos.iterator();
        while (ita.hasNext()) {
            Memo memo = ita.next();
            if (!memo.isFinished()) ita.remove();
        }

        return memos;*/
        MemoService service = new MemoService(context);
        return service.getFinishedMemos();
    }

    public static List<Memo> getAllUnfinishedMemos(Context context) {
  /*      List<Memo> memos = getAllMemos(context);
        Iterator<Memo> ita = memos.iterator();
        while (ita.hasNext()) {
            Memo memo = ita.next();
            if (memo.isFinished()) ita.remove();
        }
*/
        MemoService service = new MemoService(context);
        return service.getUnfinishedMemos();
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
