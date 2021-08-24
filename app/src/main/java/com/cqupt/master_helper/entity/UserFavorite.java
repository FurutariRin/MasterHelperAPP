package com.cqupt.master_helper.entity;

import java.util.List;

/**
 * 定义UserFavorite类
 */
public class UserFavorite {

    /**
     * 外键 账号
     */
    private String uid;
    /**
     * 收藏视频号
     */
    private String favorite_vid;

    public String getUid() {
        return uid;
    }

    public String getFavorite_vid() {
        return favorite_vid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setFavorite_vid(String favorite_vid) {
        this.favorite_vid = favorite_vid;
    }






    //    /**
//     * 获取收藏视频号
//     * @param uid 账号
//     * @return 收藏视频号表
//     */
//    public List<UserFavorite>getFavoriteVid(String uid){return null;}
//
//    /**
//     * 添加收藏视频号
//     * @param uid 账号
//     * @param favorite_vid 收藏视频号
//     * @return 是否成功
//     */
//    public int addFavoriteVid(String uid,String favorite_vid){return 0;}
//
//    /**
//     * 删除收藏视频号
//     * @param uid 账号
//     * @param favorite_vid 收藏视频号
//     * @return 是否成功
//     */
//    public int deleteFavoriteVid(String uid,String favorite_vid){return 0;}
//
//    /**
//     * 查看是否关注
//     * @param uid 账号
//     * @param favorite_vid 收藏视频号
//     * @return 1 未收藏<br>2 已收藏
//     */
//    public int findFavoriteVid(String uid,String favorite_vid){return 1;}

}
