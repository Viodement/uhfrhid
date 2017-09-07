package com.senter.demo.uhf.commonActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.senter.demo.uhf.Adapter.UserAdapter;
import com.senter.demo.uhf.Contants.Contants;
import com.senter.demo.uhf.Contants.UserMessage;
import com.senter.demo.uhf.R;
import com.senter.demo.uhf.util.OkHttpManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UseageActivity extends Activity {

    private EditText usernameEditText;
    private EditText userjobEditText;
    private EditText usernumberEditText;

    private ListView tableListView;

    List<UserMessage> list = new ArrayList<UserMessage>();
    Map<String, String> usermap = new HashMap<String, String>();
    List<Map<String, String>> Data = new ArrayList<Map<String, String>>();
    Gson gson = new Gson();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useage);
        initView();
        listener();
    }

    private void listener() {

    }

    /**
     * 添加数据进表
     *
     * @param V
     */
    public void joinusermessage(View V) {
        //获取姓名、岗位、工号
        String userName = usernameEditText.getText().toString();
        String userJob = userjobEditText.getText().toString();
        String userNumber = usernumberEditText.getText().toString();
        //添加进list
        list.add(new UserMessage(userName, userJob, userNumber));
        //创建适配器
        UserAdapter userAdapter = new UserAdapter(this, list);
        //添加listview适配器
        tableListView.setAdapter(userAdapter);
        usermap.put("name", userName);
        usermap.put("job", userJob);
        usermap.put("number", userNumber);
        Data.add(usermap);
        String usermessage = gson.toJson(Data);
        OkHttpManager.dopostJson(usermessage,UseageActivity.this, Contants.BASE_URL+"");
        //添加数据进表完成后清空EditText
        clearEidtText();
    }

    //清空EditText
    private void clearEidtText() {
        usernameEditText.getText().clear();
        userjobEditText.getText().clear();
        usernumberEditText.getText().clear();
    }

    //初始化控件
    private void initView() {
        usernameEditText = (EditText) findViewById(R.id.et_username);
        userjobEditText = (EditText) findViewById(R.id.et_userjob);
        usernumberEditText = (EditText) findViewById(R.id.et_usernumber);
        tableListView = (ListView) findViewById(R.id.boardlistview);
    }
}
