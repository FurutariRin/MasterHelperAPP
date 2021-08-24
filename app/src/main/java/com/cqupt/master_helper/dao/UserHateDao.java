package com.cqupt.master_helper.dao;

import com.cqupt.master_helper.entity.UserHate;
import com.cqupt.master_helper.entity.UserLike;
import com.cqupt.master_helper.utils.DruidUtils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;

public class UserHateDao {


    /**
     * 点踩
     *
     * @param uid      账号
     * @param hate_vid 点踩视频号
     * @param event    1 点踩事件<br>2 取消点踩事件
     * @return Code 0 点踩成功<br>Code 1 点踩失败<br>Code 2 事件错误<br>Code 3 连接错误
     */
    public int hateVideoServer(String uid, String hate_vid, int event) {
        QueryRunner queryRunner = new QueryRunner();

        int retCode = 1;
        if (event == 1) {
            Connection connection = null;
            try {
                connection = DruidUtils.getConnection();
                connection.setAutoCommit(false);
                queryRunner.update(connection,
                        "INSERT INTO project1.user_hate(uid, hate_vid) VALUES (?, ?)",
                        uid, hate_vid);
                int i = queryRunner.update(connection, "UPDATE project1.video SET hate_num = hate_num + 1 WHERE vid = ?",
                        hate_vid);
                connection.commit();
                if (i > 0) {
                    System.out.println("点踩" + hate_vid + "成功");
                    retCode = 0;
                } else {
                    System.out.println("点踩" + hate_vid + "失败");
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
                        "DELETE FROM project1.user_hate WHERE uid = ? AND hate_vid = ?",
                        uid, hate_vid);
                int i = queryRunner.update(connection,
                        "UPDATE project1.video SET hate_num = hate_num - 1 WHERE vid = ?",
                        hate_vid);
                connection.commit();
                if (i > 0) {
                    System.out.println("取消点踩" + hate_vid + "成功");
                    retCode = 0;
                } else {
                    System.out.println("取消点踩" + hate_vid + "失败");
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
     * 是否点踩
     *
     * @param uid 账号
     * @param vid 视频号
     * @return True 已点踩<br>False 未点踩
     */
    public boolean haveHate(String uid, String vid) {
        boolean retCode = false;
        QueryRunner queryRunner = new QueryRunner();

        try {
            UserLike userLike = new UserLike();
            userLike = queryRunner.query(DruidUtils.getConnection(),
                    "SELECT * FROM project1.user_hate WHERE uid = ? AND hate_vid = ?",
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
