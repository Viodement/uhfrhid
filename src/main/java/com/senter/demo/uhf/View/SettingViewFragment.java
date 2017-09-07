package com.senter.demo.uhf.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.senter.demo.uhf.R;
import com.senter.demo.uhf.modelD2.ConfigurationSettingsOfModelD2;
import com.senter.demo.uhf.modelD2.SettingActivity;
import com.senter.demo.uhf.util.App;

/**
 * Created by Lenovo on 2017/4/1.
 */

public class SettingViewFragment extends Fragment {
    private Activity activity;

    private LinearLayout settinglayout;

    private EditText powerEditText;
    private Button powerGetButton;
    private Button powerSetButton;

    private TextView temporaryTextView;
    private Button temporaryGetButton;

    private RadioGroup rgProtocolSelect;

    private final ConfigurationSettingsOfModelD2 configurationSettings = ConfigurationSettingsOfModelD2.getInstance();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getActivity().getResources().
                getIdentifier("settinglayout", "layout",
                        getActivity().getPackageName()), container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inintview(getActivity());
        listener();
//        powerEditText = (EditText)getView().findViewById(R.id.idE27SettingsActivityD_Power_etShow);
//        powerGetButton = (Button) getView().findViewById(R.id.idE27SettingsActivityD_Power_btnRead);
//        powerSetButton = (Button) getView().findViewById(R.id.idE27SettingsActivityD_Power_btnSet);
//        powerGetButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBtnPowerGet();
//            }
//        });
//        powerSetButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBtnPowerSet();
//            }
//        });
//        onBtnPowerGet();
    }

    protected void onBtnPowerGet() {
        Integer power = App.uhfInterfaceAsModelD2().getOutputPower();
        if (power == null) {
            Toast.makeText(getActivity(),"power get failed",Toast.LENGTH_LONG).show();
        }
        powerEditText.setText("" + power);
    }

    protected void onBtnPowerSet() {
        Integer power = null;
        try {
            power = Integer.valueOf(powerEditText.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(),"power format error",Toast.LENGTH_LONG).show();
            return;
        }

        if (power < 0 || power > 32) {
            Toast.makeText(getActivity(),"power must be in [0,33]",Toast.LENGTH_LONG).show();
            return;
        }

        Boolean ret = App.uhfInterfaceAsModelD2().setOutputPower(power);
        if (ret == null || ret == false) {
            Toast.makeText(getActivity(),"set power failed",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(),"power set successfully",Toast.LENGTH_LONG).show();
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


    private void listener() {
        settinglayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent();
                in.setClass(getContext(), SettingActivity.class);
                startActivity(in);
                Toast.makeText(getContext(),"谁点我",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void inintview(FragmentActivity activity) {
        settinglayout = (LinearLayout) activity.findViewById(R.id.ll_setting);
    }

    public static SettingViewFragment newInstance() {

        return new SettingViewFragment();
    }
}
