package com.senter.demo.uhf.commonActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.senter.demo.uhf.Contants.Contants;
import com.senter.demo.uhf.R;
import com.senter.demo.uhf.modelD2.Activity2Write;
import com.senter.demo.uhf.util.LogUtil;
import com.senter.demo.uhf.util.OkHttpManager;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetMessageActivity extends Activity implements AdapterView.OnItemClickListener {
    //定义ListView控件
    private ListView getMsglistView;

    List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_message);
        getMsglistView = (ListView) findViewById(R.id.getMsgListView);
//        getMsglistView.setOnItemClickListener(GetMessageActivity.this);
//        // 这里使用了系统自带的SimpleAdapter，这是一个简单的适配器，适配器的主要作用就是将资源文件（布局以及显示的文本信息）与listview控件进行绑定
//        // SimpleAdapter(上下文对象, 填充的数据,自定义的item的布局文件,
//        // map对象中的键的数组,item的布局文件中控件id数组)
//        // 注意：【map对象中的键的数组】中的元素将与【item的布局文件中控件id数组】的元素一一对应
//        data = getSource("[{\"addTime\":\"2017-04-26T13:26:21+08:00[Asia/Shanghai]\",\"epcStr\":\"7b2241223a22738b4f204e1c222c2244223a223230313730343236222c2243223a2268c06d4b5458222c2242223a225a48433030303032227d\",\"jod\":\"检测员\",\"name\":\"王传东\",\"numId\":\"ZHC00002\"},{\"addTime\":\"2017-04-26T14:01+08:00[Asia/Shanghai]\",\"epcStr\":\"7b2241223a22738b4f204e1c222c2244223a223230313730343236222c2243223a2268c06d4b5458222c2242223a225a48433030303033227d\",\"jod\":\"检测员\",\"name\":\"王传东\",\"numId\":\"ZHC00003\"},{\"addTime\":\"2017-04-26T15:10:13+08:00[Asia/Shanghai]\",\"epcStr\":\"7b2241223a225c0659299f50222c2244223a223230313730343236222c2243223a225b9e9a8c5ba47ba174065458222c2242223a225a48433030303034227d\",\"jod\":\"实验室管理员\",\"name\":\"将天齐\",\"numId\":\"ZHC00004\"}]");
//        SimpleAdapter simpleAdapter = new SimpleAdapter(GetMessageActivity.this, data, R.layout.activity_get_message
//                , new String[]{"name", "addTime", "jod", "numId"},
//                new int[]{R.id.getMsg_name, R.id.getMsg_date, R.id.getMsg_job, R.id.getMsg_Id});
//        //设置适配器
//        getMsglistView.setAdapter(simpleAdapter);
        initAsyncGet();
    }

    private void initAsyncGet() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(Contants.BASE_URL+"api/easy-users")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responsedate = response.body().string();
                    data = getSource(responsedate);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getMsglistView.setOnItemClickListener(GetMessageActivity.this);
                            // 这里使用了系统自带的SimpleAdapter，这是一个简单的适配器，适配器的主要作用就是将资源文件（布局以及显示的文本信息）与listview控件进行绑定
                            // SimpleAdapter(上下文对象, 填充的数据,自定义的item的布局文件,
                            // map对象中的键的数组,item的布局文件中控件id数组)
                            // 注意：【map对象中的键的数组】中的元素将与【item的布局文件中控件id数组】的元素一一对应
                            SimpleAdapter simpleAdapter = new SimpleAdapter(GetMessageActivity.this, data, R.layout.item_getmessage
                                    , new String[]{"name", "addTime", "jod", "numId"},
                                    new int[]{R.id.tv_name, R.id.tv_date, R.id.tv_job, R.id.tv_jobnumber});
                            //设置适配器
                            getMsglistView.setAdapter(simpleAdapter);
                        }
                    });
                    Log.d("12", responsedate);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        OkHttpManager.getAsync(Contants.BASE_URL+"api/easy-users", new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Log.d("MainActivity", "请求失败");
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Log.d("12", "成功");
            }
        });

    }

    /**
     * Item点击事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        //从listview里面获取当前点击行的map
        Map<String, Object> map = data.get(position);
        //根据key取出value
        final String value = (String) map.get("epcStr");
        LogUtil.d(value);
        //实例化Dialog对象
        AlertDialog.Builder dialog = new AlertDialog.Builder(GetMessageActivity.this);
        //设置Dialog属性
        dialog.setTitle("第"+(position+1)+"条数据，是否写入");
        dialog.setMessage("" + value);
        dialog.setCancelable(false);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(GetMessageActivity.this, Activity2Write.class);
                intent.putExtra("epc", value);
                startActivity(intent);
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    /**
     * 控件初始化
     */
    private void inintView() {

    }

    /**
     * 获取数据
     *
     * @return
     */
    private List<Map<String, Object>> getSource(String retStr) {
        List<Map<String, Object>> Data = new ArrayList<Map<String, Object>>();


        try {
            JSONArray jsonArray = new JSONArray(retStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("addTime", jsonObject.getString("addTime"));
                    map.put("jod", jsonObject.getString("jod"));
                    map.put("name", jsonObject.getString("name"));
                    map.put("numId", jsonObject.getString("numId"));
                    map.put("epcStr", jsonObject.get("epcStr"));
                    Data.add(map);
                }
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Data;

    }
}
