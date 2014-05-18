package com.xiayule.itstime.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiayule.itstime.R;
import com.xiayule.itstime.domain.Memo;
import com.xiayule.itstime.service.MemoService;
import com.xiayule.itstime.utils.Time;

import java.lang.Boolean;

public class AddMemoActivity extends BaseActivity {
    private static final String TAG = "AddMemoActivity";

    public static final String PARAM_NEW_MEMO = "new_memo";
    public static final String PARAM_DATE = "date";
    public static final String PARAM_CONTENT = "content";
    public static final String PARAM_ID = "_id";

    private TextView txtDate;
    private EditText editMemo;
    private RelativeLayout rl_date;

    // 参数列表
    private boolean paramNewMemo;
    private String paramDate;
    private String paramContent;
    private int paramId;

    // 判断新建 还是 修改
    private Boolean isNewMemo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memo);

        initValues();
        initComp();
        setListener();
    }

    private void initValues() {
        Bundle data = getIntent().getExtras();
        paramNewMemo = data.getBoolean(PARAM_NEW_MEMO, false);
        if (!paramNewMemo) {
            paramDate = data.getString(PARAM_DATE);
            paramContent = data.getString(PARAM_CONTENT);
            paramId = data.getInt(PARAM_ID);
        }
    }

    private void setListener() {
        rl_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = txtDate.getText().toString();
                String[] d = date.split("-");
                int year = Integer.valueOf(d[0]);
                int month = Integer.valueOf(d[1]);
                int day_of_month = Integer.valueOf(d[2]);

                // 直接创建一个 DatePickerdialog， 并将它显示出来
                new DatePickerDialog(AddMemoActivity.this,
                        new DatePickerDialog.OnDateSetListener(){
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                                String date = year + "-" +
                                        (month < 10 ? "0"+month : month) + "-" +
                                        (dayOfMonth < 10 ? "0"+dayOfMonth : dayOfMonth);
                                txtDate.setText(date);
                            }
                        },
                        year,
                        month,
                        day_of_month)
                .show();
            }
        });
    }

    public void initComp() {
        txtDate = (TextView) findViewById(R.id.memo_date);
        editMemo = (EditText) findViewById(R.id.et_memo);
        rl_date = (RelativeLayout) findViewById(R.id.rl_date);

        // 设置显示内容
        if (paramNewMemo) {// 如果是新建的 memo
            txtDate.setText(Time.getDate());
        } else {
            txtDate.setText(paramDate);
            editMemo.setText(paramContent);
        }

        // 将光标放在最后

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.memu_add_memo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_memo:
                String date = txtDate.getText().toString();
                String content = editMemo.getText().toString();

                MemoService service = new MemoService(this);

                if (paramNewMemo) {// 如果是新建的 memo
                    Memo memo = new Memo(date, content);
                    service.save(memo);
                } else {// 如果是修改 memo
                    Memo memo = new Memo(paramId, date, content);
                    service.update(memo);
                }

                Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();

                backHome();

                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
