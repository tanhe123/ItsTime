package com.xiayule.itstime.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.xiayule.itstime.R;


/**
 * Created by tan on 14-5-8.
 */
public class BaseActivity extends ActionBarActivity {
    protected ActionBar actionBar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化 actionbar
        this.actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_head));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                backHome();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void backHome() {
        Intent homeIntent = new Intent(this, MainActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}