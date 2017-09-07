package com.senter.demo.uhf.commonActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.senter.demo.uhf.Adapter.ProductAdapter;
import com.senter.demo.uhf.Contants.ProductMessage;
import com.senter.demo.uhf.R;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends Activity {

    //定义RadioGroup控件
    private RadioGroup productRadioGroup;
    //定义RadioButton控件
    private RadioButton idleRadioButton;
    private RadioButton busyRadioButton;
    private RadioButton destroyRadioButton;
    private RadioButton reserveRadioButton;
    //定义EditText控件
    private EditText productNameEditText;
    private EditText moreEditText;

    private String productsStatus;

    private ListView tableListView;

    List<ProductMessage> productMessageList = new ArrayList<ProductMessage>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        initView();
        listenerView();
    }

    //获取控件
    private void initView() {
        productRadioGroup = (RadioGroup) findViewById(R.id.product_state);
        idleRadioButton = (RadioButton) findViewById(R.id.idle);
        busyRadioButton = (RadioButton) findViewById(R.id.busy);
        destroyRadioButton = (RadioButton) findViewById(R.id.destroy);
        reserveRadioButton = (RadioButton) findViewById(R.id.reserve);
        productNameEditText = (EditText) findViewById(R.id.et_product_name);
        moreEditText = (EditText) findViewById(R.id.et_more);
        tableListView = (ListView) findViewById(R.id.boardlistview);
    }

    private void listenerView() {
        productRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (idleRadioButton.getId() == checkedId) {
                    productsStatus = idleRadioButton.getText().toString();
                } else if (busyRadioButton.getId() == checkedId) {
                    productsStatus = busyRadioButton.getText().toString();
                } else if (destroyRadioButton.getId() == checkedId) {
                    productsStatus = destroyRadioButton.getText().toString();
                } else if (reserveRadioButton.getId() == checkedId) {
                    productsStatus = reserveRadioButton.getText().toString();
                }
            }
        });
    }


    /**
     * 添加样品信息进表
     *
     * @param v
     */
    public void joinproductmessage(View v) {
        //获取样品名称、状态、备注信息
        String name = productNameEditText.getText().toString();
        String more = moreEditText.getText().toString();
        //添加数据进表
        productMessageList.add(new ProductMessage(name, productsStatus, more));
        //创建适配器
        ProductAdapter productAdapter = new ProductAdapter(this, productMessageList);
        //添加listview适配器
        tableListView.setAdapter(productAdapter);
        clearMessage();
    }

    //清空EditText
    private void clearMessage() {
        productNameEditText.getText().clear();
        moreEditText.getText().clear();
        productRadioGroup.clearCheck();
    }
}
