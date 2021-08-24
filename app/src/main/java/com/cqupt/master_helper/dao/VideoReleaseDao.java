package com.cqupt.master_helper.dao;

import com.cqupt.master_helper.entity.VideoRelease;
import com.cqupt.master_helper.utils.DruidUtils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VideoReleaseDao {

    /**
     * 获取用户发布视频列表
     *
     * @param uid 账号
     * @return 用户发布视频列表
     */
    public List<VideoRelease> foundUserVideoRelease(String uid) {
        List<VideoRelease> videoReleaseList = new ArrayList<>();
        QueryRunner queryRunner = new QueryRunner();

        try {
            videoReleaseList = queryRunner.query(DruidUtils.getConnection(),
                    "SELECT * FROM project1.video_release WHERE uid = ?",
                    new BeanListHandler<>(VideoRelease.class),
                    uid);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtils.closeSource();
        }

        return videoReleaseList;
    }

}
