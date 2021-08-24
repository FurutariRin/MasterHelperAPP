package com.cqupt.master_helper.dao;

import com.cqupt.master_helper.entity.User;
import com.cqupt.master_helper.entity.UserFocus;
import com.cqupt.master_helper.entity.UserReport;
import com.cqupt.master_helper.entity.Video;
import com.cqupt.master_helper.entity.VideoRelease;
import com.cqupt.master_helper.utils.DruidUtils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VideoDao {

    /**
     * 上传视频数据库类
     *
     * @param vname         视频名
     * @param vintroduction 视频简介
     * @param author_uid    发布者
     * @param vpath         视频路径
     * @param course_type   课程类型
     * @return Code 0 上传成功<br>Code 1 连接失败<br>Code 2 上传失败
     */
    public int uploadVideoServer(String vname, String vintroduction, String author_uid, String vpath, String course_type, String vpicture_path) {
        QueryRunner queryRunner = new QueryRunner();

        //上传视频

        String vid = null;
        try {
            Video video = queryRunner.query(DruidUtils.getConnection(),
                    "SELECT * FROM project1.video ORDER BY vid DESC LIMIT 1",
                    new BeanHandler<>(Video.class));//sql语句（获取最大的视频号）
            if (video != null) {
                Format formatVid = new DecimalFormat("00000000000000");
                Long longVid = Long.parseLong(video.getVid());
                vid = formatVid.format(longVid + Long.parseLong("1"));
            } else {
                vid = "00000000000001";
            }
            System.out.println("上传视频号：" + vid);
        } catch (SQLException e) {
            e.printStackTrace();
            return 1;//连接失败
        } finally {
            DruidUtils.closeSource();
        }
        BigDecimal hot_point = new BigDecimal(10000.0);//设定初始热度
        hot_point.setScale(2, BigDecimal.ROUND_HALF_DOWN);

        Connection connection = null;

        try {
            connection = DruidUtils.getConnection();
            connection.setAutoCommit(false);
            int i;
            i = queryRunner.update(connection,
                    "INSERT INTO project1.video(vid, vname, vintroduction, author_uid, vpath, course_type, hot_point, like_num, hate_num, video_visible, vpicture_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    vid, vname, vintroduction, author_uid, vpath, course_type, hot_point, 0, 0, 1, vpicture_path);
            if (i > 0) {
                System.out.println("video表插入成功");
            }
            i = queryRunner.update(connection,
                    "INSERT INTO project1.video_release(uid, release_vid, release_time) VALUES (?, ?, ?)",
                    author_uid, vid, new Timestamp(new Date().getTime()));
            connection.commit();
            if (i > 0) {
                System.out.println("video_release表插入成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return 2;//上传失败
        } finally {
            DruidUtils.closeSource();
        }
        return 0;//上传成功

    }


    /**
     * 搜索视频
     *
     * @param keywordList 用户输入的关键词
     * @return 搜索结果
     */
    public List<Video> searchServer(List<String> keywordList) {
        List<Video> videoList = new ArrayList<>();
        QueryRunner queryRunner = new QueryRunner();

        try {
            String sql = "SELECT * FROM project1.video WHERE video_visible = 1 AND vname LIKE";

            if (keywordList.size() == 1) {
                sql += " '%" + keywordList.get(0) + "%'";
            } else {
                sql += " '%" + keywordList.get(0) + "%'";
                for (int i = 1; i < keywordList.size(); i++) {
                    sql += " AND vname LIKE '%" + keywordList.get(i) + "%'";
                }
            }
            videoList = queryRunner.query(DruidUtils.getConnection(),
                    sql, new BeanListHandler<>(Video.class));
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("连接错误");
        } finally {
            DruidUtils.closeSource();
        }
        return videoList;
    }

    /**
     * 视频排名
     *
     * @return 视频排名列表
     */
    public List<Video> videoRankServer() {
        List<Video> videoList = new ArrayList<>();
        QueryRunner queryRunner = new QueryRunner();

        try {
            videoList = queryRunner.query(DruidUtils.getConnection(),
                    "SELECT * FROM project1.video WHERE video_visible = 1 ORDER BY hot_point DESC",
                    new BeanListHandler<>(Video.class));
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("连接错误");
        } finally {
            DruidUtils.closeSource();
        }

        return videoList;
    }

    /**
     * 获取视频信息
     *
     * @param vid 视频号
     * @return 视频信息
     */
    public Video videoInfo(String vid) {
        Video video = new Video();
        QueryRunner queryRunner = new QueryRunner();

        try {
            video = queryRunner.query(DruidUtils.getConnection(),
                    "SELECT * FROM project1.video WHERE vid = ?",
                    new BeanHandler<>(Video.class),
                    vid);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return video;
    }

    /**
     * 推荐视频表
     *
     * @param uid 账号
     * @return 推荐视频表
     */
    public List<Video> videoRecommendServer(String uid) {
        List<Video> videoList = new ArrayList<>();
        User user = new User();
        QueryRunner queryRunner = new QueryRunner();

        try {
            user = queryRunner.query(DruidUtils.getConnection(),
                    "SELECT * FROM project1.user WHERE uid = ?",
                    new BeanHandler<>(User.class),
                    uid);
            if (!user.getRecommend_type().equals("默认")) {
                videoList = queryRunner.query(DruidUtils.getConnection(),
                        "SELECT A.* FROM ( SELECT @line := @line + 1 AS line, project1.video.* FROM (SELECT @line := 0) v, project1.video WHERE course_type = ? ORDER BY hot_point DESC ) A WHERE line * 2 <= (SELECT COUNT(*) FROM project1.video WHERE course_type = ?) AND video_visible = 1",
                        new BeanListHandler<>(Video.class),
                        user.getRecommend_type(), user.getRecommend_type());
            }else {
                videoList = queryRunner.query(DruidUtils.getConnection(),
                        "SELECT A.* FROM ( SELECT @line := @line + 1 AS line, project1.video.* FROM (SELECT @line := 0) v, project1.video ORDER BY hot_point DESC ) A WHERE line * 2 <= (SELECT COUNT(*) FROM project1.video) AND video_visible = 1",
                        new BeanListHandler<>(Video.class));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("连接错误");
        } finally {
            DruidUtils.closeSource();
        }

        return videoList;
    }

    /**
     * 管理员搜索视频（单元测试通过）
     *
     * @param keywordList 用户输入的关键词
     * @return 搜索结果
     */
    public List<Video> adminSearchServer(List<String> keywordList) {
        List<Video> videoList = new ArrayList<>();
        QueryRunner queryRunner = new QueryRunner();

        try {
            String sql = "SELECT * FROM project1.video WHERE vname LIKE";

            if (keywordList.size() == 1) {
                sql += " '%" + keywordList.get(0) + "%'";
            } else {
                sql += " '%" + keywordList.get(0) + "%'";
                for (int i = 1; i < keywordList.size(); i++) {
                    sql += " AND vname LIKE '%" + keywordList.get(i) + "%'";
                }
            }
            videoList = queryRunner.query(DruidUtils.getConnection(),
                    sql, new BeanListHandler<>(Video.class));
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("连接错误");
        } finally {
            DruidUtils.closeSource();
        }
        return videoList;
    }


    /**
     * 管理员删除视频（单元测试通过）
     *
     * @param vid 视频号
     * @return Code 1 删除成功<br>Code 2 删除失败<br>Code 3 连接失败
     */
    public int videoDeleteServer(String vid) {
        int retCode = 2;
        QueryRunner queryRunner = new QueryRunner();

        try {
            int i = queryRunner.update(DruidUtils.getConnection(),
                    "UPDATE project1.video SET video_visible = 0 WHERE vid = ?",
                    vid);
            if (i > 0) {
                System.out.println("删除视频成功");
                retCode = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Code：3");
            return 3;
        }

        System.out.println("Code：" + retCode);

        return retCode;
    }

    /**
     * 管理员通过举报删除视频（单元测试通过）
     *
     * @param vid       视频号
     * @param report_id 举报号
     * @return Code 1 删除成功<br>Code 2 删除失败<br>Code 3 连接失败
     */
    public int reportVideoDeleteServer(String vid, Integer report_id) {
        int retCode = 2;
        QueryRunner queryRunner = new QueryRunner();
        Connection connection = null;

        try {
            connection = DruidUtils.getConnection();
            connection.setAutoCommit(false);
            queryRunner.update(connection,
                    "UPDATE project1.video SET video_visible = 0 WHERE vid = ?",
                    vid);
            int i = queryRunner.update(connection,
                    "DELETE FROM project1.user_report WHERE report_id = ?",
                    report_id);
            connection.commit();
            if (i > 0) {
                System.out.println("删除视频成功");
                retCode = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            System.out.println("Code：3");
            return 3;
        } finally {
            DruidUtils.closeSource();
        }

        System.out.println("Code：" + retCode);

        return retCode;
    }

    /**
     * 获取不同课程类型的视频
     *
     * @param course_type 课程类型
     * @return 视频列表
     */
    public List<Video> useCourseTypeFoundVideo(String course_type) {
        List<Video> videoList = new ArrayList<>();
        QueryRunner queryRunner = new QueryRunner();

        try {
            videoList = queryRunner.query(DruidUtils.getConnection(),
                    "SELECT * FROM project1.video WHERE course_type = ?",
                    new BeanListHandler<>(Video.class),
                    course_type);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtils.closeSource();
        }

        return videoList;
    }


}
