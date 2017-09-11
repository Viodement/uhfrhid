package com.senter.demo.uhf.commonActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.senter.demo.uhf.R;

public class HomePageActivity extends Activity {

    //消息通知控件
    ImageView messageImageView;
    //标签识别
    ImageView scancodeImageView;
    //台账管理
    ImageView manageImageView;
    //只能巡检
    ImageView localImageView;
    //工具使用
    ImageView toolsImageView;
    //知识库
    ImageView knowldgeImageView;
    //个人中心
    ImageView personImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        initView();
        initListener();
    }

    /**
     * 控件初始化
     */
    private void initView() {
        messageImageView = (ImageView) findViewById(R.id.homepage_1);
        scancodeImageView = (ImageView) findViewById(R.id.homepage_2);
        manageImageView = (ImageView) findViewById(R.id.homepage_3);
        localImageView = (ImageView) findViewById(R.id.homepage_4);
        toolsImageView = (ImageView) findViewById(R.id.homepage_5);
        knowldgeImageView = (ImageView) findViewById(R.id.homepage_6);
        personImageView = (ImageView) findViewById(R.id.homepage_7);
    }

    /**
     * 监听事件
     */
    private void initListener() {
        //消息通知
        messageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //标签识别
        scancodeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent codeIntent = new Intent(HomePageActivity.this,ModelChoiceActivity.class);
                startActivity(codeIntent);
            }
        });
        //台账管理
        manageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //只能巡检
        localImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //工具使用
        toolsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //知识库
        knowldgeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //个人中心
        personImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
