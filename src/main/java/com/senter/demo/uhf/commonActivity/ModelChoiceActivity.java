package com.senter.demo.uhf.commonActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.senter.demo.common.misc.ActivityHelper;
import com.senter.demo.uhf.R;
import com.senter.demo.uhf.util.App;
import com.senter.support.openapi.StUhf;

public class ModelChoiceActivity extends Activity {

    Button readRfidButton;
    Button readBarCodeButton;
    ActivityHelper<ModelChoiceActivity> ah = new ActivityHelper<ModelChoiceActivity>(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_choice);
        initView();
        initListener();
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        readRfidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uhfModelChoiced(StUhf.InterrogatorModel.InterrogatorModelD2);
            }
        });
    }

    /**
     * 控件初始化
     */
    private void initView() {
        readRfidButton = (Button) findViewById(R.id.bt_rfid);
        readBarCodeButton = (Button) findViewById(R.id.bt_barcode);
    }

    public void barcode(View view){
        Intent barcode = new Intent(ModelChoiceActivity.this,BarcodeActivity.class);
        startActivity(barcode);
    }
    /**
     * 模式选择
     *
     * @param interrogatorModel
     */
    protected void uhfModelChoiced(StUhf.InterrogatorModel interrogatorModel) {
        if (interrogatorModel == null) {
            if (App.getUhfWithDetectionAutomaticallyIfNeed() != null) {
                startFunctionSelectionActivity(App.uhf().getInterrogatorModel());
                finish();
            } else {
                ah.showToastShort("no uhf module detected");
            }
        } else {
            if (App.getUhf(interrogatorModel) != null) {
                startFunctionSelectionActivity(interrogatorModel);
                finish();
            } else {
                ah.showToastShort("no uhf module detected");
            }
        }
    }

    private final void startFunctionSelectionActivity(StUhf.InterrogatorModel interrogatorModel) {
        switch (interrogatorModel) {

            case InterrogatorModelD2: {
                ah.startActivity(com.senter.demo.uhf.modelD2.Activity_FunctionSelection.class);
                //ah.startActivity(com.senter.demo.uhf.commonActivity.ViewPagerActivity.class);
                break;
            }
            case InterrogatorModelD1://down through
            default:
                throw new IllegalArgumentException();
        }
    }
    /**
     * 重写onKeyDown方法
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent backHome = new Intent(ModelChoiceActivity.this, HomePageActivity.class);
        startActivity(backHome);
        return super.onKeyDown(keyCode, event);
    }
}
