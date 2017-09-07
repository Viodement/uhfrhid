package com.senter.demo.uhf.commonActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.senter.demo.uhf.R;
import com.senter.demo.uhf.util.Jinzhizhuanhuan;
import com.senter.demo.uhf.util.Judge;
import com.senter.demo.uhf.util.LogUtil;

public class ShowMessageActivity extends Activity {
    //定义文本框控件

    private TextView showMessageTextview;
    private TextView jsontomsgTextView;

    //定义Edittext控件
    private EditText smnameEditText;
    private EditText smdateEditText;
    private EditText smjobEditText;
    private EditText smidEditText;
    //定义按钮控件
    private Button resolveButton;
    String epccode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_message);
        //控件初始化
        initView();
        Intent intent = getIntent();
        epccode = intent.getStringExtra("epcStr");
    }

    /**
     * 控件初始化
     */
    private void initView() {
        showMessageTextview = (TextView) findViewById(R.id.tv_message);
        resolveButton = (Button) findViewById(R.id.bt_resolve);
        jsontomsgTextView = (TextView) findViewById(R.id.tv_jsontomsg);
        //初始化Edittext
        smnameEditText = (EditText) findViewById(R.id.sm_name);
        smdateEditText = (EditText) findViewById(R.id.sm_date);
        smjobEditText = (EditText) findViewById(R.id.sm_job);
        smidEditText = (EditText) findViewById(R.id.sm_id);
    }
    /**
     * 解析按钮点击事件
     */
    public void onClickresovle(View view) {
        Judge judge = new Judge();
        if (judge.isContain(epccode, "fd")) {
            String[] Epc = epccode.split("fd");
            String a = Epc[0];
            String EpcStr = Epc[1];
            String resovle1 = EpcStr.replaceAll(" ", "");
            String resove = resovle1;
            String outcome1 = Jinzhizhuanhuan.toStringHex(resove);
            String outcome = outcome1.trim();
            LogUtil.d(outcome1);
            jsontomsgTextView.setText(outcome);
        } else {

        }
    }
}
