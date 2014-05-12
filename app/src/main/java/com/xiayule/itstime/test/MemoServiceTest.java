package com.xiayule.itstime.test;

import android.test.AndroidTestCase;
import android.util.Log;

import com.xiayule.itstime.domain.Memo;
import com.xiayule.itstime.service.MemoService;


import java.util.List;

public class MemoServiceTest extends AndroidTestCase {
    private static final String TAG = "MemoServiceTest";

    public void testSave() throws Exception {

    }

    public void testDelete() throws Exception {

    }

    public void testDeleteAll() {
        MemoService service = new MemoService(getContext());
        service.deleteAll();
    }

    public void testUpdate() throws Exception {

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