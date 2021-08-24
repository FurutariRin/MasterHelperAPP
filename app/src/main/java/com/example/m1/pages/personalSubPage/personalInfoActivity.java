package com.example.m1.pages.personalSubPage;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cqupt.master_helper.R;
import com.cqupt.master_helper.biz.basic.UserInfoUpdateBiz;
import com.cqupt.master_helper.dao.UserDao;
import com.cqupt.master_helper.entity.User;
import com.example.m1.pages.basicManageActivity;
import com.example.m1.pages.loginPageActivity;
import com.example.m1.pages.mainPageActivity;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class personalInfoActivity extends AppCompatActivity implements View.OnClickListener  {
    private Button userNameModifyBtn;
    private Button emailModifyBtn;
    private Button passwordModifyBtn;
    private Button adminEntryBtn;
    private Button logout;

    private LinearLayout userNameModifyTable;
    private LinearLayout emailModifyTable;
    private LinearLayout passwordModifyTable;

    private TextView txt_uid;
    private TextView txt_uname;
    private TextView txt_email;

    private Button updateUname;
    private EditText inputUname;

    private EditText inputEmail;
    private Button sendCode;
    private EditText inputCode;
    private Button updateEmail;

    private EditText inputOldPsw;
    private EditText inputNewPsw;
    private EditText inputAgainPsw;
    private Button changePsw;

//    private int adminFlag = 1;

    private String uid;
    private User user;

    private int retCode;

    private String code;

    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");

        Thread thread = new Thread() {
            @Override
            public void run() {
                user = new UserDao().userInfo(uid);
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_personal_info);
        bindView();
    }

    private void bindView() {

        //控件绑定
        userNameModifyBtn = this.findViewById(R.id.userNameModifyBtn);
        emailModifyBtn = this.findViewById(R.id.emailModifyBtn);
        passwordModifyBtn = this.findViewById(R.id.passwordModifyBtn);
        adminEntryBtn = this.findViewById(R.id.btn_adminEntry);
        logout = this.findViewById(R.id.btn_logout);

        userNameModifyTable = this.findViewById(R.id.usernameModifyTable);
        emailModifyTable = this.findViewById(R.id.emailModifyTable);
        passwordModifyTable = this.findViewById(R.id.passowrModifyTable);

        txt_uid = this.findViewById(R.id.txt_uid);
        txt_uname = this.findViewById(R.id.txt_uname);
        txt_email = this.findViewById(R.id.txt_email);

        updateUname = this.findViewById(R.id.btn_updateUname);
        inputUname = this.findViewById(R.id.edt_updateUname);

        inputEmail = this.findViewById(R.id.edt_updateEmail);
        sendCode = this.findViewById(R.id.btn_sendCode);
        inputCode = this.findViewById(R.id.edt_code);
        updateEmail = this.findViewById(R.id.btn_updateEmail);

        inputOldPsw = this.findViewById(R.id.edt_oldPsw);
        inputNewPsw = this.findViewById(R.id.edt_newPsw);
        inputAgainPsw = this.findViewById(R.id.edt_againPsw);
        changePsw = this.findViewById(R.id.btn_changePsw);

        if (user.getLevel() == 3)
            adminEntryBtn.setVisibility(View.VISIBLE);

        //加载用户数据
        txt_uid.setText(user.getUid());
        txt_uname.setText(user.getUname());
        if (user.getEmail() == null || user.getEmail().equals("")) {
            txt_email.setText("未绑定邮箱");
        } else {
            txt_email.setText(user.getEmail());
        }

        //设置监听事件

        userNameModifyBtn.setOnClickListener(this);
        emailModifyBtn.setOnClickListener(this);
        passwordModifyBtn.setOnClickListener(this);
        adminEntryBtn.setOnClickListener(this);
        logout.setOnClickListener(this);

        updateUname.setOnClickListener(this);
        sendCode.setOnClickListener(this);
        updateEmail.setOnClickListener(this);
        changePsw.setOnClickListener(this);

    }

    private void resetView() {
        userNameModifyBtn.setVisibility(View.VISIBLE);
        emailModifyBtn.setVisibility((View.VISIBLE));
        passwordModifyBtn.setVisibility(View.VISIBLE);

        userNameModifyTable.setVisibility(View.GONE);
        emailModifyTable.setVisibility(View.GONE);
        passwordModifyTable.setVisibility(View.GONE);

    }

//    private void hideAllFragments() {
//        if (manageVideoInfoFragment != null)
//            fragmentTransaction.hide(manageVideoInfoFragment);
//        if (manageUserInfoFragment != null)
//            fragmentTransaction.hide(manageUserInfoFragment);
//        if (basicManageFragment!=null)
//            fragmentTransaction.hide(basicManageFragment);
//    }

    @Override
    public void onClick(View v) {
        resetView();
        switch (v.getId()) {
            case R.id.userNameModifyBtn:
                userNameModifyTable.setVisibility(View.VISIBLE);
                userNameModifyBtn.setVisibility(View.GONE);
                break;
            case R.id.emailModifyBtn:
                emailModifyTable.setVisibility(View.VISIBLE);
                emailModifyBtn.setVisibility(View.GONE);
                break;
            case R.id.passwordModifyBtn:
                passwordModifyTable.setVisibility(View.VISIBLE);
                passwordModifyBtn.setVisibility(View.GONE);
                break;
            case R.id.btn_adminEntry:
                Intent basicManagePage = new Intent();
                basicManagePage.setClass(this, basicManageActivity.class);
                this.startActivity(basicManagePage);
                break;
            case R.id.btn_logout:
                Intent logoutIntent = new Intent(this, loginPageActivity.class);
                this.startActivity(logoutIntent);
                finish();
                mainPageActivity.activity.finish();
                break;
            case R.id.btn_updateUname:
                Thread threadUpdateUname = new Thread() {
                    @Override
                    public void run() {
                        retCode = new UserInfoUpdateBiz().unameUpdate(user.getUid(), inputUname.getText().toString().trim());
                    }
                };
                threadUpdateUname.start();
                try {
                    threadUpdateUname.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (retCode == 1) {
                    Toast.makeText(this, "更改成功", Toast.LENGTH_SHORT).show();
                    user.setUname(inputUname.getText().toString().trim());
                    txt_uname.setText(user.getUname());
                } else if (retCode == 2) {
                    Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
                } else if (retCode == 3) {
                    Toast.makeText(this, "连接失败", Toast.LENGTH_SHORT).show();
                }
                userNameModifyTable.setVisibility(View.GONE);
                userNameModifyBtn.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_sendCode:
                Thread threadSendCode = new Thread() {
                    @Override
                    public void run() {
                        String email = inputEmail.getText().toString().trim();

                        code = "";
                        //验证码位数
                        int number = 6;
                        int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
                        Random random = new Random();
                        for (int i = 0; i < number; i++) {
                            int next = random.nextInt(10000);//目的是产生足够随机的数，避免产生的数字重复率高的问题
                            code += numbers[next % 10];
                        }
                        System.out.println(code);

                        try {
                            // 收件人电子邮箱
                            String to = email;

                            // 发件人电子邮箱
                            String from = "fgv951@163.com";

                            // 获取系统属性
                            Properties properties = new Properties();

                            // 设置邮件服务器
                            properties.setProperty("mail.transport.protocol", "SMTP");
                            properties.setProperty("mail.smtp.host", "smtp.163.com");
                            properties.setProperty("mail.smtp.port", "25");
                            properties.setProperty("mail.smtp.auth", "true");
                            properties.setProperty("mail.smtp.timeout", "10000");

                            // 获取默认session对象
                            Session session = Session.getDefaultInstance(properties,
                                    new Authenticator() {
                                        protected PasswordAuthentication getPasswordAuthentication() {
                                            // 登陆邮件发送服务器的用户名和密码
                                            return new PasswordAuthentication("fgv951@163.com", "TNNNCQGMSJSRBOFS");
                                        }
                                    });

                            // 创建默认的 MimeMessage 对象
                            MimeMessage message = new MimeMessage(session);

                            // Set From: 头部头字段
                            message.setFrom(new InternetAddress(from));

                            // Set To: 头部头字段
                            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                            // Set Subject: 头部头字段
                            message.setSubject("考研帮验证码");

                            // 设置消息体
                            message.setText("验证码为：" + code);

                            // 发送消息
                            Transport.send(message);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                threadSendCode.start();
                Toast.makeText(this, "验证码已发送", Toast.LENGTH_SHORT).show();
                emailModifyTable.setVisibility(View.VISIBLE);
                emailModifyBtn.setVisibility(View.GONE);
                break;
            case R.id.btn_updateEmail:
                Thread threadUpdateEmail = new Thread() {
                    @Override
                    public void run() {
                        if (code.equals(inputCode.getText().toString().trim())) {
                            retCode = new UserInfoUpdateBiz().addEmail(user.getUid(), inputEmail.getText().toString().trim());
                        } else {
                            retCode = 4;
                        }
                    }
                };
                threadUpdateEmail.start();
                try {
                    threadUpdateEmail.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (retCode == 1) {
                    Toast.makeText(this, "绑定成功", Toast.LENGTH_SHORT).show();
                    user.setEmail(inputEmail.getText().toString().trim());
                    txt_email.setText(user.getEmail());
                } else if (retCode == 2) {
                    Toast.makeText(this, "绑定失败", Toast.LENGTH_SHORT).show();
                    emailModifyTable.setVisibility(View.VISIBLE);
                    emailModifyBtn.setVisibility(View.GONE);
                } else if (retCode == 3) {
                    Toast.makeText(this, "连接失败", Toast.LENGTH_SHORT).show();
                    emailModifyTable.setVisibility(View.VISIBLE);
                    emailModifyBtn.setVisibility(View.GONE);
                } else if (retCode == 4) {
                    Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
                    emailModifyTable.setVisibility(View.VISIBLE);
                    emailModifyBtn.setVisibility(View.GONE);
                }
                break;
            case R.id.btn_changePsw:
                if (inputOldPsw.getText().toString().trim().equals("")) {
                    Toast.makeText(this, "请输入旧密码", Toast.LENGTH_SHORT).show();
                    passwordModifyTable.setVisibility(View.VISIBLE);
                    passwordModifyBtn.setVisibility(View.GONE);
                    break;
                }
                if (inputNewPsw.getText().toString().trim().equals("")) {
                    Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
                    passwordModifyTable.setVisibility(View.VISIBLE);
                    passwordModifyBtn.setVisibility(View.GONE);
                    break;
                }
                if (inputAgainPsw.getText().toString().trim().equals("")) {
                    Toast.makeText(this, "请再次输入新密码", Toast.LENGTH_SHORT).show();
                    passwordModifyTable.setVisibility(View.VISIBLE);
                    passwordModifyBtn.setVisibility(View.GONE);
                    break;
                }
                if (!inputNewPsw.getText().toString().trim().equals(inputAgainPsw.getText().toString().trim())) {
                    Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    passwordModifyTable.setVisibility(View.VISIBLE);
                    passwordModifyBtn.setVisibility(View.GONE);
                    break;
                }
                if (!inputOldPsw.getText().toString().trim().equals(user.getPsw())) {
                    Toast.makeText(this, "旧密码错误", Toast.LENGTH_SHORT).show();
                    passwordModifyTable.setVisibility(View.VISIBLE);
                    passwordModifyBtn.setVisibility(View.GONE);
                    break;
                }
                Thread threadChangePsw = new Thread() {
                    @Override
                    public void run() {
                        if (inputNewPsw.getText().toString().trim().equals(inputAgainPsw.getText().toString().trim())) {
                            retCode = new UserInfoUpdateBiz().passwordUpdate(user.getUid(), user.getPsw(), inputAgainPsw.getText().toString().trim());
                        } else {
                            retCode = 4;
                        }
                    }
                };
                threadChangePsw.start();
                try {
                    threadChangePsw.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (retCode == 1) {
                    Toast.makeText(this, "更改密码成功", Toast.LENGTH_SHORT).show();
                    user.setPsw(inputAgainPsw.getText().toString().trim());
                } else if (retCode == 2) {
                    Toast.makeText(this, "旧密码失败", Toast.LENGTH_SHORT).show();
                    passwordModifyTable.setVisibility(View.VISIBLE);
                    passwordModifyBtn.setVisibility(View.GONE);
                } else if (retCode == 3) {
                    Toast.makeText(this, "连接失败", Toast.LENGTH_SHORT).show();
                    passwordModifyTable.setVisibility(View.VISIBLE);
                    passwordModifyBtn.setVisibility(View.GONE);
                } else if (retCode == 4) {
                    Toast.makeText(this, "更改密码失败", Toast.LENGTH_SHORT).show();
                    passwordModifyTable.setVisibility(View.VISIBLE);
                    passwordModifyBtn.setVisibility(View.GONE);
                }
                break;


        }
    }

}
