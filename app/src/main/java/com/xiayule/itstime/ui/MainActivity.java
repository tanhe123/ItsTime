package com.xiayule.itstime.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import com.xiayule.itstime.R;
import com.xiayule.itstime.fragment.BlankFragment;
import com.xiayule.itstime.fragment.MemoListFragment;
import com.xiayule.itstime.service.MemoService;


/*
TODO:
1. 动态修改 actionbar， 如长按 list item， 然后可以删除，可以标记为已完成
2. Navigation

 */
public class MainActivity extends BaseActivity
        implements MemoListFragment.OnFragmentInteractionListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        if (savedInstanceState == null) {

            int count = getMemoCount();

            if (count == 0) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new BlankFragment())
                        .commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, MemoListFragment.newInstance())
                        .commit();
            }

            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        }

        setListener();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //TODO: 动态修改 actionbar
        MenuInflater inflater = getMenuInflater();
 //       menu.clear();;
   //     inflater.inflate(R.menu.memu_add_memo, menu);


        return super.onPrepareOptionsMenu(menu);
    }

    public int getMemoCount() {
        MemoService service = new MemoService(this);
        int count = service.getCount();
        return count;
    }

    private void setListener() {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        menu.clear();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_memo:
                Intent intent = new Intent(this, AddMemoActivity.class);
                intent.putExtra(AddMemoActivity.PARAM_NEW_MEMO, true);
                startActivity(intent);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //提示如果是服务里调用，必须加入new task标
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
