package com.cqupt.master_helper.dao;

import com.cqupt.master_helper.entity.UserComment;

public class UserCommentDao {


    /**
     * 发布评论
     * @param uid 账号
     * @param vid 视频号
     * @param content 评论内容
     * @return Code 0 评论成功<br>Code 1 评论失败
     */
    public int commentServer(String uid,String vid,String content){
        UserComment userComment = new UserComment();

        int retCode = 1;

        String comment_id = null;
        //获取最大评论号，并赋值

//        int successAddCommentId = userComment.addCommentId(vid,comment_id);
//        int successAddCommentAuthor = userComment.addCommentAuthor(vid,comment_id,uid);
//        int successAddCommentContent = userComment.addCommentContent(vid,comment_id,content);
//        int successAddCommentVisible = userComment.addCommentVisible(vid,comment_id);

//        if(successAddCommentId != 0
//                && successAddCommentAuthor != 0
//                && successAddCommentContent != 0
//                && successAddCommentVisible != 0){
//            retCode = 1;
//        }else{
//            retCode = 0;
//        }
        return retCode;
    }
}
