package com.senter.demo.uhf.commonActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.senter.demo.common.misc.ActivityHelper;
import com.senter.demo.uhf.Contants.Contants;
import com.senter.demo.uhf.R;
import com.senter.demo.uhf.View.SelfDialog;
import com.senter.demo.uhf.util.App;
import com.senter.demo.uhf.util.LogUtil;
import com.senter.demo.uhf.util.OkHttpManager;
import com.senter.support.openapi.StUhf;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends Activity {
    //定义提示框控件
    private SelfDialog selfDialog;
    ProgressDialog p;

    protected static final String TAG = "LoginActivity";
    //定义文本框控件
    private EditText usernameEdittext;
    private EditText passwordEdittext;

    //定义图片控件
    private Button lookpassword;
    private Button forgetPassword;
    private ImageView usernameEmptyButton;
    private ImageView passwordEmptyButton;
    private CheckBox rememberCheckBox;

    private boolean mbDisplayFlg = false;
    ActivityHelper<LoginActivity> ah = new ActivityHelper<LoginActivity>(this);
    private Button loginButton;

    //定义网络请求参数
    private static OkHttpClient client = new OkHttpClient();
    private String result;

    /**
     * 在这里直接设置连接超时，静态方法内，在构造方法被调用前就已经初始话了
     */
    static {
        client.setConnectTimeout(10, TimeUnit.SECONDS);
        client.setWriteTimeout(10, TimeUnit.SECONDS);
        client.setReadTimeout(10, TimeUnit.SECONDS);
    }

    private Request request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        Listener();
    }

    private void Listener() {
        //输入用户名文本框监听事件
        usernameEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                usernameEmptyButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //输入密码文本框监听事件
        passwordEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                passwordEmptyButton.setVisibility(View.VISIBLE);
                lookpassword.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //设置输入用户名文本框焦点监听事件
        usernameEdittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (usernameEdittext.isFocused()) {
                    if (!TextUtils.isEmpty(usernameEdittext.getText().toString())) {
                        //获得焦点时显示清空用户名控件
                        usernameEmptyButton.setVisibility(View.VISIBLE);
                    }
                } else {
                    //没有焦点时不显清空用户名示控件
                    usernameEmptyButton.setVisibility(View.INVISIBLE);
                }
            }
        });
        //设置输入密码文本框焦点监听事件
        passwordEdittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (passwordEdittext.isFocused()) {
                    if (!TextUtils.isEmpty(passwordEdittext.getText().toString())) {
                        //获得焦点时显示清空密码，密码可见控件
                        passwordEmptyButton.setVisibility(View.VISIBLE);
                        lookpassword.setVisibility(View.VISIBLE);
                    }
                } else {
                    //没有焦点时不显示清空密码，密码可见控件
                    passwordEmptyButton.setVisibility(View.INVISIBLE);
                    lookpassword.setVisibility(View.INVISIBLE);
                }
            }
        });
        //设置输入密码文本框焦点监听事件
        passwordEdittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (passwordEdittext.isFocused()) {
                    if (!TextUtils.isEmpty(passwordEdittext.getText().toString())) {
                        //获得焦点时显示清空密码，密码可见控件
                        passwordEmptyButton.setVisibility(View.VISIBLE);
                        lookpassword.setVisibility(View.VISIBLE);
                    }
                } else {
                    //没有焦点时不显示清空密码，密码可见控件
                    passwordEmptyButton.setVisibility(View.INVISIBLE);
                    lookpassword.setVisibility(View.INVISIBLE);
                }
            }
        });
        //密码可见点击事件
        lookpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mbDisplayFlg) {
                    passwordEdittext.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    passwordEdittext.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                mbDisplayFlg = !mbDisplayFlg;
                passwordEdittext.postInvalidate();
            }
        });
        //忘记密码点击事件
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 清空用户名
     *
     * @param view
     */
    public void emptyUsername(View view) {
        usernameEdittext.setText("");
        usernameEmptyButton.setVisibility(View.INVISIBLE);
    }

    /**
     * 清空密码
     *
     * @param view
     */
    public void emptyPassword(View view) {
        passwordEdittext.setText("");
        passwordEmptyButton.setVisibility(View.INVISIBLE);
    }

    /**
     * 记住密码
     *
     * @param username
     * @param user_pass
     */
    public void remember_checkbox(String username, String user_pass) {
        SharedPreferences sp = getSharedPreferences(Contants.SP_INFOS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Contants.USERNAME, username);
        editor.putString(Contants.USERPASS, user_pass);
        editor.commit();
    }

    /**
     * 页面跳转时将密码储存下来
     */
    @Override
    protected void onStop() {
        super.onStop();
        if (rememberCheckBox.isChecked()) {
            this.remember_checkbox(usernameEdittext.getText().toString(), passwordEdittext.getText().toString());
        }
    }

    /**
     * 再次进入时将密码和用户名显示在文本框中
     */
    @Override
    protected void onStart() {
        super.onStart();
        checkIfReamember();
    }

    private void checkIfReamember() {
        SharedPreferences sp = getSharedPreferences(Contants.SP_INFOS, MODE_PRIVATE);
        String username = sp.getString(Contants.USERNAME, null);
        String userpass = sp.getString(Contants.USERPASS, null);
        if (username != null && userpass != null) {
            usernameEdittext.setText(username);
            passwordEdittext.setText(userpass);
            rememberCheckBox.setChecked(true);
        }
    }

    private void initView() {
        //初始化登录按钮
        loginButton = (Button) findViewById(R.id.Login_Button);
        //初始化用户名输入框
        usernameEdittext = (EditText) findViewById(R.id.et_user);
        //初始化密码输入框
        passwordEdittext = (EditText) findViewById(R.id.et_password);
        //初始化清空用户名按钮
        usernameEmptyButton = (ImageView) findViewById(R.id.iv_empty_username);
        //初始化清空密码按钮
        passwordEmptyButton = (ImageView) findViewById(R.id.iv_empty_password);
        //初始化记住密码复选框
        rememberCheckBox = (CheckBox) findViewById(R.id.cb_rememberpwd);
        //初始化密码可见按钮
        lookpassword = (Button) findViewById(R.id.iv_lookpassword);
        //初始化忘记密码按钮
        forgetPassword = (Button) findViewById(R.id.button_forget);
    }

    /**
     * 模式选择
     *
     * @param interrogatorModel
     */
    protected void uhfModelChoiced(StUhf.InterrogatorModel interrogatorModel) {
        if (interrogatorModel == null) {
            if (App.getUhfWithDetectionAutomaticallyIfNeed() != null) {
                startFunctionSelectionActivity(App.uhf().getInterrogatorModel());
                finish();
            } else {
                ah.showToastShort("no uhf module detected");
            }
        } else {
            if (App.getUhf(interrogatorModel) != null) {
                startFunctionSelectionActivity(interrogatorModel);
                finish();
            } else {
                ah.showToastShort("no uhf module detected");
            }
        }
    }

    private final void startFunctionSelectionActivity(StUhf.InterrogatorModel interrogatorModel) {
        switch (interrogatorModel) {

            case InterrogatorModelD2: {
                ah.startActivity(com.senter.demo.uhf.modelD2.Activity_FunctionSelection.class);
                //ah.startActivity(com.senter.demo.uhf.commonActivity.ViewPagerActivity.class);
                break;
            }
            case InterrogatorModelD1://down through
            default:
                throw new IllegalArgumentException();
        }
    }

    private void initAsyncGet() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String username = usernameEdittext.getText().toString();
                String password = passwordEdittext.getText().toString();
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                        "{\"username\":\"" + username + "\",\"password\":\"" + password + "\",\"rememberMe\": true}");
                Log.d("MainActivity", username.toString());
                Log.d("MainActivity", password.toString());
                request = new Request.Builder().
                        url(Contants.BASE_URL + "api/authenticate")
                        .post(requestBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {

                    @Override
                    public void onFailure(Request request, IOException e) {
                        Log.d("MainActivity", "请求失败");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "请求服务器失败，请检查网络连接", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        result = response.body().string();
                        LogUtil.d(result);
                        Gson gson = new Gson();

                        Map map = gson.fromJson(result, Map.class);

                        SharedPreferences sharedPreferences = getSharedPreferences(Contants.SP_INFOS, MODE_PRIVATE);
                        sharedPreferences.edit().putString(Contants.TOKEN, "Bearer " + map.get("idToken")).commit();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //实例化AlertDialog对象
                                AlertDialog.Builder mbuilder = new AlertDialog.Builder(LoginActivity.this);
                                //设置图标
                                //设置内容
                                mbuilder.setTitle("工作模式");
                                //设置content来显示信息
                                mbuilder.setTitle("D2模式");
                                //设置一个PositiveButton
                                mbuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        uhfModelChoiced(StUhf.InterrogatorModel.InterrogatorModelD2);
                                        /**
                                         * 显示正在登录对话框
                                         */
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                //实例化ProgressDialog对象
                                                ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                                                //设置内容
                                                progressDialog.setTitle("请等待");
                                                //显示信息
                                                progressDialog.setMessage("正在登录，请稍候");
                                                progressDialog.setCancelable(false);
                                                progressDialog.show();
                                                new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            Thread.sleep(2000);
                                                        } catch (InterruptedException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                                mbuilder.show();
                            }
                        });
                    }
                });
            }
        }).start();

        OkHttpManager.getAsync("https://www.baidu.com/", new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Log.d("MainActivity", "请求失败");
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Log.d("MainActivity", "哈哈哈啊哈哈我是百度1");
            }
        });
    }

    /**
     * 登陆点击事件
     *
     * @param view
     */
    public void loginOnClick(final View view) {
        String username = usernameEdittext.getText().toString().trim();
        if (username == null || username.length() <= 0) {
            usernameEdittext.requestFocus();
            usernameEdittext.setError("用户名不能为空");
            return;
        } else {
            username = usernameEdittext.getText().toString().trim();
        }
        String password = passwordEdittext.getText().toString().trim();
        if (password == null || password.length() <= 0) {
            passwordEdittext.requestFocus();
            passwordEdittext.setError("密码不能为空");
            return;
        } else {
            password = passwordEdittext.getText().toString().trim();
        }
        //initAsyncGet();
       uhfModelChoiced(StUhf.InterrogatorModel.InterrogatorModelD2);
    }
}
