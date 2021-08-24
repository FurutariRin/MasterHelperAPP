package com.example.m1.pages;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cqupt.master_helper.R;
import com.cqupt.master_helper.biz.basic.LoginBiz;
import com.cqupt.master_helper.dao.UserDao;
import com.cqupt.master_helper.entity.User;

import okhttp3.FormBody;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class loginPageActivity extends AppCompatActivity {
    private EditText name;
    private EditText password;

    private Button login_page_login_btn;
    private Button login_page_register_btn;

    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        bindView();
        click();
    }

    private void bindView() {
        name = findViewById(R.id.loginName);
        password = findViewById(R.id.loginPassword);
        login_page_login_btn = findViewById(R.id.login_page_login_btn);
        login_page_register_btn = findViewById(R.id.login_page_register_btn);
    }

//按钮响应

    private void click() {
        login_page_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                Toast.makeText(loginPageActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                        int retCode = new LoginBiz().LoginBiz(name.getText().toString().trim(),
                                password.getText().toString().trim());

                        if (retCode == 0) {
                            Looper.prepare();
                            Toast.makeText(loginPageActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(loginPageActivity.this, mainPageActivity.class);
                            intent.putExtra("uid", name.getText().toString().trim());
                            startActivity(intent);
                            finish();
                            Looper.loop();
                        } else if (retCode == 1) {
                            Looper.prepare();
                            Toast.makeText(loginPageActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        } else if (retCode == 2) {
                            Looper.prepare();
                            Toast.makeText(loginPageActivity.this, "密码不合法", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        } else if (retCode == 3) {
                            Looper.prepare();
                            Toast.makeText(loginPageActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        } else if (retCode == 4) {
                            Looper.prepare();
                            Toast.makeText(loginPageActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        } else if (retCode == 5) {
                            Looper.prepare();
                            Toast.makeText(loginPageActivity.this, "连接错误", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        } else if (retCode == 6) {
                            Looper.prepare();
                            Toast.makeText(loginPageActivity.this, "没有该用户", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                }).start();

//        if (name.getText().toString().equals("")) {
//            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
//
//        } else if (password.getText().toString().equals("")) {
//            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
//        } else if (name.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
//            Intent login = new Intent();
//            login.setClass(loginPageActivity.this, mainPageActivity.class);
//            loginPageActivity.this.startActivity(login);
//        } else {
//            Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
//        }


            }
        });

        login_page_register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent();
                register.setClass(loginPageActivity.this, registerPageActivity.class);
                loginPageActivity.this.startActivity(register);
            }
        });
    }

    public void login(View view) {

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    FormBody.Builder params =new FormBody.Builder();
//                    params.add("uid","1244");
//                    params.add("password","221421");
//                    OkHttpClient loginClient =new OkHttpClient();
//                    Request loginRequest =new Request.Builder()
//                            .url("http://10.20.169.74")
//                            .post(params.build())
//                            .build();
//                    Response loginResponse =loginClient.newCall(loginRequest).execute();
//                    String response =loginResponse.body().string();
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(loginPageActivity.this,"登录成功",Toast.LENGTH_SHORT);
//                        }
//                    });
//                }
//                catch (Exception e){
//                    e.printStackTrace();
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(loginPageActivity.this,"登录失败",Toast.LENGTH_SHORT);
//                        }
//                    });
//
//                }
//            }
//        }).start();

    }

    public void register(View view) {
//        Toast.makeText(this, "暂未启用注册功能", Toast.LENGTH_SHORT).show();
    }
}
