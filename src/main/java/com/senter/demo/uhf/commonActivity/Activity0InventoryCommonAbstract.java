package com.senter.demo.uhf.commonActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.senter.demo.common.misc.Accompaniment;
import com.senter.demo.uhf.Contants.Contants;
import com.senter.demo.uhf.R;
import com.senter.demo.uhf.record.ActivityRecordsList;
import com.senter.demo.uhf.record.RecordRWer;
import com.senter.demo.uhf.util.DataTransfer;
import com.senter.demo.uhf.util.HandleDate;
import com.senter.demo.uhf.util.MorePopWindow;
import com.senter.demo.uhf.util.OkHttpManager;
import com.senter.support.openapi.StUhf.UII;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public abstract class Activity0InventoryCommonAbstract extends Activity_Abstract {
    public static final String TAG = "Activity0InventoryCommonAbstract";
    private final Accompaniment accompaniment = Accompaniment.
            newInstanceOfResource(Activity0InventoryCommonAbstract.this, R.raw.tag_inventoried);
    private Handler accompainimentsHandler;
    private final Runnable accompainimentRunnable = new Runnable() {
        @Override
        public void run() {
            accompaniment.start();
            accompainimentsHandler.removeCallbacks(this);
        }
    };

    //    private ImageView menuImageView;
    private ListView msgListView;
    private Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
            .create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity20inventory);
        HandlerThread htHandlerThread = new HandlerThread("");
        htHandlerThread.start();
        accompainimentsHandler = new Handler(htHandlerThread.getLooper());

        views = new Views();
    }

    @Override
    protected void onDestroy() {
        if (accompainimentsHandler != null) {
            accompainimentsHandler.getLooper().quit();
        }
        accompaniment.release();
        super.onDestroy();
    }

    protected final void addNewUiiMassageToListview(UII uii) {
        trigTagAccompainiment();
        views.recordsBoard.addMassage("Uii:" + DataTransfer.xGetString(uii.getBytes()));
    }

    protected final void addNewUiiMassageToListview(String msg) {
        trigTagAccompainiment();
        views.recordsBoard.addMassage(msg);
    }

    protected final void viewsSetAsStoped() {
        views.setStateStoped();
    }

    protected final void viewsSetAsStarted() {
        views.setStateStarted();
    }

    protected final void enableBtnInventory(final boolean enable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                views.btnInventoryOrStop.setEnabled(enable);
            }
        });
    }

    protected abstract void uiOnInverntryButton();

    private void trigTagAccompainiment() {
        accompainimentsHandler.post(accompainimentRunnable);
    }

    /**
     * define three common menu items
     * 1、clear data on screen
     * 2、save data as record
     * 3、review record
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        menu.add(0, 0, 0, R.string.EmptyData);
        menu.add(0, 1, 0, R.string.SaveRecord);
        menu.add(0, 2, 0, R.string.CheckRecord);
        return true;
    }

    /**
     * defined three common menu items
     * 1、clear data on screen
     * 2、save data as record
     * 3、review record
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0: {
                views.recordsBoard.clearMsg();
                break;
            }
            case 1: {
                if (views.recordsBoard.isEmpty() == false) {// save  if data exist
                    new RecodesSaver(Activity0InventoryCommonAbstract.this).showAskWhetherSaveRecodesDialog();
                } else {// tip user if data not exist
                    Toast.makeText(this, getString(R.string.WithoutDataToSave), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case 2: {
                startActivity(new Intent(this, ActivityRecordsList.class));
                break;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //定义map存放epc码
    final Map<String, Object> epcmap = new HashMap<String, Object>();
    //定义List存放map
    final List<String> epcList = new ArrayList<String>();

    //上传数据点击事件
    public void gaininvertoryMsg(View v) {
        //获取ListView适配器
        final SimpleAdapter listviewadapter = (SimpleAdapter) msgListView.getAdapter();
        //实例化Dialog对象
        AlertDialog.Builder countDialog = new AlertDialog.Builder(Activity0InventoryCommonAbstract.this);
        int count = listviewadapter.getCount();
        //设置Dialog属性
        countDialog.setTitle("总共"+count+"条数据，是否上传？");
        countDialog.setCancelable(false);
        countDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listviewadapter.getCount() != 0) {
                    for (int i = 0; i < listviewadapter.getCount(); i++) {
                        HashMap<String, String> uiimap = (HashMap<String, String>) listviewadapter.getItem(i);
                        String map = uiimap.toString();
                        String Epc = HandleDate.cutInvertoryData(map);
                        epcList.add(Epc);
                    }
                    epcmap.put("epcs", epcList);
                    String postData = gson.toJson(epcmap);
                    OkHttpManager.dopostJson(postData, Activity0InventoryCommonAbstract.this, Contants.BASE_URL + "api/device-inventory/checkDevice");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final ProgressDialog myprogressDialog = new ProgressDialog(Activity0InventoryCommonAbstract.this);
                            //显示信息
                            myprogressDialog.setMessage("正在上传，请稍候");
                            myprogressDialog.setCancelable(false);
                            myprogressDialog.show();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1000);
                                        myprogressDialog.dismiss();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Looper.prepare();
                                    Toast.makeText(Activity0InventoryCommonAbstract.this, "上传成功！", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                            }).start();

                        }
                    });
                } else {
                    Toast.makeText(Activity0InventoryCommonAbstract.this, "没有数据，请添加数据后重试", Toast.LENGTH_LONG).show();
                }
            }
        });
        countDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        countDialog.show();

    }

    private Views views;

    protected final Views getViews() {
        return views;
    }

    protected final class Views {
        private final Button btnInventoryOrStop = (Button) findViewById(R.id.idE20InventoryMain_llInvestory_btnStart);
        //初始化recordsBoard
        private final RecordsBoard recordsBoard = new RecordsBoard(Activity0InventoryCommonAbstract.this, findViewById(R.id.idE20InventoryActivity_inShow));
        //初始化popuwindow
        private final MorePopWindow morePopWindow = new MorePopWindow(Activity0InventoryCommonAbstract.this);


        private final RadioGroup rGroup = (RadioGroup) findViewById(R.id.idE20InventoryMain_llInvestory_rgRbs);
        private final View.OnClickListener mBtnInventoryClickLisener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uiOnInverntryButton();
            }
        };

        public Views() {
            rGroup.check(R.id.idE20InventoryMain_llInvestory_rbSingleStep);
            btnInventoryOrStop.setOnClickListener(mBtnInventoryClickLisener);

            //初始化ListView
            msgListView = (ListView) findViewById(R.id.idE2CommenLv2Overlap_lvShow);
//            menuImageView = (ImageView) findViewById(R.id.iv_title);
//            menuImageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    morePopWindow.showPopWindow(menuImageView);
//                }
//            });
        }

        public Views setModes(InventoryMode... modes) {
            for (InventoryMode mode : modes) {
                switch (mode) {
                    case SingleTag: {
                        rGroup.setVisibility(View.VISIBLE);
                        findViewById(R.id.idE20InventoryMain_llInvestory_rbSingleTag).setVisibility(View.VISIBLE);
                        break;
                    }
                    case SingleStep: {
                        rGroup.setVisibility(View.VISIBLE);
                        findViewById(R.id.idE20InventoryMain_llInvestory_rbSingleStep).setVisibility(View.VISIBLE);
                        break;
                    }
                    case AntiCollision: {
                        rGroup.setVisibility(View.VISIBLE);
                        findViewById(R.id.idE20InventoryMain_llInvestory_rbAntiCollision).setVisibility(View.VISIBLE);
                        break;
                    }
                    case Custom: {
                        rGroup.setVisibility(View.GONE);
                    }
                    default:
                        break;
                }
            }
            return this;
        }

        public final InventoryMode getSpecifiedInventoryMode() {
            if (rGroup.getVisibility() != View.VISIBLE) {
                return InventoryMode.Custom;
            }
            if (((RadioButton) findViewById(R.id.idE20InventoryMain_llInvestory_rbSingleTag)).isChecked()) {
                return InventoryMode.SingleTag;
            } else if (((RadioButton) findViewById(R.id.idE20InventoryMain_llInvestory_rbSingleStep)).isChecked()) {
                return InventoryMode.SingleStep;
            } else if (((RadioButton) findViewById(R.id.idE20InventoryMain_llInvestory_rbAntiCollision)).isChecked()) {
                return InventoryMode.AntiCollision;
            } else {
                throw new IllegalStateException();
            }
        }

        public final void setStateStoped() {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    views.rGroup.setEnabled(true);
                    views.btnInventoryOrStop.setText(R.string.Inventory);
                }
            });
        }

        public final void enableInventoryButton(final boolean enable) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    btnInventoryOrStop.setEnabled(enable);
                }
            });
        }

        public final void setStateStarted() {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    views.rGroup.setEnabled(false);
                    views.btnInventoryOrStop.setText(R.string.Stop);
                }
            });
        }


        public final boolean isStateInventoring() {
            return rGroup.isEnabled() == false;
        }
    }


    private final class RecodesSaver {
        public static final String Preference_Name = "com_android_serialread_SerialPortRead";
        public static final String Preference_Prefix_Default = "Def";
        public static final String Preference_Prefix_Key = "Preference_Prefix_Key";
        private final Activity ownersActivity;

        public RecodesSaver(Activity activity) {
            ownersActivity = activity;
        }


        protected final void showAskWhereSaveRecodesDialog() {
            final Dialog dialog;
            LayoutInflater myInflater = LayoutInflater.from(ownersActivity);
            final View view = myInflater.inflate(R.layout.main_asksavepath2, null);

            AlertDialog.Builder dBuilder = new AlertDialog.Builder(ownersActivity).setView(view).setCancelable(false);
            {// show default prefix on ui
                String nString = getSharedPreferences(Preference_Name, 0).getString(Preference_Prefix_Key, Preference_Prefix_Default);
                ((EditText) view.findViewById(R.id.idAskSavePath_edittextPrefix)).setText(nString);
            }
            {// show time as middle name on ui

                Time time = new Time(Time.getCurrentTimezone());
                time.setToNow();
                String string = String.format(Locale.getDefault(), "%4d%02d%02d%02d%02d%02d", time.year, time.month + 1, time.monthDay, time.hour, time.minute, time.second);
                // SimpleDateFormat dfDateFormat=new SimpleDateFormat("yyyyMMddHHMMss");dfDateFormat.format(new Date());
                ((EditText) view.findViewById(R.id.idAskSavePath_edittextName)).setText(string);
            }
            dialog = dBuilder.create();
            View.OnClickListener lOk = new View.OnClickListener() {// yes
                @Override
                public void onClick(View v) {
                    String pathName = RecordRWer.path();
                    String preN = "";
                    String midN = "";
                    try {
                        preN = ((EditText) view.findViewById(R.id.idAskSavePath_edittextPrefix)).getText().toString();
                        midN = ((EditText) view.findViewById(R.id.idAskSavePath_edittextName)).getText().toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (preN == null || midN == null || preN.length() == 0 || midN.length() == 0) {
                        Toast.makeText(Activity0InventoryCommonAbstract.this, R.string.PrefixAndFileNameCannotEmpty, Toast.LENGTH_SHORT).show();
                    } else {
                        {// save current prefix as default for future.
                            getSharedPreferences(Preference_Name, 0).edit().putString(Preference_Prefix_Key, preN).commit();
                        }
                        pathName = pathName + preN + "-" + midN + RecordRWer.suffixRecord;
                        try {
                            RecordRWer.XmlOper.saveRecode2File(pathName, views.recordsBoard.getData());
                            Toast.makeText(Activity0InventoryCommonAbstract.this, R.string.SaveSuccessful, Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(Activity0InventoryCommonAbstract.this, R.string.SaveFailure, Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                        finish();
                    }
                }
            };
            View.OnClickListener lCancel = new View.OnClickListener() {// no
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    finish();
                }
            };

            ((Button) view.findViewById(R.id.idAskSavePath_btnOk)).setOnClickListener(lOk);
            ((Button) view.findViewById(R.id.idAskSavePath_btnCancel)).setOnClickListener(lCancel);

            dialog.show();
        }

        protected final void showAskWhetherSaveRecodesDialog() {
            AlertDialog.Builder aBuilder = new AlertDialog.Builder(ownersActivity).setTitle(R.string.AreYouSave).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {// no
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            }).setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {// yes
                @Override
                public void onClick(DialogInterface dialog, int which) {// create a diaglog to allow the user to confirm whether or not to save the record
                    showAskWhereSaveRecodesDialog();
                    dialog.dismiss();
                }
            }).setOnKeyListener(new DialogInterface.OnKeyListener() {// back
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return true;
                    }
                    return false;
                }
            }).setCancelable(false);
            aBuilder.create().show();
            return;
        }
    }

    public static enum InventoryMode {
        SingleTag, SingleStep, AntiCollision, Custom;
    }
}
