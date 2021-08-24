package com.cqupt.master_helper.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 定义Video类
 */
public class Video implements Serializable {
    /**
     * 视频号
     */
    private String vid;
    /**
     * 视频名
     */
    private String vname;
    /**
     * 视频简介
     */
    private String vintroduction;
    /**
     * 发布者
     */
    private String author_uid;
    /**
     * 视频路径
     */
    private String vpath;
    /**
     * 课程类型
     */
    private String course_type;
    /**
     * 热度
     */
    private BigDecimal hot_point;
    /**
     * 赞数
     */
    private Integer like_num;
    /**
     * 踩数
     */
    private Integer hate_num;
    /**
     * 视频可见性
     */
    private Boolean video_visible;
    /**
     * 图片路径
     */
    private String vpicture_path;

    public String getVid() {
        return vid;
    }

    public String getVname() {
        return vname;
    }

    public String getVintroduction() {
        return vintroduction;
    }

    public String getAuthor_uid() {
        return author_uid;
    }

    public String getVpath() {
        return vpath;
    }

    public String getCourse_type() {
        return course_type;
    }

    public BigDecimal getHot_point() {
        return hot_point;
    }

    public Integer getLike_num() {
        return like_num;
    }

    public Integer getHate_num() {
        return hate_num;
    }

    public Boolean getVideo_visible() {
        return video_visible;
    }

