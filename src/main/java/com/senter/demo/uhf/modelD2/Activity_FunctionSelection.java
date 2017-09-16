package com.senter.demo.uhf.modelD2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.senter.demo.common.misc.ActivityHelper;
import com.senter.demo.uhf.R;
import com.senter.demo.uhf.commonActivity.Activityocation;
import com.senter.demo.uhf.commonActivity.GetMessageActivity;
import com.senter.demo.uhf.commonActivity.ModelChoiceActivity;
import com.senter.demo.uhf.commonActivity.ProductActivity;
import com.senter.demo.uhf.commonActivity.UseageActivity;
import com.senter.demo.uhf.modelD2.ConfigurationSettingsOfModelD2.Protocol;
import com.senter.demo.uhf.util.App;
import com.senter.demo.uhf.util.ExceptionForToast;
import com.senter.demo.uhf.util.LogUtil;


public class Activity_FunctionSelection extends Activity {
    public static final String Tag = "Main2Activity";
    private long exitTime = 0;

    private final ConfigurationSettingsOfModelD2 mConfigurationSettingsOfModelD2
            = ConfigurationSettingsOfModelD2.getInstance();

    private Protocol mCurrentProtocolOfModelD2 = null;

    private ActivityHelper<Activity_FunctionSelection> ah = new ActivityHelper<Activity_FunctionSelection>(this);
    ImageView useageImageView;
    ImageView productImageView;
    ImageView equipmentImageView;
    ImageView locationImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            App.uhfInit();
        } catch (ExceptionForToast e) {
            e.printStackTrace();
            App.uhfClear();
            App.appCfgSaveModelClear();
            ah.showToastShort(e.getMessage());
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d("The activity state---->onDestroy");
        System.exit(0);
    }

    @Override
    protected void onResume() {
        onCreateInitViews();
        super.onResume();
        LogUtil.d("The activity state---->onResume");
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        menu.clear();
//        menu.add(0, 0, 0, "clear remembered model");
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getGroupId()) {
            case 0: {
                switch (item.getItemId()) {
                    case 0: {
                        switch (item.getOrder()) {
                            case 0: {
                                App.uhfUninit();
                                App.uhfClear();
                                App.appCfgSaveModelClear();
                                System.exit(0);
                                finish();
                                return true;
                            }
                            default:
                                break;
                        }
                        break;
                    }
                    default:
                        break;
                }
                break;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class Views {
        public Views() {
            if ((mConfigurationSettingsOfModelD2.usingProtocol() == Protocol.I8k6c)
                    && (mCurrentProtocolOfModelD2 == Protocol.I8k6c)) {
                return;
            } else {
                //through down
            }
            //加载布局
            setContentView(R.layout.activity1_function_selection_d);
            //定义LieanerLayout
            LinearLayout ll;
//            tv6CTag = findViewById(R.id.idMain2ActivityD6C_tv6CTag);

            if (mConfigurationSettingsOfModelD2.usingProtocol() == Protocol.I8k6c) {// 6C
                /**
                 *如果当前选择6C模式
                 */
                mCurrentProtocolOfModelD2 = Protocol.I8k6c;
                //初始化盘点布局
                ll = (LinearLayout) findViewById(R.id.idMain2ActivityD_glApps_inInventory);
                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onIso6C_0Inventory();
                    }
                });
                //初始化读取布局
                ll = (LinearLayout) findViewById(R.id.idMain2ActivityD_glApps_inRead);
                ll.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onIso6C_1Read();
                    }
                });
                //初始化写入布局
                ll = (LinearLayout) findViewById(R.id.idMain2ActivityD_glApps_inWrite);
                ll.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onIso6C_2Write();
                    }
                });
            } else {
                throw new IllegalStateException();
            }
            //初始化设置布局
            ll = (LinearLayout) findViewById(R.id.idMain2ActivityD_glApps_inSettings);
            ll.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onActivitySettings();
                }
            });
            //初始化ImageView
            useageImageView = (ImageView) findViewById(R.id.iv_useage);
            productImageView = (ImageView) findViewById(R.id.iv_product);
            equipmentImageView = (ImageView) findViewById(R.id.iv_equipment);
            locationImageView = (ImageView) findViewById(R.id.iv_location);
            locationImageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent locationIntent = new Intent();
                    locationIntent.setClass(Activity_FunctionSelection.this, Activityocation.class);
                    startActivity(locationIntent);
                }
            });
            addmessage();
        }
    }

    private void addmessage() {
        //添加用户信息
        useageImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent useagemsgintent = new Intent();
                useagemsgintent.setClass(Activity_FunctionSelection.this, UseageActivity.class);
                startActivity(useagemsgintent);
            }
        });
        //添加设备信息

        //添加样品信息
        productImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productIntent = new Intent();
                productIntent.setClass(Activity_FunctionSelection.this, ProductActivity.class);
                startActivity(productIntent);
            }
        });
    }

    /**
     * 从服务器获取数据
     *
     * @param view
     */
    public void gainmsg(View view) {
        Intent downloadMessageintent = new Intent();
        downloadMessageintent.setClass(Activity_FunctionSelection.this, GetMessageActivity.class);
        startActivity(downloadMessageintent);
    }

    /**
     * 添加信息
     */

    private void onCreateInitViews() {
        new Views();
    }

    /**
     * 盘点
     */
    private void onIso6C_0Inventory() {
        ah.startActivity(com.senter.demo.uhf.modelD2.Activity0Inventory.class);
    }

    /**
     * 读取
     */
    private void onIso6C_1Read() {
        ah.startActivity(com.senter.demo.uhf.modelD2.Activity1Read.class);
    }

    /**
     * 写入
     */
    private void onIso6C_2Write() {
        ah.startActivity(com.senter.demo.uhf.modelD2.Activity2Write.class);
    }

    /**
     * 设置
     */
    private void onActivitySettings() {
        ah.startActivity(com.senter.demo.uhf.modelD2.Activity5Settings.class);
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
        Intent backHome = new Intent(Activity_FunctionSelection.this, ModelChoiceActivity.class);
        startActivity(backHome);
        return super.onKeyDown(keyCode, event);
    }
}
