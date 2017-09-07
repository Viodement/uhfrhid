package com.senter.demo.uhf.Contants;

/**
 * Created by Lenovo on 2017/4/18.
 */
public class Contants {
    /**
     * 本机地址
     */
    public static final String BASE_URL = "http://192.168.1.199:8080/";

    /**
     * 同步请求
     */
    public static final String SYNC_URL = BASE_URL + "test.json";


    /**
     * 异步请求
     */
    public static final String ASYNC_URL = BASE_URL + "test1.json";


    /**
     * POST提交
     */
    public static final String POST_URL = BASE_URL + "test2.json";


    public static final String JACK_URL = BASE_URL + "jack.json";


    //btn标识
    public static final int BTN_FLAG_INVERTORY = 0X01;
    public static final int BTN_FLAG_READ = 0x01 << 1;
    public static final int BTN_FLAG_WRITE = 0x01 << 2;
    public static final int BTN_FLAG_SETTINGS = 0x01 << 3;

    //Fragment标识
    public static final String FRAGMENT_FLAG_INVERTORY = "盘点";
    public static final String FRAGMENT_FLAG_WRITE = "写入";
    public static final String FRAGMENT_FLAG_READ = "读取";
    public static final String FRAGMENT_FLAG_SETTINGS = "设置";

    //声明SharedPrefere的名称，需要设置简单的存储和存储的内容以常量的方式进行声明
    public final static String SP_INFOS = "user_login";
    public final static String USERNAME = "username";
    public final static String USERPASS = "user_pass";
    public final static String TOKEN = "token";
}
