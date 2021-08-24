package com.example.m1.pages.manageSubPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cqupt.master_helper.R;
import com.cqupt.master_helper.dao.UserDao;
import com.cqupt.master_helper.entity.User;

public class manageUserInfoActivity extends AppCompatActivity {

    private TextView txt_uid;
    private TextView txt_uname;
    private TextView txt_email;
    private TextView txt_recommend;
    private TextView txt_level;

    private Spinner spn_level;

    private Button btn_changeLevel;

    private User foundUser;
    private int retCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user_info);

        Intent intent = getIntent();
        foundUser = (User) intent.getSerializableExtra("UserData");

        load();
        click();
    }

    private void load() {
        txt_uid = this.findViewById(R.id.txt_searchUid);
        txt_uname = this.findViewById(R.id.txt_searchUname);
        txt_email = this.findViewById(R.id.txt_searchEmail);
        txt_recommend = this.findViewById(R.id.txt_searchRecommendType);
        txt_level = this.findViewById(R.id.txt_searchLevel);
        spn_level = this.findViewById(R.id.spn_searchLevel);
        btn_changeLevel = this.findViewById(R.id.btn_changeLevel);

        txt_uid.setText(foundUser.getUid());
        txt_uname.setText(foundUser.getUname());
        if (foundUser.getEmail() == null) {
            txt_email.setText("未绑定邮箱");
        } else {
            txt_email.setText(foundUser.getEmail());
        }
        if (foundUser.getLevel().equals(1)) {
            spn_level.setSelection(1);
            txt_level.setText(spn_level.getSelectedItem().toString().trim());
        } else if (foundUser.getLevel().equals(2)) {
            spn_level.setSelection(0);
            txt_level.setText(spn_level.getSelectedItem().toString().trim());
        } else if (foundUser.getLevel().equals(3)) {
            spn_level.setVisibility(View.GONE);
            txt_level.setText("管理员");
        }
    }

    private void click() {
        btn_changeLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txt_level.toString().trim().equals(spn_level.getSelectedItem().toString().trim())) {
                    if (spn_level.getSelectedItem().toString().trim().equals("普通用户")) {
                        if (foundUser.getLevel().equals(1)) {
                            Thread threadNoBan = new Thread() {
                                @Override
                                public void run() {
                                    retCode = new UserDao().userLevelServer(foundUser.getUid(), 2);
                                }
                            };
                            threadNoBan.start();
                            try {
                                threadNoBan.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (retCode == 1) {
                                Toast.makeText(manageUserInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                foundUser.setLevel(2);
                                txt_level.setText(spn_level.getSelectedItem().toString().trim());
                            } else if (retCode == 2) {
                                Toast.makeText(manageUserInfoActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                            } else if (retCode == 3) {
                                Toast.makeText(manageUserInfoActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else if (spn_level.getSelectedItem().toString().trim().equals("禁言用户")) {
                        if (foundUser.getLevel().equals(2)) {
                            Thread threadBan = new Thread() {
                                @Override
                                public void run() {
                                    retCode = new UserDao().userLevelServer(foundUser.getUid(), 1);
                                }
                            };
                            threadBan.start();
                            try {
                                threadBan.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (retCode == 1) {
                                Toast.makeText(manageUserInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                foundUser.setLevel(1);
                                txt_level.setText(spn_level.getSelectedItem().toString().trim());
                            } else if (retCode == 2) {
                                Toast.makeText(manageUserInfoActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                            } else if (retCode == 3) {
                                Toast.makeText(manageUserInfoActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }
            }
        });

    }

}