package com.senter.demo.uhf.commonActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.senter.demo.uhf.R;
import com.senter.demo.zxing.activity.CaptureActivity;
import com.senter.demo.zxing.encoding.EncodingUtils;

public class BarcodeActivity extends Activity {
    private TextView tv_content;
    private EditText et_input;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        initView();
        initListener();
    }

    private void initListener() {

    }

    //控件初始化
    private void initView() {
        tv_content = (TextView) findViewById(R.id.tv_content);
        et_input = (EditText) findViewById(R.id.et_input);
        img = (ImageView) findViewById(R.id.img);
    }

    public void scan(View view) {
        startActivityForResult(new Intent(BarcodeActivity.this, CaptureActivity.class), 0);
    }

    public void generate(View view) {
        String str = et_input.getText().toString();
        if (str.equals("")) {
            Toast.makeText(BarcodeActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
        } else {
            // 位图
            try {
                /**
                 * 参数：1.文本 2 3.二维码的宽高 4.二维码中间的那个logo
                 */
                Bitmap bitmap = EncodingUtils.createQRCode(str, 500, 500, null);
                // 设置图片
                img.setImageBitmap(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String result = data.getExtras().getString("result");
            Log.d("Main", result);
            tv_content.setText(result);
        }
    }
}
