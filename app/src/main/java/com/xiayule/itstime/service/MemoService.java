package com.xiayule.itstime.service;

import android.app.PendingIntent;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.xiayule.itstime.domain.Memo;

import java.util.ArrayList;
import java.util.List;

public class MemoService {
    private static final String TAG = "MemoService";

    private String table = "memos";

    private DatabaseHelper memoDatabaseHelper;

    public MemoService(Context context) {
        memoDatabaseHelper = new DatabaseHelper(context);
    }

    public void save(Memo memo) {
        SQLiteDatabase db = memoDatabaseHelper.getWritableDatabase();
        db.execSQL("insert into memos(content, date, finished) values(?, ?, ?)",
                new Object[] {memo.getContent(),
                        memo.getDate(),
                        memo.isFinished() ? 1 : 0});

        Log.i(TAG, memo.getContent() + " " + memo.getDate());
        db.close();
    }

    public void delete(int id) {
        SQLiteDatabase db = memoDatabaseHelper.getWritableDatabase();
        db.execSQL("delete from memos where _id=?",
                new Object[] {id});
        db.close();
    }

    public void update(Memo memo) {
        SQLiteDatabase db = memoDatabaseHelper.getWritableDatabase();
        db.execSQL("update memos set content=?, date=?, finished=? where _id=?",
                new Object[] {
                        memo.getContent(),
                        memo.getDate(),
                        memo.isFinished()+"",
                        memo.getId()});

        db.close();
    }

    public List<Memo> getScrollData() {
        List<Memo> memos = new ArrayList<Memo>();

        SQLiteDatabase db = memoDatabaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from memos order by date desc", null);

        while (cursor.moveToNext()) {
            Memo memo = readOneFromCursor(cursor);
            memos.add(memo);
        }

        cursor.close();
        db.close();
        return memos;
    }

    public Memo find(int id) {
        SQLiteDatabase db = memoDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from memos where _id=?",
                new String[] {id+""});

        Memo memo = null;
        if (cursor.moveToFirst()) {
            memo = readOneFromCursor(cursor);
            cursor.close();
        }
        db.close();
        return memo;
    }

    public void deleteAll() {
        SQLiteDatabase db = memoDatabaseHelper.getWritableDatabase();
        db.execSQL("delete from memos");
        db.close();
    }

    /**
     * 获取表中的数量
     * @return
     */
    public int getCount() {
        SQLiteDatabase db = memoDatabaseHelper.getReadableDatabase();
        /*
        Cursor cursor = db.rawQuery("select count(*) from memos", null);
        int count = cursor.getInt(0);
        cursor.close();*/

        Cursor cursor = db.rawQuery("select * from memos", null);
        int count = cursor.getCount();
        db.close();
        return count;
    }

    private Memo readOneFromCursor(Cursor cursor) {
        String content = cursor.getString(cursor.getColumnIndex("content"));
        int id = cursor.getInt(cursor.getColumnIndex("_id"));
        String date = cursor.getString(cursor.getColumnIndex("date"));
        boolean isFinished = (cursor.getInt(cursor.getColumnIndex("finished")) == 1) ?
                true : false;

        Memo memo = new Memo(id, date, content, isFinished);

        return memo;
    }
}
