package com.senter.demo.uhf.commonActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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

    private long exitTime = 0;


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
                Intent codeIntent = new Intent(HomePageActivity.this, ModelChoiceActivity.class);
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
                Intent equipIntent = new Intent(HomePageActivity.this, EquipActivity.class);
                startActivity(equipIntent);
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
                Intent personalIntent = new Intent(HomePageActivity.this, PersonalActivity.class);
                startActivity(personalIntent);
            }
        });
    }

    /**
     * 重写返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                Intent backHome = new Intent(Intent.ACTION_MAIN);
                backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                backHome.addCategory(Intent.CATEGORY_HOME);
                startActivity(backHome);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
