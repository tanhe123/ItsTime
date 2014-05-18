package com.xiayule.itstime.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiayule.itstime.R;
import com.xiayule.itstime.domain.Memo;
import com.xiayule.itstime.swipelistview.SwipeListView;

import java.util.List;

/**
 * Created by tan on 14-5-18.
 */
public class MemoAdapter extends BaseAdapter {
    private List<Memo> data;
    private Context context;
    private LayoutInflater inflater;

    public MemoAdapter(Context context, List<Memo> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
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
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final Memo item = getItem(position);
        ViewHolder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.memo_list_item, viewGroup, false);
            holder = new ViewHolder();
            holder.memo_date = (TextView) view.findViewById(R.id.memo_date);
            holder.memo_content = (TextView) view.findViewById(R.id.memo_content);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ((SwipeListView)viewGroup).recycle(view, position);

        holder.memo_date.setText(item.getDate());
        holder.memo_content.setText(item.getContent());


        return view;
    }

    class ViewHolder {
        TextView memo_date;
        TextView memo_content;
    }
}
