package com.cqupt.master_helper.entity;

import java.util.List;

/**
 * 定义VideoRelease类
 */
public class VideoRelease {

    /**
     * ID,主键
     */
    private Integer id;
    /**
     * 账号
     */
    private String uid;
    /**
     * 发布视频号
     */
    private String release_vid;
    /**
     * 发布时间
     */
    private String release_time;

    public Integer getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public String getRelease_vid() {
        return release_vid;
    }

    public String getRelease_time() {
        return release_time;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setRelease_vid(String release_vid) {
        this.release_vid = release_vid;
    }

    public void setRelease_time(String release_time) {
        this.release_time = release_time;
    }



    //    /**
//     * 获取该用户发布的视频
//     * @param uid 账号
//     * @return 发布视频集
//     */
//    public List<VideoRelease> getUserReleaseVideo(String uid){return null;}
//
//    /**
//     * 添加发布视频号<br>并添加发布时间
//     * @param uid 账号
//     * @param release_vid 发布视频号
//     * @return 是否成功
//     */
//    public int addReleaseVideo(String uid,String release_vid){return 0;}

}
