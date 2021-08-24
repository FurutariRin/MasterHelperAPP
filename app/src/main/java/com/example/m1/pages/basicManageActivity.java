package com.example.m1.pages;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cqupt.master_helper.R;
import com.cqupt.master_helper.dao.UserDao;
import com.cqupt.master_helper.dao.VideoDao;
import com.cqupt.master_helper.entity.User;
import com.cqupt.master_helper.entity.Video;
import com.example.m1.pages.manageSubPage.manageUserInfoActivity;
import com.example.m1.pages.manageSubPage.manageVideoInfoActivity;
import com.example.m1.pages.manageSubPage.reportManageActivity;

public class basicManageActivity extends AppCompatActivity implements View.OnClickListener {

    private Button userSearchButton;
    private Button videoSearchButton;
    private Button reportManageButton;

    private EditText userSearchBar;
    private EditText videoSearchBar;

    private Bundle bundle = new Bundle();

    private Video foundVideo;
    private User foundUser;


    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_manage);
        bindView();

    }

    private void bindView() {
        userSearchButton = findViewById(R.id.videoSearchBtn);
        videoSearchButton = findViewById(R.id.userSearchBtn);
        reportManageButton = findViewById(R.id.reportManageBtn);

        userSearchBar = findViewById(R.id.userSearchBar);
        videoSearchBar = findViewById(R.id.videoSearchBar);

        userSearchButton.setOnClickListener(this);
        videoSearchButton.setOnClickListener(this);
        reportManageButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.videoSearchBtn:
                if (videoSearchBar.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入内容！", Toast.LENGTH_SHORT).show();
                } else {
                    Thread threadFindVideo = new Thread() {
                        @Override
                        public void run() {
                            foundVideo = new VideoDao().videoInfo(videoSearchBar.getText().toString().trim());
                        }
                    };
                    threadFindVideo.start();
                    try {
                        threadFindVideo.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (foundVideo == null) {
                        Toast.makeText(this, "未找到视频", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    Intent manageVideoInfoActivity = new Intent();
                    manageVideoInfoActivity.setClass(this, manageVideoInfoActivity.class);
                    manageVideoInfoActivity.putExtra("VideoData", foundVideo);
                    bundle.putString("SearchName", videoSearchBar.getText().toString());
                    this.startActivity(manageVideoInfoActivity, bundle);
                }
                break;
            case R.id.userSearchBtn:
                if (userSearchBar.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入内容！", Toast.LENGTH_SHORT).show();
                } else {
                    Thread threadFindUser = new Thread() {
                        @Override
                        public void run() {
                            foundUser = new UserDao().userInfo(userSearchBar.getText().toString().trim());
                        }
                    };
                    threadFindUser.start();
                    try {
                        threadFindUser.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (foundUser == null) {
                        Toast.makeText(this, "未找到用户", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    Intent manageUserInfoActivity = new Intent();
                    manageUserInfoActivity.setClass(this, manageUserInfoActivity.class);
                    manageUserInfoActivity.putExtra("UserData", foundUser);
                    bundle.putString("SearchName", userSearchBar.getText().toString());
                    this.startActivity(manageUserInfoActivity, bundle);
                }

                break;
            case R.id.reportManageBtn:
                Intent reportManageActivity = new Intent();
                reportManageActivity.setClass(this, reportManageActivity.class);
                this.startActivity(reportManageActivity);
        }

    }
}