package com.example.m1.bean;

import android.net.Uri;

import com.cqupt.master_helper.dao.UserDao;
import com.cqupt.master_helper.entity.Video;

import java.util.List;

public class recycleViewData {
    public int image;
    public String videoName;
    public String vid;
    public String uploaderName;
    public String authorUid;
    public int hot;
    public String videoUri;
    public String videoDesc;

    public recycleViewData(Video video){
        this.videoName = video.getVname();
        this.vid = video.getVid();
        this.uploaderName = new UserDao().userInfo(video.getAuthor_uid()).getUname();
        this.authorUid = video.getAuthor_uid();
        this.hot = video.getHot_point().intValue();
        this.videoUri = video.getVpath();
        this.videoDesc = video.getVintroduction();
    }

}
