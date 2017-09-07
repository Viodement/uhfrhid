package com.senter.demo.uhf.commonActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.senter.demo.uhf.R;

/**
 * Created by Lenovo on 2017/4/11.
 */

public class ForgetPasswordActivity extends Activity {
    private TextView tv_forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_forgetpassword);
        tv_forget = (TextView) findViewById(R.id.tv_forget);
    }
}
