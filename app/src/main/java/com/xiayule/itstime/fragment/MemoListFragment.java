package com.xiayule.itstime.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
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

    private List<Memo> memos;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * //@param param1 Parameter 1.
     * //@param param2 Parameter 2.
     * @return A new instance of fragment MemoListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MemoListFragment newInstance() {
        MemoListFragment fragment = new MemoListFragment();
    //    Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
  //      args.putString(ARG_PARAM2, param2);
      //  fragment.setArguments(args);
        return fragment;
    }

    public MemoListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
   //         mParam1 = getArguments().getString(ARG_PARAM1);
     //       mParam2 = getArguments().getString(ARG_PARAM2);
  //      }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memo_list, container, false);

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

        // 单击事件

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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
