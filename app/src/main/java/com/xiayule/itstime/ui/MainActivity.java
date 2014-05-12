package com.xiayule.itstime.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.xiayule.itstime.R;
import com.xiayule.itstime.fragment.BlankFragment;
import com.xiayule.itstime.fragment.MemoListFragment;
import com.xiayule.itstime.service.MemoService;

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

        }

        setListener();
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
