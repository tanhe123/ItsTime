package com.xiayule.itstime.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.xiayule.itstime.R;
import com.xiayule.itstime.adapter.MemoAdapter;
import com.xiayule.itstime.domain.Memo;
import com.xiayule.itstime.service.BroadCastService;
import com.xiayule.itstime.service.PreferenceService;
import com.xiayule.itstime.swipelistview.BaseSwipeListViewListener;
import com.xiayule.itstime.swipelistview.SwipeListView;
import com.xiayule.itstime.ui.AddMemoActivity;

import java.util.ArrayList;
import java.util.List;

public class MemoListFragment extends ListFragment {
    private static final String TAG = "MemoListFragment";

    private SwipeListView swipeListView;
    private List<Memo> memos = new ArrayList<Memo>();

    private MemoAdapter adapter;

    private MyBroadcastReceiver br;

    private int idShowMethod;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        idShowMethod = PreferenceService.getShowMethod(getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memo_list, container, false);

        swipeListView = (SwipeListView) view.findViewById(android.R.id.list);

        setListener();

        return view;
    }

    public void setListener() {
        adapter = new MemoAdapter(getActivity(), memos, idShowMethod);

        //setListAdapter(adapter);
        swipeListView.setAdapter(adapter);

        refresh(idShowMethod);

        swipeListView.setSwipeListViewListener(new BaseSwipeListViewListener() {
            @Override
            public void onClickFrontView(int position) {
                super.onClickFrontView(position);
                Memo memo = memos.get(position);

                Log.i(TAG, memo.toString());

                Intent intent = new Intent(getActivity(), AddMemoActivity.class);
                intent.putExtra(AddMemoActivity.PARAM_NEW_MEMO, false);
                intent.putExtra(AddMemoActivity.PARAM_ID, memo.getId());
                intent.putExtra(AddMemoActivity.PARAM_DATE, memo.getDate());
                intent.putExtra(AddMemoActivity.PARAM_CONTENT, memo.getContent());

                startActivity(intent);
            }
        });
    }

    public void refresh(int idShowMethod) {
        adapter.refresh(idShowMethod);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Memo memo = memos.get(position);

        Log.i(TAG, memo.toString());

        Intent intent = new Intent(getActivity(), AddMemoActivity.class);
        intent.putExtra(AddMemoActivity.PARAM_NEW_MEMO, false);
        intent.putExtra(AddMemoActivity.PARAM_ID, memo.getId());
        intent.putExtra(AddMemoActivity.PARAM_DATE, memo.getDate());
        intent.putExtra(AddMemoActivity.PARAM_CONTENT, memo.getContent());

        startActivity(intent);
    }

    @Override
    public void onStart() {
        // 注册广播
        br = new MyBroadcastReceiver();
        BroadCastService.registerBroadCastUpdate(getActivity(), br);
        super.onStart();
    }

    @Override
    public void onStop() {
        // 注销广播
        getActivity().unregisterReceiver(br);
        super.onStop();
    }

    //广播接收器
    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            refresh(idShowMethod);
        }
    }
}