    public String getVpicture_path() {
        return vpicture_path;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public void setVintroduction(String vintroduction) {
        this.vintroduction = vintroduction;
    }

    public void setAuthor_uid(String author_uid) {
        this.author_uid = author_uid;
    }

    public void setVpath(String vpath) {
        this.vpath = vpath;
    }

    public void setCourse_type(String course_type) {
        this.course_type = course_type;
    }

    public void setHot_point(BigDecimal hot_point) {
        this.hot_point = hot_point;
    }

    public void setLike_num(Integer like_num) {
        this.like_num = like_num;
    }

    public void setHate_num(Integer hate_num) {
        this.hate_num = hate_num;
    }

    public void setVideo_visible(Boolean video_visible) {
        this.video_visible = video_visible;
    }

    public void setVpicture_path(String vpicture_path) {
        this.vpicture_path = vpicture_path;
    }



    //    /**
//     * 用课程类型获取推荐视频号
//     * @param course_type 课程类型
//     * @return 视频号集
//     */
//    public List<Video> useCourseTypeGetRecommendVid(String course_type){
//        List<Video> videoList = new ArrayList<>();
//        //推荐算法
//        //sql语句（获取视频号）
//        try {
//            //执行语句得到结果
//            //关闭连接
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return videoList;
//    }
//
//    /**
//     * 获取视频排行
//     * @return 视频排行集
//     */
//    public List<Video> getRankVid(){
//        List<Video> videoList = new ArrayList<>();
//        //sql语句（获取视频号）
//        try {
//            //执行语句得到结果
//            //关闭连接
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return videoList;
//    }
//
//    /**
//     * 获取视频数据
//     * @param vid 视频号
//     * @return 视频数据
//     */
//    public Video getVideoData(String vid){return null;}
//
//    /**
//     * 获取视频路径
//     * @param vid 视频号
//     * @return 视频路径
//     */
//    public String getVpath(String vid){
//        String vpath = null;//用sql语句获取vpath
//        //sql语句（获取视频路径）
//        try {
//            //执行语句得到结果
//            //关闭连接
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return vpath;
//    }
//
//
//    /**
//     * 获取搜索结果
//     * @param keyword 关键词
//     * @return 视频结果
//     */
//    public List<Video> getSearchVideoList(List<String> keyword){
//        List<Video> videoList = new ArrayList<>();//用sql语句获取所有搜索结果
//        //sql语句（获取搜索结果的所有数据）
//        try {
//            //执行语句得到结果
//            //关闭连接
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return videoList;
//    }
//
//    /**
//     * 获取发布视频数据
//     * @param videoReleaseList 发布视频号
//     * @return 发布视频数据集
//     */
//    public List<Video> getReleaseVideoData(List<VideoRelease> videoReleaseList){return null;}
//
//    /**
//     * 获取所有视频
//     * @return 所有视频
//     */
//    public List<Video> getAllVideo(){
//        List<Video> videoList = new ArrayList<>();
//        //sql语句
//        try {
//            //执行语句得到结果
//            //关闭连接
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return videoList;
//    }
//
//    /**
//     * 获取视频简介
//     * @param vid 视频号
//     * @return vintroduction 视频简介
//     */
//    public String getVintroduction(String vid){
//        String vintroduction = null;//用sql语句获取vintroduction
//        //sql语句（获取视频简介）
//        try {
//            //执行语句得到结果
//            //关闭连接
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return vintroduction;
//    }
//
//    /**
//     * 获取收藏视频数据
//     * @param userFavoriteList 收藏视频
//     * @return 收藏视频数据集
//     */
//    public List<Video> getCollectVideo(List<UserFavorite> userFavoriteList){return null;}
//
//    /**
//     * 添加视频号
//     * @param vid 视频号
//     * @return 是否成功
//     */
//    public int addVid(String vid){
//        //sql语句（添加新的视频号）
//        try {
//            //执行语句
//            //关闭连接
//        }catch (Exception e){
//            e.printStackTrace();
//            return -1;
//        }
//        return 0;
//    }
//
//    /**
//     *添加视频名
//     * @param vid 视频号
//     * @param vname 视频名
//     * @return 是否成功
//     */
//    public int addVname(String vid,String vname){
//        //sql语句（获取用户输入的视频名
//        try {
//            //执行语句
//            //关闭连接
//        }catch (Exception e){
//            e.printStackTrace();
//            return -1;
//        }
//        return 0;
//    }
//
//    /**
//     *添加视频简介
//     * @param vid 视频号
//     * @param vintroduction 视频简介
//     * @return 是否成功
//     */
//    public int addVintroduction(String vid,String vintroduction){
//        //sql语句（添加用户输入的视频简介）
//        try {
//            //执行语句
//            //关闭连接
//        }catch (Exception e){
//            e.printStackTrace();
//            return -1;
//        }
//        return 0;
//    }
//
//    /**
//     * 添加发布者
//     * @param vid 视频号
//     * @param author_uid 发布者
//     * @return 是否成功
//     */
//    public int addAuthorUid(String vid,String author_uid){
//        //sql语句(添加获取到的用户uid)
//        try {
//            //执行语句
//            //关闭连接
//        }catch (Exception e){
//            e.printStackTrace();
//            return -1;
//        }
//        return 0;
//    }
//
//    /**
//     * 添加视频路径
//     * @param vid 视频号
//     * @param vpath 视频路径
//     * @return 是否成功
//     */
//    public int addVpath(String vid,String vpath){
//        //sql语句（添加获取到的视频路径）
//        try {
//            //执行语句
//            //关闭连接
//        }catch (Exception e){
//            e.printStackTrace();
//            return -1;
//        }
//        return 0;
//    }
//
//    /**
//     * 添加课程类型
//     * @param vid 视频号
//     * @param course_type 课程类型
//     * @return 是否成功
//     */
//    public int addCourseType(String vid,String course_type){
//        if(course_type == null){
//            //sql语句（添加为其他）
//            try {
//                //执行语句
//                //关闭连接
//            }catch (Exception e){
//                e.printStackTrace();
//                return -1;
//            }
//        }
//        else {
//            //sql语句（添加用户选择的课程）
//            try {
//                //执行语句
//                //关闭连接
//            } catch (Exception e) {
//                e.printStackTrace();
//                return -1;
//            }
//        }
//        return 0;
//    }
//
//    /**
//     * 添加初始热度
//     * @param vid 视频号
//     * @param hot_point 热度
//     * @return 是否成功
//     */
//    public int addHotPoint(String vid,BigDecimal hot_point){//建议设定为固定的热度（指初始热度）
//        hot_point.setScale(2,BigDecimal.ROUND_HALF_DOWN);
//        //sql语句（添加热度属性）
//        try {
//            //执行语句
//            //关闭连接
//        }catch (Exception e){
//            e.printStackTrace();
//            return -1;
//        }
//        return 0;
//    }
//
//    /**
//     * 添加初始赞数
//     * @param vid 视频号
//     * @return 是否成功
//     */
//    public int addLikeNum(String vid){
//        //sql语句（默认赞数0）
//        try {
//            //执行语句
//            //关闭连接
//        }catch (Exception e){
//            e.printStackTrace();
//            return -1;
//        }
//        return 0;
//    }
//
//    /**
//     * 添加初始踩数
//     * @param vid 视频号
//     * @return 是否成功
//     */
//    public int addHateNum(String vid){
//        //sql语句(默认踩数0)
//        try {
//            //执行语句
//            //关闭连接
//        }catch (Exception e){
//            e.printStackTrace();
//            return -1;
//        }
//        return 0;
//    }
//
//    /**
//     * 添加视频可见性
//     * @param vid 视频号
//     * @return 是否成功
//     */
//    public int addVideoVisible(String vid){
//        //sql语句（添加为true）
//        try {
//            //执行语句
//            //关闭连接
//        }catch (Exception e){
//            e.printStackTrace();
//            return -1;
//        }
//        return 0;
//    }
//
//    /**
//     * 添加图片路径
//     * @param vid 视频号
//     * @param vpicture_path 图片路径
//     * @return 是否成功
//     */
//    public int addVpicturePath(String vid,String vpicture_path){
//        //sql语句（添加图片路径）
//        try {
//            //执行语句
//            //关闭连接
//        }catch (Exception e){
//            e.printStackTrace();
//            return -1;
//        }
//        return 0;
//    }
//
//    /**
//     * 修改热度
//     * @param vid 视频号
//     * @param hot_point 热度
//     * @return 是否成功
//     */
//    public int setHotPoint(String vid,BigDecimal hot_point){
//        hot_point.setScale(2,BigDecimal.ROUND_HALF_DOWN);//设定精度为小数点后2位四舍五入
//        //sql语句（修改由算法算出的新热度）
//        try {
//            //执行语句
//            //关闭连接
//        }catch (Exception e){
//            e.printStackTrace();
//            return -1;
//        }
//        return 0;
//    }
//
//    /**
//     * 增加赞数
//     * @param vid 视频号
//     * @return 是否成功
//     */
//    public int setLikeNum(String vid){
//        //sql语句（赞数+1）
//        try {
//            //执行语句
//            //关闭连接
//        }catch (Exception e){
//            e.printStackTrace();
//            return -1;
//        }
//        return 0;
//    }
//
//    /**
//     * 减少赞数
//     * @param vid 视频号
//     * @return 是否成功
//     */
//    public int minusLikeNum(String vid){
//        //sql语句（赞数-1）
//        try {
//            //执行语句
//            //关闭连接
//        }catch (Exception e){
//            e.printStackTrace();
//            return -1;
//        }
//        return 0;
//    }
//
//    /**
//     * 增加踩数
//     * @param vid 视频号
//     * @return 是否成功
//     */
//    public int setHateNum(String vid){
//        //sql语句（踩数+1）
//        try {
//            //执行语句
//            //关闭连接
//        }catch (Exception e){
//            e.printStackTrace();
//            return -1;
//        }
//        return 0;
//    }
//
//    /**
//     * 减少踩数
//     * @param vid 视频号
//     * @return 是否成功
//     */
//    public int minusHateNum(String vid){
//        //sql语句（踩数-1）
//        try {
//            //执行语句
//            //关闭连接
//        }catch (Exception e){
//            e.printStackTrace();
//            return -1;
//        }
//        return 0;
//    }
//
//    /**
//     * 设置视频不可见
//     * @param vid 视频号
//     * @return 是否成功
//     */
//    public int setVideoVisible(String vid){
//        //sql语句（视频可见性为false）
//        try {
//            //执行语句
//            //关闭连接
//        }catch (Exception e){
//            e.printStackTrace();
//            return -1;
//        }
//        return 0;
//    }

}
