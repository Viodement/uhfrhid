package com.senter.demo.uhf.commonActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.senter.demo.uhf.R;

public class EquipActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equip);
    }

    /**
     * 出库使用
     * @param view
     */
    public void outuse(View view) {
        Intent outIntent = new Intent(EquipActivity.this,ComeActivity.class);
        startActivity(outIntent);
    }

    public void inuse(View view){
        Intent inIntent = new Intent(EquipActivity.this,InActivity.class);
        startActivity(inIntent);
    }

    public void mytools(View view){

    }
}
