package com.xiayule.itstime.service;

import android.content.Context;

import com.xiayule.itstime.domain.Memo;
import com.xiayule.itstime.service.MemoService;

import java.util.ArrayList;
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
        List<Memo> memos = new ArrayList<Memo>();
        memos.addAll(getAllUnfinishedMemos(context));
        memos.addAll(getAllFinishedMemos(context));
        return memos;
    }

    public static List<Memo> getAllFinishedMemos(Context context) {
        MemoService service = new MemoService(context);
        return service.getFinishedMemos();
    }

    public static List<Memo> getAllUnfinishedMemos(Context context) {
        MemoService service = new MemoService(context);
        return service.getUnfinishedMemos();
    }

    public static void deleteMemo(Context context, int id) {
        MemoService service = new MemoService(context);
        service.delete(id);
    }

    public static void addMemo(Context context, String date, String content) {
        MemoService service = new MemoService(context);
        Memo memo = new Memo(date, content);
        service.save(memo);
    }

    public static void updateMemo(Context context, Memo memo) {
        MemoService service = new MemoService(context);
        service.update(memo);
    }

    public static void clearAllFinished(Context context) {
        MemoService service = new MemoService(context);
        service.clearFinished();
    }
}
