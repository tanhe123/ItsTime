package com.xiayule.itstime.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.xiayule.itstime.R;
import com.xiayule.itstime.domain.Memo;
import com.xiayule.itstime.service.MemoService;
import com.xiayule.itstime.ui.AddMemoActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MemoListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MemoListFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class MemoListFragment extends ListFragment {
    private static final String TAG = "MemoListFragment";

    private SimpleAdapter adapter;
    private ListView listView;

    private List<Memo> memos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memo_list, container, false);

        listView = (ListView) view.findViewById(android.R.id.list);

        setListener();

        return view;
    }

    public List<Memo> getAllMemos() {
        MemoService service = new MemoService(getActivity());
        memos = service.getScrollData();
        return memos;
    }

    public void setListener() {
        // 配置 listview
        // 获取所有的备忘
        List<Memo> memos = getAllMemos();
        // 将备忘专程固定格式
        List<HashMap<String, String>> datas = new ArrayList<HashMap<String, String>>();
        for (Memo memo : memos) {
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("date", memo.getDate());
            data.put("content", memo.getContent());
            datas.add(data);
        }
        // 为 listview 设置 adapter
        adapter = new SimpleAdapter(getActivity(), datas, R.layout.memo_list_item,
                new String[] {"date", "content"}, new int[] {R.id.date, R.id.memo_content});
        setListAdapter(adapter);


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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}
