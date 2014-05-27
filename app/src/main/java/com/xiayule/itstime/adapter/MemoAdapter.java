package com.xiayule.itstime.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiayule.itstime.R;
import com.xiayule.itstime.domain.Memo;
import com.xiayule.itstime.swipelistview.SwipeListView;
import com.xiayule.itstime.service.MemoManager;

import java.util.List;

/**
 * Created by tan on 14-5-18.
 */
public class MemoAdapter extends BaseAdapter {
    private static final String TAG = "MemoAdapter";

    private List<Memo> data;
    private Context context;
    private LayoutInflater inflater;
    private int idShowMethod = 1;// 决定显示内容，全部，已完成，未完成

    public MemoAdapter(Context context, List<Memo> data, int idShowMethod) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
        this.idShowMethod = idShowMethod;
    }

    public void setIdShowMethod(int idShowMethod) {
        this.idShowMethod = idShowMethod;
    }

    public int getIdShowMethod() {
        return idShowMethod;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Memo getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        final Memo item = getItem(position);
        ViewHolder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.memo_list_item, viewGroup, false);
            holder = new ViewHolder();
            holder.memo_date = (TextView) view.findViewById(R.id.memo_date);
            holder.memo_content = (TextView) view.findViewById(R.id.memo_content);
            holder.bt_finish = (Button) view.findViewById(R.id.bt_finish);
            holder.bt_remove = (Button) view.findViewById(R.id.bt_remove);
            holder.ll_front = (LinearLayout) view.findViewById(R.id.front);

            holder.bt_finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 切换任务状态
                    item.setFinished(!item.isFinished());
                    MemoManager.updateMemo(context, item);
                    Log.d(TAG, "position: " + position + " content: " + item.getContent());
                    refresh(idShowMethod);
                }
            });

            holder.bt_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MemoManager.deleteMemo(context, item.getId());
                    refresh(idShowMethod);
                }
            });

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ((SwipeListView)viewGroup).recycle(view, position);

        holder.memo_date.setText(item.getDate());
        holder.memo_content.setText(item.getContent());

        //TODO: 不同的状态显示不同的效果
    // memo 完成 和 未完成 展示的效果不同
    if (!item.isFinished()) {
        holder.bt_finish.setText(R.string.mark_finished);
        holder.ll_front.setBackgroundColor(view.getResources().getColor(R.color.memo_unfinished));
    } else {
        holder.bt_finish.setText(R.string.mark_unfinished);
        holder.ll_front.setBackgroundColor(view.getResources().getColor(R.color.memo_finished));
    }

    return view;
}

    public void refresh(int idShowMethod) {
        switch (idShowMethod) {
            case 0:// 显示全部
                refreshAll();;
                break;
            case 1:// 显示未完成
                refreshUnfinished();
                break;
            case 2:// 显示已完成
                refreshFinished();
                break;
            default:
                refreshUnfinished();
                break;
        }
    }

    public void refreshAll() {
        // 配置 listview
        // 获取所有的备忘
        data.clear();
        data.addAll(MemoManager.getAllMemos(context));
        this.notifyDataSetChanged();
    }

    public void refreshFinished() {
        // 配置 listview
        // 获取所有的备忘
        data.clear();
        data.addAll(MemoManager.getAllFinishedMemos(context));
        this.notifyDataSetChanged();
    }

    public void refreshUnfinished() {
        // 配置 listview
        // 获取所有的备忘
        data.clear();
        data.addAll(MemoManager.getAllUnfinishedMemos(context));
        this.notifyDataSetChanged();
    }

    public void clearFinished() {
        MemoManager.clearAllFinished(context);
    }

    class ViewHolder {
        TextView memo_date;
        TextView memo_content;

        Button bt_finish;
        Button bt_remove;

        LinearLayout ll_front;
    }
}
