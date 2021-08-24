package com.cqupt.master_helper.entity;

import java.util.List;

/**
 * 定义UserHate类
 */
public class UserHate {

    /**
     * ID
     */
    private Integer id;
    /**
     * 账号
     */
    private String uid;
    /**
     * 点踩视频号
     */
    private String hate_vid;

    public Integer getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public String getHate_vid() {
        return hate_vid;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setHate_vid(String hate_vid) {
        this.hate_vid = hate_vid;
    }


    //    /**
//     * 添加点踩视频号
//     * @param uid 账号
//     * @param hate_vid 点踩视频号
//     * @return 是否成功
//     */
//    public int addHateVid(String uid,String hate_vid){return 0;}
//
//    /**
//     * 删除点踩视频号
//     * @param uid 账号
//     * @param hate_vid 点踩视频号
//     * @return 是否成功
//     */
//    public int deleteHateVid(String uid,String hate_vid){return 0;}
//
//    /**
//     * 查看是否点踩
//     * @param uid 账号
//     * @param hate_vid 点踩视频号
//     * @return 1 未点踩<br>2 已点踩
//     */
//    public int findHateVid(String uid,String hate_vid){return 1;}

}
