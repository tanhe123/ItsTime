package com.xiayule.itstime.ui;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.xiayule.itstime.R;
import com.xiayule.itstime.comp.AddMemoDialog;
import com.xiayule.itstime.fragment.BlankFragment;
import com.xiayule.itstime.fragment.MemoListFragment;
import com.xiayule.itstime.service.BroadCastService;
import com.xiayule.itstime.service.LocalService;
import com.xiayule.itstime.service.MemoManager;
import com.xiayule.itstime.service.MemoService;
import com.xiayule.itstime.service.PreferenceService;

import com.xiayule.itstime.utils.PendingAlarmManager;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    public MemoListFragment memoListFragment;

    String[] mDrawerListTitles = new String[]{NEW_MEMO, SETTING_EMAIL, CLEAR_ALL_FINISHED};

    private static final String NEW_MEMO = "高级创建";
    private static final String SYNC_MEMO = "同步";
    private static final String SETTING_EMAIL = "设置邮箱";
    private static final String CLEAR_ALL_FINISHED = "清除已完成";

    private int idShowMethod;// 决定如何显示，全部，未完成，已完成

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initComp();
        initDrawerLayout();

        initActionNavigation();
        setListener();

        idShowMethod = PreferenceService.getShowMethod(this);

        if (savedInstanceState == null) {

            int count = getMemoCount();

            actionBar.setSelectedNavigationItem(idShowMethod);

            if (count == 0) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new BlankFragment())
                        .commit();
            } else {
                refreshMemoListFragment();
            }
        }

        PendingAlarmManager.freshAllAlarm(this);
    }

    private void initService() {
        Intent intent = new Intent(this, LocalService.class);
        startService(intent);
        Log.d(TAG, "准备启动 service");
    }

    private void refreshMemoListFragment() {
        memoListFragment = new MemoListFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, memoListFragment)
                .commit();
    }

    private void initActionNavigation() {
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        ArrayAdapter dropDownAdapter =
                ArrayAdapter.createFromResource(this,
                        R.array.navigation_mode_list_array,
                        android.R.layout.simple_list_item_1);
        actionBar.setListNavigationCallbacks(dropDownAdapter, new ActionBar.OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int position, long id) {
                // TODO: 导航栏　可以选择默认显示全部0，未完成1，已完成2

                // 用 position 来表示选择
                PreferenceService.saveShowMethod(MainActivity.this, position);

                Log.d(TAG, "selected " + position);

                refreshMemoListFragment();

                return true;
            }
        });
    }

    private void initComp() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
    }

    private void initDrawerLayout() {
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item,
                R.id.item, mDrawerListTitles));
    }

    private void setListener() {
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // set the listener of the listview
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String title = mDrawerListTitles[position];
                if (title.equals(NEW_MEMO)) {// 快速新建
                    actionAddMemo();
                } else if (title.equals(SETTING_EMAIL)) {// 设置通知邮箱
                    Toast.makeText(MainActivity.this, "该功能马上到来!!!", Toast.LENGTH_SHORT).show();
                } else if (title.equals(CLEAR_ALL_FINISHED)) {
                    MemoManager.clearAllFinished(MainActivity.this);
                    BroadCastService.sendBroadCastUpdate(MainActivity.this);
                }

                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });
    }

    public int getMemoCount() {
        MemoService service = new MemoService(this);
        int count = service.getCount();
        return count;
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
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_add_memo:
                //actionAddMemo();
                AlertDialog dialog = AddMemoDialog.getNewMemoDialog(MainActivity.this);
                dialog.show();

                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    public void actionAddMemo() {
        Intent intent = new Intent(this, AddMemoActivity.class);
        intent.putExtra(AddMemoActivity.PARAM_NEW_MEMO, true);
        startActivity(intent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
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


