package com.example.m1.pages.manageSubPage;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cqupt.master_helper.R;
import com.cqupt.master_helper.dao.UserDao;
import com.cqupt.master_helper.dao.VideoDao;
import com.cqupt.master_helper.entity.Video;
import com.example.m1.adapter.adapterUtilities.adapterUtilities;

public class manageVideoInfoActivity extends AppCompatActivity {

    private TextView txt_vid;
    private TextView txt_vname;
    private TextView txt_authorID;
    private TextView txt_authorUname;
    private TextView txt_hint;

    private ImageView cover;

    private Button deleteVideo;

    private Video foundVideo;
    private String authorName;
    private int retCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_video_info);

        Intent intent = getIntent();
        foundVideo = (Video) intent.getSerializableExtra("VideoData");

        load();
        click();
    }

    private void load() {
        txt_vid = this.findViewById(R.id.Vid);
        txt_vname = this.findViewById(R.id.videoname);
        txt_authorID = this.findViewById(R.id.uploadUid);
        txt_authorUname = this.findViewById(R.id.uploadName);
        txt_hint = this.findViewById(R.id.text_manageUserUploadVideoName);
        deleteVideo = this.findViewById(R.id.deleteBtn);
        cover = this.findViewById(R.id.image_manageUserUploadCover);

        txt_vid.setText(foundVideo.getVid());
        txt_vname.setText(foundVideo.getVname());
        txt_authorID.setText(foundVideo.getAuthor_uid());
        adapterUtilities.genCover(cover, foundVideo.getVpath(), this);


        Thread thread = new Thread() {
            @Override
            public void run() {
                authorName = new UserDao().userInfo(foundVideo.getAuthor_uid()).getUname();
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        txt_authorUname.setText(authorName);
        if (foundVideo.getVideo_visible().equals(false)) {
            txt_hint.setText("已删除");
            deleteVideo.setVisibility(View.GONE);
        }
    }

    private void click() {
        deleteVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread threadDeleteVideo = new Thread() {
                    @Override
                    public void run() {
                        retCode = new VideoDao().videoDeleteServer(foundVideo.getVid());
                    }
                };
                threadDeleteVideo.start();
                try {
                    threadDeleteVideo.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (retCode == 1) {
                    Toast.makeText(manageVideoInfoActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    txt_hint.setText("已删除");
                    deleteVideo.setVisibility(View.GONE);
                } else if (retCode == 2) {
                    Toast.makeText(manageVideoInfoActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                } else if (retCode == 3) {
                    Toast.makeText(manageVideoInfoActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}