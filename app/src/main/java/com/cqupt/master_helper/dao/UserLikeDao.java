package com.cqupt.master_helper.dao;

import com.cqupt.master_helper.entity.UserLike;
import com.cqupt.master_helper.utils.DruidUtils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;

public class UserLikeDao {

    /**
     * 点赞
     *
     * @param uid      账号
     * @param like_vid 点赞视频号
     * @param event    1 点赞事件<br>2 取消点赞事件
     * @return Code 0 点赞成功<br>Code 1 点赞失败<br>Code 2 事件错误<br>Code 3 连接错误
     */
    public int likeVideoServer(String uid, String like_vid, int event) {
        QueryRunner queryRunner = new QueryRunner();

        int retCode = 1;
        if (event == 1) {
            Connection connection = null;
            try {
                connection = DruidUtils.getConnection();
                connection.setAutoCommit(false);
                queryRunner.update(connection,
                        "INSERT INTO project1.user_like(uid, like_vid) VALUES (?, ?)",
                        uid, like_vid);
                int i = queryRunner.update(connection, "UPDATE project1.video SET like_num = like_num + 1 WHERE vid = ?",
                        like_vid);
                connection.commit();
                if (i > 0) {
                    System.out.println("点赞" + like_vid + "成功");
                    retCode = 0;
                } else {
                    System.out.println("点赞" + like_vid + "失败");
                    retCode = 1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.rollback();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                return 3;
            } finally {
                DruidUtils.closeSource();
            }
        } else if (event == 2) {
            Connection connection = null;
            try {
                connection = DruidUtils.getConnection();
                connection.setAutoCommit(false);
                queryRunner.update(connection,
                        "DELETE FROM project1.user_like WHERE uid = ? AND like_vid = ?",
                        uid, like_vid);
                int i = queryRunner.update(connection,
                        "UPDATE project1.video SET like_num = like_num - 1 WHERE vid = ?",
                        like_vid);
                connection.commit();
                if (i > 0) {
                    System.out.println("取消点赞" + like_vid + "成功");
                    retCode = 0;
                } else {
                    System.out.println("取消点赞" + like_vid + "失败");
                    retCode = 1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    connection.rollback();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                return 3;
            } finally {
                DruidUtils.closeSource();
            }
        }
        return retCode;
    }

    /**
     * 是否点赞
     *
     * @param uid 账号
     * @param vid 视频号
     * @return True 已点赞<br>False 未点赞
     */
    public boolean haveLike(String uid, String vid) {
        boolean retCode = false;
        QueryRunner queryRunner = new QueryRunner();

        try {
            UserLike userLike = new UserLike();
            userLike = queryRunner.query(DruidUtils.getConnection(),
                    "SELECT * FROM project1.user_like WHERE uid = ? AND like_vid = ?",
                    new BeanHandler<>(UserLike.class),
                    uid, vid);
            if (userLike == null) {
                retCode = false;
            } else {
                retCode = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtils.closeSource();
        }
        return retCode;
    }
}
