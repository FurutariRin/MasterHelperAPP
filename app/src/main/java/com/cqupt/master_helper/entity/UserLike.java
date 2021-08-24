package com.cqupt.master_helper.entity;

import java.util.List;

/**
 * 定义UserLike类
 */
public class UserLike {

    /**
     * ID
     */
    private Integer id;
    /**
     * 账号
     */
    private String uid;
    /**
     * 点赞视频号
     */
    private String like_vid;

    public Integer getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public String getLike_vid() {
        return like_vid;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setLike_vid(String like_vid) {
        this.like_vid = like_vid;
    }


    //    /**
//     * 添加点赞视频号
//     * @param uid 账号
//     * @param like_vid 点赞视频号
//     * @return 是否成功
//     */
//    public int addLikeVid(String uid,String like_vid){return 0;}
//
//    /**
//     * 删除点赞视频号
//     * @param uid 账号
//     * @param like_vid 点赞视频号
//     * @return 是否成功
//     */
//    public int deleteLikeVid(String uid,String like_vid){return 0;}
//
//    /**
//     * 查看是否点赞
//     * @param uid 账号
//     * @param like_vid 点赞视频号
//     * @return 1 未点赞<br>2 已点赞
//     */
//    public int findLikeVid(String uid,String like_vid){return 1;}

}
