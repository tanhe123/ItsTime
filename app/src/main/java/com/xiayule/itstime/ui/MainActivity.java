package com.xiayule.itstime.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.xiayule.itstime.R;
import com.xiayule.itstime.comp.MNotification;
import com.xiayule.itstime.fragment.BlankFragment;
import com.xiayule.itstime.fragment.MemoListFragment;
import com.xiayule.itstime.service.MemoService;
import com.xiayule.itstime.service.PreferenceService;
import com.xiayule.itstime.utils.AlarmTask;

import java.util.Calendar;


/*
TODO:
* 每次启动应用 更新 待办提醒

1. 动态修改 actionbar， 如长按 list item， 然后可以删除，可以标记为已完成
3. 待办提醒(用每个待办的数据库id作为 通知id，防止相同)
4. 邮件通知
5. Notification notification 显示 现在去做（稍后会继续提醒）， 已完成 两个选项， 如果第二次显示则显示 正在做和已完成
* 如果有多条要合并，并显示条数（或者合并，单击 展开)
6. 完成积分 排行
7. 配置文件读取
8. 要兼容弹出输入法的布局
9. 美化 listview

已解决:
1. Navigation (actionbar 显示 indacator)
2. listview
3. 开机启动
4. 数据库增加字段 finished
*/

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    String[] mDrawerListTitles = new String[]{NEW_MEMO, SETTING_EMAIL};

    private static final String NEW_MEMO = "新建";
    private static final String SYNC_MEMO = "同步";
    private static final String SETTING_EMAIL = "设置邮箱";

    private static final int NAVIGATION_SHOW_ALL = 0;
    private static final int NAVIGATION_SHOW_UNFINISHED = 1;
    private static final int NAVIGATION_SHOW_FINISHED = 2;

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
            Bundle args = new Bundle();
            args.putInt(MemoListFragment.PARAM_SHOW_METHOD, idShowMethod);

            if (count == 0) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new BlankFragment())
                        .commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new MemoListFragment())
                        .commit();
            }
        }

        // newTaskTest();
    //    PendingAlarmManager.fresh(this);
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

                Toast.makeText(MainActivity.this, "selected " + position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void newTaskTest() {
        Calendar c =  Calendar.getInstance();
        AlarmTask.newTask(this, c.getTimeInMillis()+5000, 1);

    }

    private void initComp() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
    }

    private void initDrawerLayout() {
        String[] mPlanetTitles = new String[] {"新建", "通知邮箱", "设置"};
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
                if (title.equals(NEW_MEMO)) {// 新建
                    actionAddMemo();
                } else if (title.equals(SETTING_EMAIL)) {// 设置通知邮箱

                }

                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });
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
                actionAddMemo();
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

    @Override
    protected void onStop() {
        super.onStop();
        MNotification.shwoNotification(this, "该起床喽");
    }

    @Override
    protected void onStart() {
        super.onStart();
   //     MNotification.clearNotification();
    }
}


