package com.senter.demo.uhf.View;

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
import android.widget.ImageView;

import com.senter.demo.uhf.R;
import com.senter.demo.uhf.commonActivity.UseageActivity;

public class HomeViewFragment extends Fragment {
    private Button powerGetButton;
    private EditText powerEditText;
    //定义图片控件
    private ImageView addmessageImageView;
    private ImageView downloadImageView;

    /**
     * 载入fragment布局
     * 控件初始化
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getActivity().getResources().
                getIdentifier("homeviewlayout", "layout",
                        getActivity().getPackageName()), container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getActivity());
        initListener();
    }

    /**
     * 控件初始化
     * @param activity
     */
    private void initView(FragmentActivity activity) {
        addmessageImageView = (ImageView) activity.findViewById(R.id.iv_addMessage);
        downloadImageView = (ImageView) activity.findViewById(R.id.iv_downloadMsg);
        powerEditText = (EditText) activity.findViewById(R.id.idE27SettingsActivityD_Power_etShow);
    }

    /**
     * 监听事件
     */
    private void initListener() {
        addmessageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addintent = new Intent();
                addintent.setClass(getContext(),UseageActivity.class);
                startActivity(addintent);
            }
        });
    }

    public static HomeViewFragment newInstance() {
        return new HomeViewFragment();
    }
}
