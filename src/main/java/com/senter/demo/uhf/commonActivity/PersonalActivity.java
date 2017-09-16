package com.senter.demo.uhf.commonActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.view.View;
import android.widget.TextView;

import com.senter.demo.uhf.R;

public class PersonalActivity extends Activity {

    TextView versionTextView;
    TextView exitTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        initView();
        initListener();
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        //版本检查
        versionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder versionAlertDialog = new AlertDialog.Builder(PersonalActivity.this);
                versionAlertDialog.setMessage("当前版本已经是最新版！");
                versionAlertDialog.setCancelable(false);
                versionAlertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                versionAlertDialog.show();
            }
        });
        //退出当前帐号
        exitTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent exitIntent = new Intent(PersonalActivity.this,LoginActivity.class);
                startActivity(exitIntent);
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        versionTextView = (TextView) findViewById(R.id.tv_check_version);
        exitTextView = (TextView) findViewById(R.id.tv_exit);
    }
}
