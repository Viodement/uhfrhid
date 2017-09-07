package com.senter.demo.uhf.modelD2;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.senter.demo.uhf.R;
import com.senter.demo.uhf.util.App;
import com.senter.support.openapi.StUhf;

public class SettingActivity extends Activity {
    private EditText powerEditText;
    private Button powerGetButton;
    private Button powerSetButton;

    private TextView temporaryTextView;
    private Button temporaryGetButton;

    private RadioGroup rgProtocolSelect;

    private final ConfigurationSettingsOfModelD2 configurationSettings = ConfigurationSettingsOfModelD2.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        powerEditText = (EditText) findViewById(R.id.idE27SettingsActivityD_Power_etShow);
        powerGetButton = (Button) findViewById(R.id.idE27SettingsActivityD_Power_btnRead);
        powerSetButton = (Button) findViewById(R.id.idE27SettingsActivityD_Power_btnSet);
        powerGetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnPowerGet();
            }
        });

        powerSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnPowerSet();
                finish();
            }
        });
        onBtnPowerGet();

        if (App.uhfInterfaceAsModel() == StUhf.InterrogatorModel.InterrogatorModelD2) {
            LinearLayout ll = (LinearLayout) findViewById(R.id.idE27SettingsActivityD_Temporary_ll);
            ll.setVisibility(View.VISIBLE);
            temporaryTextView = (TextView) findViewById(R.id.idE27SettingsActivityD_Temporary_tvShow);
            temporaryGetButton = (Button) findViewById(R.id.idE27SettingsActivityD_Temporary_btnRead);
            temporaryGetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBtnTemporaryGet();
                }
            });

            onBtnTemporaryGet();
        }


        rgProtocolSelect = (RadioGroup) findViewById(R.id.idE27SettingsActivityD_Protocol_rg);
        rgProtocolSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.idE27SettingsActivityD_Protocol_rb6C:
                        configurationSettings.useProtocol(ConfigurationSettingsOfModelD2.Protocol.I8k6c);
                        break;
                    case R.id.idE27SettingsActivityD_Protocol_rb6B:
                        configurationSettings.useProtocol(ConfigurationSettingsOfModelD2.Protocol.I8k6b);
                        break;

                    default:
                        break;
                }
            }
        });

        if (configurationSettings.usingProtocol() == ConfigurationSettingsOfModelD2.Protocol.I8k6c) {// 6C
            rgProtocolSelect.check(R.id.idE27SettingsActivityD_Protocol_rb6C);
        } else if (configurationSettings.usingProtocol() == ConfigurationSettingsOfModelD2.Protocol.I8k6b) {// 6B
            rgProtocolSelect.check(R.id.idE27SettingsActivityD_Protocol_rb6B);
        } else {
            throw new IllegalStateException("only 6c and 6b,no other protocol");
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            SettingActivity.this.setResult(2);
            SettingActivity.this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }



    protected void onBtnPowerGet() {
        Integer power = App.uhfInterfaceAsModelD2().getOutputPower();
        if (power == null) {
            Toast.makeText(SettingActivity.this, "power get failed", Toast.LENGTH_LONG).show();
        }
        powerEditText.setText("" + power);
    }

    protected void onBtnPowerSet() {
        Integer power = null;
        try {
            power = Integer.valueOf(powerEditText.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(SettingActivity.this, "power format error", Toast.LENGTH_LONG).show();
            return;
        }

        if (power < 0 || power > 32) {
            Toast.makeText(SettingActivity.this, "power must be in [0,33]", Toast.LENGTH_LONG).show();
            return;
        }

        Boolean ret = App.uhfInterfaceAsModelD2().setOutputPower(power);
        if (ret == null || ret == false) {
            Toast.makeText(SettingActivity.this, "set power failed", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(SettingActivity.this, "power set successfully", Toast.LENGTH_LONG).show();
        }
    }

    protected void onBtnTemporaryGet() {
        Integer t = App.uhfInterfaceAsModelD2().getReadersTemperature();

        if (t == null) {
            temporaryTextView.setText("");
        } else {
            temporaryTextView.setText("" + t + " ℃");
        }
    }

}
