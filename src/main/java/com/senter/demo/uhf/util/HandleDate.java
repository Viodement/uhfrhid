package com.senter.demo.uhf.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Lenovo on 2017/6/7.
 */

public class HandleDate {
    //读取界面数据处理
    public static String cutReadData(String readdata) {
        int beginIndex = readdata.indexOf(":") + 1;
        int endIndex = readdata.indexOf("\r");
        String Data = readdata.substring(beginIndex, endIndex);
        String lastData = Data.replaceAll(" ", "");
        return lastData;
    }

    //map转Json
    public static String maptojson(String key, String value) {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
                .create();
        Map map = new HashMap();
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            value = (String) map.get(key);
        }
        map.put(key, value);
        String postData = gson.toJson(map);
        return postData;
    }
    //盘点界面数据处理
    public static String cutInvertoryData(String invertorydata){
        int beginIndex = invertorydata.indexOf(":") + 1;
        int endIndex = invertorydata.indexOf("}");
        String Data = invertorydata.substring(beginIndex, endIndex);
        String lastData = Data.replaceAll(" ", "");
        return lastData;
    }
}
