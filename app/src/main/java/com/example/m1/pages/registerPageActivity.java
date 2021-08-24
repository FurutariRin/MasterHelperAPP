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
import com.cqupt.master_helper.biz.basic.RegisterBiz;
import com.example.m1.pages.loginPageActivity;
import com.cqupt.master_helper.dao.UserDao;
import com.cqupt.master_helper.entity.User;

public class registerPageActivity extends AppCompatActivity {

    private EditText uname;
    private EditText psw;
    private EditText pswAgain;

    private Button reg_page_reg_btn;

    private int retCode;
    private String uid;
    private String name;
    private String getPsw;
    private String getPswAgain;
    private RegisterBiz registerBiz;

    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        load();
        click();
    }

    private void load() {
        uname = findViewById(R.id.reg_page_user_name_input);
        psw = findViewById(R.id.reg_page_user_password_input_first);
        pswAgain = findViewById(R.id.reg_page_user_password_confirm_input);
        reg_page_reg_btn = findViewById(R.id.reg_page_reg_btn);
    }

    private void click() {
        reg_page_reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = uname.getText().toString().trim();
                getPsw = psw.getText().toString().trim();
                getPswAgain = pswAgain.getText().toString().trim();
                Thread thread = new Thread() {
                    @Override
                    public void run() {

                        registerBiz = new RegisterBiz();
                        retCode = registerBiz.RegisterBiz(name,
                                getPsw,
                                getPswAgain);
                        uid = registerBiz.getUid();
                    }
                };
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (retCode == 0) {
//                    Looper.prepare();
                    Toast.makeText(registerPageActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(registerPageActivity.this, mainPageActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("uid", uid);
                    startActivity(intent);
                    finish();
//                    Looper.loop();
                } else if (retCode == 1) {
//                    Looper.prepare();
                    Toast.makeText(registerPageActivity.this, "密码不合法", Toast.LENGTH_SHORT).show();
//                    Looper.loop();
                } else if (retCode == 2) {
//                    Looper.prepare();
                    Toast.makeText(registerPageActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
//                    Looper.loop();
                } else if (retCode == 3) {
//                    Looper.prepare();
                    Toast.makeText(registerPageActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
//                    Looper.loop();
                } else if (retCode == 4) {
//                    Looper.prepare();
                    Toast.makeText(registerPageActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
//                    Looper.loop();
                } else if (retCode == 5) {
//                    Looper.prepare();
                    Toast.makeText(registerPageActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
//                    Looper.loop();
                }

            }
        });

    }

    public void register(View view) {
    }
}
