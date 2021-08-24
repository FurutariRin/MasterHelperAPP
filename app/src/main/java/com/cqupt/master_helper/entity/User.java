package com.cqupt.master_helper.entity;

import android.content.Intent;

import java.io.Serializable;
import java.util.List;

/**
 * 定义User类(implements Serializable后可以用intent传递)
 */
public class User implements Serializable {
    /**
     * 账号
     */
    private String uid;
    /**
     * 密码
     */
    private String psw;
    /**
     * 昵称
     */
    private String uname;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 推荐课程
     */
    private String recommend_type;
    /**
     * 权限状态
     */
    private Integer level;

    public String getUid() {
        return uid;
    }

    public String getPsw() {
        return psw;
    }

    public String getUname() {
        return uname;
    }

    public String getEmail() {
        return email;
    }

    public String getRecommend_type() {
        return recommend_type;
    }

    public Integer getLevel() {
        return level;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRecommend_type(String recommend_type) {
        this.recommend_type = recommend_type;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }





    //    /**
//     * 获取账号
//     * @return uid 账号
//     */
//    public String getUid(){return null;}
//
//    /**
//     * 获取密码
//     * @param uid 账号
//     * @return psw 密码
//     */
//    public String getPsw(String uid){return null;}
//
//    /**
//     * 获取昵称
//     * @param uid 账号
//     * @return uname 昵称
//     */
//    public String getUname(String uid){return null;}
//
//    /**
//     * 获取邮箱
//     * @param uid 账号
//     * @return email 邮箱
//     */
//    public String getEmail(String uid){return null;}
//
//    /**
//     * 获取推荐课程
//     * @param uid 账号
//     * @return recommend_type 推荐课程
//     */
//    public String getRecommendType(String uid){return null;}
//
//    /**
//     * 获取权限等级
//     * @param uid 账号
//     * @return level 权限等级
//     */
//    public Integer getLevel(String uid){return null;}
//
//    /**
//     * 获取关注用户数据
//     * @param userFavoriteList 关注用户
//     * @return 关注用户数据集
//     */
//    public List<User> getFocusUserData(List<UserFocus> userFavoriteList){return null;}
//
//    /**
//     * 添加账号
//     * @param uid 账号
//     * @return 是否成功
//     */
//    public int addUid(String uid){return 0;}
//
//    /**
//     * 添加密码
//     * @param psw 密码
//     * @return 是否成功
//     */
//    public int addPsw(String psw){return 0;}
//
//    /**
//     * 添加昵称
//     * @param uname 昵称
//     * @return 是否成功
//     */
//    public int addUname(String uname){return 0;}
//
//    /**
//     * 添加邮箱
//     * @param uid 账号
//     * @param email 邮箱
//     * @return 是否成功
//     */
//    public int addEmail(String uid,String email){return 0;}
//
//    /**
//     * 设置初始积分
//     * @return 是否成功
//     */
//    public int addPoint(){return 0;}
//
//    /**
//     * 添加默认推荐课程
//     * @return 是否成功
//     */
//    public int addRecommendType(){return 0;}
//
//    /**
//     * 添加默认权限等级
//     * @return 是否成功
//     */
//    public int addLevel(){return 0;}
//
//    /**
//     * 修改昵称
//     * @param uid 账号
//     * @param uname 昵称
//     * @return 是否成功
//     */
//    public int setUname(String uid,String uname){return 0;}
//
//    /**
//     * 修改密码
//     * @param uid 账号
//     * @param psw 密码
//     * @return 是否成功
//     */
//    public int setPsw(String uid,String psw){return 0;}
//
//    /**
//     * 修改权限等级
//     * @param uid 账号
//     * @param level 权限等级
//     * @return 是否成功
//     */
//    public int setLevel(String uid,Integer level){return 0;}
//
//    /**
//     * 解除邮箱绑定
//     * @param uid 账号
//     * @return 是否成功
//     */
//    public int deleteEmail(String uid){return 0;}

}
