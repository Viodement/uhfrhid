package com.senter.demo.uhf.commonActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.senter.demo.uhf.R;
import com.senter.demo.uhf.commonActivity.DestinationTagSpecifics.PasswordType;
import com.senter.demo.uhf.commonActivity.DestinationTagSpecifics.ProtocolType;
import com.senter.demo.uhf.util.DataTransfer;
import com.senter.support.openapi.StUhf.Bank;
import com.senter.support.openapi.StUhf.UII;


public abstract class Activity2WriteCommonAbstract extends Activity_Abstract {
    @SuppressWarnings("unused")
    private static final String Tag = "Activity2Write";
    private RecordsBoard recordsBoard;
    private DestinationTagSpecifics destinationTagSpecifics;

    private Spinner bankSpinner;
    private EditText ptrEditText;
    private EditText writeEditText;
    private Button writeButton;

    private String epc = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity22writeactivity);

        bankSpinner = (Spinner) findViewById(R.id.idE22WriteActivity_spinnerBanks);
        ptrEditText = (EditText) findViewById(R.id.idE22WriteActivity_etPointer);
        writeEditText = (EditText) findViewById(R.id.idE22WriteActivity_etDataForWrite);
        //获取数据
        Intent intent = getIntent();
        epc = intent.getStringExtra("epc");
        writeEditText.setText(epc);

        bankSpinner.setSelection(bankSpinner.getCount() - 1);

        writeButton = (Button) findViewById(R.id.idE22WriteActivity_btnWrite);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnWrite();
            }
        });

        recordsBoard = new RecordsBoard(this, findViewById(R.id.idE22WriteActivity_inShow));
        destinationTagSpecifics = new DestinationTagSpecifics(this, findViewById(R.id.idE22WriteActivity_inDestTypes),
                ProtocolType.Iso18k6C, PasswordType.Apwd, getDestinationType());
        destinationTagSpecifics.setOnReadyLisener(new DestinationTagSpecifics.OnDestOpTypesLisener() {
            @Override
            public void onReadyStateChanged(boolean now) {
                setViewEnable(writeButton, now);
            }
        });
    }

    protected abstract DestinationTagSpecifics.TargetTagType[] getDestinationType();

    public void enteringMessage(View v) {
        Toast.makeText(Activity2WriteCommonAbstract.this, "点击事件触发", Toast.LENGTH_LONG).show();
    }

    private void onBtnWrite() {
        if (destinationTagSpecifics.isOrderedUii() == true && destinationTagSpecifics.getDstTagUiiIfOrdered() == null) {
            Toast.makeText(this, R.string.InputCorrectLabel, Toast.LENGTH_SHORT).show();
            return;
        }

        final Bank bank;
        final int ptr;
        final byte[] data;


        bank = Bank.ValueOf(bankSpinner.getSelectedItemPosition());

        try {
            ptr = Integer.valueOf(ptrEditText.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, R.string.InputCorrectReadAddr, Toast.LENGTH_SHORT).show();
            return;
        }
        String decode = writeEditText.getText().toString();

        if (decode.length() == 0 || decode.length() % 4 != 0) {
            Toast.makeText(this, R.string.InputPrompt, Toast.LENGTH_SHORT).show();
            int buwei = decode.length() % 4;
            String buweiStr = "";
            for (int j = 0; j < buwei; j++) {
                buweiStr += "F";
            }
            decode = buweiStr + decode;
//            return;
        }

        data = new byte[decode.length() / 2];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (Integer.valueOf(decode.substring(2 * i, 2 * i + 2), 16) & 0xff);
        }


        onWrite(bank, ptr, data);
    }

    protected final DestinationTagSpecifics getDestinationTagSpecifics() {
        return destinationTagSpecifics;
    }

    protected abstract void onWrite(Bank bank, int offset, byte[] data);

    protected final void addNewMassageToListview(final UII uii, final int writtenWords) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recordsBoard.addMassage(getString(R.string.Label) + DataTransfer.xGetString(uii.getBytes()) + "\r\n"
                        + getString(R.string.WriteWordNumber) + writtenWords);
            }
        });
    }

    protected final void addNewMassageToListview(final UII uii, final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recordsBoard.addMassage(getString(R.string.Label) + DataTransfer.xGetString(uii.getBytes()) + "\r\n" + msg);
            }
        });
    }

    protected final void enableBtnWrite(final boolean enable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                writeButton.setEnabled(enable);
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        menu.add(0, 0, 0, R.string.EmptyData);
        return true;
    }

    /**
     * 清空数据
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
