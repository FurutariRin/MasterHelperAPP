package com.example.m1.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cqupt.master_helper.R;
import com.cqupt.master_helper.biz.report.VideoReportBiz;
import com.cqupt.master_helper.biz.video.FocusAuthorBiz;
import com.cqupt.master_helper.biz.video.LikeOrHateBiz;
import com.cqupt.master_helper.biz.video.VideoCollectBiz;
import com.cqupt.master_helper.dao.UserFavoriteDao;
import com.cqupt.master_helper.dao.UserFocusDao;
import com.cqupt.master_helper.dao.UserHateDao;
import com.cqupt.master_helper.dao.UserLikeDao;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class videoPlayPageActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView videoPlayPageVideoName;
    private TextView txt_authorName;
    private TextView txt_introduction;
    private JzvdStd videoPlayPageVideoBody;

    private Context context;

    private Button focusUserBtn;
    private ImageView likeBtn;
    private ImageView dislikeBtn;
    private ImageView favoriteBtn;
    private ImageView shareBtn;
    private Button btn_report;

    private boolean likeBtnState = false;
    private boolean dislikeBtnState = false;
    private boolean favoriteBtnState = false;
    private boolean focusUserBtnState = false;

    private String author_uid;
    private String uid;
    private String vid;

    private int retCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplaypage);
        bindView();
    }


    private void bindView() {
        videoPlayPageVideoName = findViewById(R.id.videoPlayPageVideoName);
        txt_authorName = findViewById(R.id.txt_UploaderName);
        txt_introduction = findViewById(R.id.txt_introduction);
        videoPlayPageVideoBody = findViewById(R.id.videoPlayPageVideoBody);
        focusUserBtn = findViewById(R.id.videoPlayPageFocusUser);
        likeBtn = findViewById(R.id.videoPlayPageLike);
        dislikeBtn = findViewById(R.id.videoPlayPageDislike);
        favoriteBtn = findViewById(R.id.videoPlayPageFavorite);
        shareBtn = findViewById(R.id.videoPlayPageShare);
        btn_report = findViewById(R.id.btn_report);
        videoInit();

        Thread threadState = new Thread() {
            @Override
            public void run() {
                focusUserBtnState = new UserFocusDao().findFocusUid(uid, author_uid);
                likeBtnState = new UserLikeDao().haveLike(uid, vid);
                dislikeBtnState = new UserHateDao().haveHate(uid, vid);
                favoriteBtnState = new UserFavoriteDao().haveCollect(uid, vid);
            }
        };
        threadState.start();
        try {
            threadState.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        iconInit();
        context = getApplicationContext();

        focusUserBtn.setOnClickListener(this);
        likeBtn.setOnClickListener(this);
        dislikeBtn.setOnClickListener(this);
        favoriteBtn.setOnClickListener(this);
        shareBtn.setOnClickListener(this);
        btn_report.setOnClickListener(this);
    }


    //--------------------------------------------------------------------------------------------------
//播放器设置
    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    //--------------------------------------------------------------------------------------------------
    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.videoPlayPageFocusUser:
                if (focusUserBtnState == false) {
                    Thread threadFocus = new Thread() {
                        @Override
                        public void run() {
                            retCode = new FocusAuthorBiz().FocusAuthorBiz(uid, author_uid, FocusAuthorBiz.EVENT_FOCUS);
                        }
                    };
                    threadFocus.start();
                    try {
                        threadFocus.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (retCode == 0) {
                        focusUserBtnState = true;
                        focusUserBtn.setText("已关注");
                        toast("关注成功！");
                    } else if (retCode == 1) {
                        toast("关注失败");
                    } else if (retCode == 2) {
                        toast("事件错误");
                    }
                } else {
                    Thread threadDisFocus = new Thread() {
                        @Override
                        public void run() {
                            retCode = new FocusAuthorBiz().FocusAuthorBiz(uid, author_uid, FocusAuthorBiz.EVENT_DISFOCUS);
                        }
                    };
                    threadDisFocus.start();
                    try {
                        threadDisFocus.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (retCode == 0) {
                        focusUserBtnState = false;
                        focusUserBtn.setText("关注");
                        toast("取消关注");
                    } else if (retCode == 1) {
                        toast("取消关注失败");
                    } else if (retCode == 2) {
                        toast("事件错误");
                    }
                }
                break;
            case R.id.videoPlayPageLike:
                if (likeBtnState == false) {
                    Thread threadLike = new Thread() {
                        @Override
                        public void run() {
                            retCode = new LikeOrHateBiz().LikeOrHateBiz(uid, vid, LikeOrHateBiz.EVENT_OK, LikeOrHateBiz.LIKE);
                        }
                    };
                    threadLike.start();
                    try {
                        threadLike.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (retCode == 0) {
                        likeBtnState = true;
                        changeBtnColor(likeBtn, true);
                        toast("点赞成功！");
                    } else if (retCode == 1) {
                        toast("点赞失败");
                    } else if (retCode == 2) {
                        toast("事件错误");
                    } else if (retCode == 3) {
                        toast("连接错误");
                    }
                } else {
                    Thread threadDisLike = new Thread() {
                        @Override
                        public void run() {
                            retCode = new LikeOrHateBiz().LikeOrHateBiz(uid, vid, LikeOrHateBiz.EVENT_CANCEL, LikeOrHateBiz.LIKE);
                        }
                    };
                    threadDisLike.start();
                    try {
                        threadDisLike.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (retCode == 0) {
                        likeBtnState = false;
                        changeBtnColor(likeBtn, false);
                        toast("取消点赞");
                    } else if (retCode == 1) {
                        toast("取消点赞失败");
                    } else if (retCode == 2) {
                        toast("事件错误");
                    } else if (retCode == 3) {
                        toast("连接错误");
                    }
                }
                break;
            case R.id.videoPlayPageDislike:
                if (dislikeBtnState == false) {
                    Thread threadHate = new Thread() {
                        @Override
                        public void run() {
                            retCode = new LikeOrHateBiz().LikeOrHateBiz(uid, vid, LikeOrHateBiz.EVENT_OK, LikeOrHateBiz.HATE);
                        }
                    };
                    threadHate.start();
                    try {
                        threadHate.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (retCode == 0) {
                        dislikeBtnState = true;
                        changeBtnColor(dislikeBtn, true);
                        toast("点踩成功！");
                    } else if (retCode == 1) {
                        toast("点踩失败");
                    } else if (retCode == 2) {
                        toast("事件错误");
                    } else if (retCode == 3) {
                        toast("连接错误");
                    }
                } else {
                    Thread threadDisHate = new Thread() {
                        @Override
                        public void run() {
                            retCode = new LikeOrHateBiz().LikeOrHateBiz(uid, vid, LikeOrHateBiz.EVENT_CANCEL, LikeOrHateBiz.HATE);
                        }
                    };
                    threadDisHate.start();
                    try {
                        threadDisHate.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (retCode == 0) {
                        dislikeBtnState = false;
                        changeBtnColor(dislikeBtn, false);
                        toast("取消点踩");
                    } else if (retCode == 1) {
                        toast("取消点踩失败");
                    } else if (retCode == 2) {
                        toast("事件错误");
                    } else if (retCode == 3) {
                        toast("连接错误");
                    }
                }
                break;
            case R.id.videoPlayPageFavorite:
                if (favoriteBtnState == false) {
                    Thread threadCollect = new Thread() {
                        @Override
                        public void run() {
                            retCode = new VideoCollectBiz().collect(uid, vid, VideoCollectBiz.EVENT_COLLECT);
                        }
                    };
                    threadCollect.start();
                    try {
                        threadCollect.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (retCode == 0) {
                        favoriteBtnState = true;
                        changeBtnColor(favoriteBtn, true);
                        toast("收藏成功！");
                    } else if (retCode == 1) {
                        toast("收藏失败");
                    } else if (retCode == 2) {
                        toast("事件错误");
                    } else if (retCode == 3) {
                        toast("连接错误");
                    }
                } else {
                    Thread threadDisCollect = new Thread() {
                        @Override
                        public void run() {
                            retCode = new VideoCollectBiz().collect(uid, vid, VideoCollectBiz.EVENT_DISCOLLECT);
                        }
                    };
                    threadDisCollect.start();
                    try {
                        threadDisCollect.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (retCode == 0) {
                        favoriteBtnState = false;
                        changeBtnColor(favoriteBtn, false);
                        toast("取消收藏");
                    } else if (retCode == 1) {
                        toast("取消收藏失败");
                    } else if (retCode == 2) {
                        toast("事件错误");
                    } else if (retCode == 3) {
                        toast("连接错误");
                    }
                }
                break;
            case R.id.btn_report:
                Thread threadReport = new Thread() {
                    @Override
                    public void run() {
                        retCode = new VideoReportBiz().VideoReportBiz(vid);
                    }
                };
                threadReport.start();
                try {
                    threadReport.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (retCode == 0) {
                    toast("举报成功");
                } else if (retCode == 1) {
                    toast("举报失败");
                } else if (retCode == 2) {
                    toast("事件错误");
                }
        }


    }

    //点击事件的提示
    private void toast(String tips) {
        Toast.makeText(context, tips, Toast.LENGTH_SHORT).show();
    }

    //--------------------------------------------------------------------------------------------------
    //视频数据初始化
    private void videoInit() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        videoPlayPageVideoName.setText(bundle.getString("videoName"));
        txt_authorName.setText(bundle.getString("uploaderName"));
        txt_introduction.setText(bundle.getString("introduction"));
        videoPlayPageVideoBody.setUp(bundle.getString("videoUri"), bundle.getString("videoName"));
        author_uid = bundle.getString("authorUid");
        uid = bundle.getString("uid");
        vid = bundle.getString("vid");
    }

    //图标初始化
    private void iconInit() {
        changeBtnColor(likeBtn, likeBtnState);
        changeBtnColor(dislikeBtn, dislikeBtnState);
        changeBtnColor(favoriteBtn, favoriteBtnState);
        if (focusUserBtnState == true)
            focusUserBtn.setText("已关注");
    }

    //修改图标颜色
    private void changeBtnColor(ImageView v, boolean state) {
        if (state == true) {
            v.setColorFilter(0xFFADD8E6);
        } else {
            v.setColorFilter(0xFFe9e9e9);
        }
    }


    //图标颜色修改

}