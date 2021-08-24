package com.cqupt.master_helper.entity;

import java.util.List;

/**
 * 定义UserComment类
 */
public class UserComment {

    /**
     * 外键 视频号
     */
    private String vid;
    /**
     * 评论号
     */
    private String comment_id;
    /**
     * 评论者
     */
    private String comment_author_id;
    /**
     * 评论内容
     */
    private String comment_content;
    /**
     * 评论可见性
     */
    private Boolean comment_visible;

    public String getVid() {
        return vid;
    }

    public String getComment_id() {
        return comment_id;
    }

    public String getComment_author_id() {
        return comment_author_id;
    }

    public String getComment_content() {
        return comment_content;
    }

    public Boolean getComment_visible() {
        return comment_visible;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public void setComment_author_id(String comment_author_id) {
        this.comment_author_id = comment_author_id;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public void setComment_visible(Boolean comment_visible) {
        this.comment_visible = comment_visible;
    }











    //    /**
//     * 添加评论号
//     * @param vid 视频号
//     * @param comment_id 评论号
//     * @return 是否成功
//     */
//    public int addCommentId(String vid,String comment_id){return 0;}
//
//    /**
//     * 添加评论者
//     * @param vid 视频号
//     * @param comment_id 评论号
//     * @param comment_author 评论者
//     * @return 是否成功
//     */
//    public int addCommentAuthor(String vid,String comment_id,String comment_author){return 0;}
//
//    /**
//     * 添加评论内容
//     * @param vid 视频号
//     * @param comment_id 评论号
//     * @param comment_content 评论内容
//     * @return 是否成功
//     */
//    public int addCommentContent(String vid,String comment_id,String comment_content){return 0;}
//
//    /**
//     * 添加评论可见性
//     * @param vid 视频号
//     * @param comment_id 评论号
//     * @return 是否成功
//     */
//    public int addCommentVisible(String vid,String comment_id){return 0;}
//
//    /**
//     * 修改评论可见性
//     * @param vid 视频号
//     * @param comment_id 评论号
//     * @return 是否成功
//     */
//    public int setCommentVisible(String vid,String comment_id){return 0;}
//
//    /**
//     * 获取该视频所有评论
//     * @param vid 视频号
//     * @return 所有评论集
//     */
//    public List<UserComment> getAllComment(String vid){return null;}

}
