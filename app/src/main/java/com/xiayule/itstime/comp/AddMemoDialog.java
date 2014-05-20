package com.xiayule.itstime.comp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.xiayule.itstime.R;
import com.xiayule.itstime.service.MemoManager;
import com.xiayule.itstime.ui.MainActivity;
import com.xiayule.itstime.utils.Time;

/**
 * Created by tan on 14-5-20.
 */
public class AddMemoDialog {
    public static AlertDialog getNewMemoDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.dialog_add_memo, null);

        builder.setTitle(R.string.title_add_memo_quick)
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 向数据库写入数据
                        EditText memo_content = (EditText) view.findViewById(R.id.memo_content);
                        String content = memo_content.getText().toString();
                        MemoManager.addMemo(context,
                                Time.getDate(),// 获取当前时间
                                content);

                        // 发送更新广播
                        Intent intent = new Intent(context.getResources().getString(R.string.broadcast_update_action));
                        context.sendBroadcast(intent);
                    }
                })

                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return builder.create();
    }
}
