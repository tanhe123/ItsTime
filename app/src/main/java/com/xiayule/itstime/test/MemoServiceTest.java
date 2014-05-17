package com.xiayule.itstime.test;

import android.test.AndroidTestCase;
import android.util.Log;

import com.xiayule.itstime.domain.Memo;
import com.xiayule.itstime.service.MemoService;


import java.util.List;

public class MemoServiceTest extends AndroidTestCase {
    private static final String TAG = "MemoServiceTest";

    public void testSave() throws Exception {
        MemoService service = new MemoService(getContext());
        Memo memo = new Memo("2014-01-02", "你好");
        service.save(memo);
    }

    public void testDelete() throws Exception {
        MemoService service = new MemoService(getContext());
        Memo memo = new Memo(2, "2014-01-02", "你好吗", true);
        service.delete(2);
    }

    public void testDeleteAll() {
        MemoService service = new MemoService(getContext());
        service.deleteAll();
    }

    public void testUpdate() throws Exception {
        MemoService service = new MemoService(getContext());
        Memo memo = new Memo(2, "2014-01-02", "你好吗", true);
        service.update(memo);
    }

    public void testGetCount() {
        MemoService service = new MemoService(getContext());
        int count = service.getCount();
        Log.i(TAG, count+"  m");
    }

    public void testGetScrollData() throws Exception {
        MemoService service = new MemoService(getContext());
        List<Memo> memos = service.getScrollData();
        Log.i(TAG, memos.toString());
    }

    public void testFind() throws Exception {

    }
}