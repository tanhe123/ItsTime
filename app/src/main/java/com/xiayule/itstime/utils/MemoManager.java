package com.xiayule.itstime.utils;

import android.content.Context;

import com.xiayule.itstime.domain.Memo;
import com.xiayule.itstime.service.MemoService;

/**
 * Created by tan on 14-5-17.
 */
public class MemoManager {
    public static Memo getMemo(Context context, int id) {
        MemoService service = new MemoService(context);
        return service.find(id);
    }
}
