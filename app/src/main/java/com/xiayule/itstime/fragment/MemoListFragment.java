package com.xiayule.itstime.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.xiayule.itstime.R;
import com.xiayule.itstime.adapter.MemoAdapter;
import com.xiayule.itstime.domain.Memo;
import com.xiayule.itstime.service.MemoService;
import com.xiayule.itstime.swipelistview.BaseSwipeListViewListener;
import com.xiayule.itstime.swipelistview.SwipeListView;
import com.xiayule.itstime.ui.AddMemoActivity;
import com.xiayule.itstime.utils.MemoManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MemoListFragment extends ListFragment {
    private static final String TAG = "MemoListFragment";

    private SwipeListView swipeListView;
    private List<Memo> memos = new ArrayList<Memo>();

    private MemoAdapter adapter;

    public static final String PARAM_SHOW_METHOD = "SHOW_METHOD";
    private int idShowMethod;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        idShowMethod = getArguments().getInt(PARAM_SHOW_METHOD, 1);
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
}
