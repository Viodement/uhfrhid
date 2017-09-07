package com.senter.demo.uhf.commonActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.senter.demo.uhf.Contants.Contants;
import com.senter.demo.uhf.R;
import com.senter.demo.uhf.commonActivity.DestinationTagSpecifics.PasswordType;
import com.senter.demo.uhf.commonActivity.DestinationTagSpecifics.ProtocolType;
import com.senter.demo.uhf.util.DataTransfer;
import com.senter.demo.uhf.util.HandleDate;
import com.senter.demo.uhf.util.OkHttpManager;
import com.senter.support.openapi.StUhf.Bank;
import com.senter.support.openapi.StUhf.UII;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class Activity1ReadCommonAbstract extends Activity_Abstract {
    private RecordsBoard recordsBoard;
    private DestinationTagSpecifics destinationTagSpecifics;

    private Spinner spnrBank;
    private EditText etPtr;
    private EditText etCnt;
    private Button btnRead;
    private Button btngain;

    private Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
            .create();
    private ListView listView;

    protected final DestinationTagSpecifics getDestinationTagSpecifics() {
        return destinationTagSpecifics;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity21readactivity);

        spnrBank = (Spinner) findViewById(R.id.idE21ReadActivity_spinnerBanks);
        etPtr = (EditText) findViewById(R.id.idE21ReadActivity_etPointer);
        etCnt = (EditText) findViewById(R.id.idE21ReadActivity_etOrdedCount);


        spnrBank.setSelection(spnrBank.getCount() - 1);

        btnRead = (Button) findViewById(R.id.idE21ReadActivity_btnRead);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnRead();
            }
        });


        recordsBoard = new RecordsBoard(this, findViewById(R.id.idE21ReadActivity_inShow));
        listView = (ListView) findViewById(R.id.idE2CommenLv2Overlap_lvShow);
        //获取界面全部数据
        btngain = (Button) findViewById(R.id.btn_gainepc);
        final Map<String, Object> mapList = new HashMap<String, Object>();
        final List<String> mapList1 = new ArrayList<String>();
        btngain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取适配器
                SimpleAdapter adapter = (SimpleAdapter) listView.getAdapter();
                if (adapter.getCount()!=0){
                    for (int i = 0; i < adapter.getCount(); i++) {
                        HashMap<String, String> epcmap = (HashMap<String, String>) adapter.getItem(i);
                        String map = epcmap.toString();
                        String Epc = HandleDate.cutReadData(map);
                        mapList1.add(Epc);
                    }
                    mapList.put("epcs", mapList1);
                    String postjson = gson.toJson(mapList);
                    OkHttpManager.dopostJson(postjson,Activity1ReadCommonAbstract.this, Contants.BASE_URL+"api/device-inventory/checkDevice");
                }else{
                    Toast.makeText(Activity1ReadCommonAbstract.this,"没有数据，请添加数据后重试",Toast.LENGTH_LONG).show();
                }

            }
        });

        destinationTagSpecifics = new DestinationTagSpecifics(this, findViewById(R.id.idE21ReadActivity_inDestTypes), ProtocolType.Iso18k6C, PasswordType.Apwd, getDestinationType());
        destinationTagSpecifics.setOnReadyLisener(new DestinationTagSpecifics.OnDestOpTypesLisener() {
            @Override
            public void onReadyStateChanged(boolean now) {
                setViewEnable(btnRead, now);
            }
        });
    }

    protected abstract DestinationTagSpecifics.TargetTagType[] getDestinationType();

    protected final void enableBtnRead(final boolean enable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnRead.setEnabled(enable);
            }
        });
    }

    private void onBtnRead() {
        if (destinationTagSpecifics.isOrderedUii() == true && destinationTagSpecifics.getDstTagUiiIfOrdered() == null) {
            Toast.makeText(this, R.string.InputCorrectLabel, Toast.LENGTH_SHORT).show();
            return;
        }

        final Bank bank;
        final int ptr;
        final int cnt;

        bank = Bank.ValueOf(spnrBank.getSelectedItemPosition());

        try {
            ptr = Integer.valueOf(etPtr.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, R.string.InputCorrectReadAddr, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            cnt = Integer.valueOf(etCnt.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, R.string.InputCorrectReadLength, Toast.LENGTH_SHORT).show();
            return;
        }
        onRead(bank, ptr, cnt);
    }

    protected abstract void onRead(final Bank bank, final int ptr, final int cnt);

    protected final void addNewMassageToListview(final UII uii, final byte[] data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                byte[] dataShow = data;
                if (data == null) {
                    dataShow = new byte[]{};
                }
                recordsBoard.addMassage(getString(R.string.Label) + (uii != null ? DataTransfer.xGetString(uii.getBytes()) : "unknown")
                        + "\r\n" + getString(R.string.Length) + dataShow.length / 2 + " " + getString(R.string.Data) + DataTransfer.xGetString(dataShow));
            }
        });
    }

    @Override
    public final boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        menu.add(0, 0, 0, R.string.EmptyData);
        return true;
    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0: {
                recordsBoard.clearMsg();
                break;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
