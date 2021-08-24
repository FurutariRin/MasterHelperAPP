package com.cqupt.master_helper.dao;

import com.cqupt.master_helper.entity.UserFocus;
import com.cqupt.master_helper.utils.DruidUtils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserFocusDao {

    /**
     * 关注
     *
     * @param uid       账号
     * @param focus_uid 关注账号
     * @param event     1 关注事件<br>2 取关事件
     * @return Code 0 关注成功<br>Code 1 关注失败<br>Code 2 事件错误<br>Code 3 连接错误
     */
    public int focusAuthorServer(String uid, String focus_uid, int event) {
        QueryRunner queryRunner = new QueryRunner();

        int retCode = 1;
        if (event == 1) {
            try {
                int i = queryRunner.update(DruidUtils.getConnection(),
                        "INSERT INTO project1.user_focus(uid, focus_uid) VALUES (?, ?)",
                        uid, focus_uid);
                if (i > 0) {
                    System.out.println("关注" + focus_uid + "成功");
                    retCode = 0;
                } else {
                    System.out.println("关注" + focus_uid + "失败");
                    retCode = 1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return 3;
            } finally {
                DruidUtils.closeSource();
            }
        } else if (event == 2) {
            try {
                int i = queryRunner.update(DruidUtils.getConnection(),
                        "DELETE FROM project1.user_focus WHERE uid = ? AND focus_uid = ?",
                        uid, focus_uid);
                if (i > 0) {
                    System.out.println("取关" + focus_uid + "成功");
                    retCode = 0;
                } else {
                    System.out.println("取关" + focus_uid + "失败");
                    retCode = 1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return 3;
            } finally {
                DruidUtils.closeSource();
            }
        } else {
            retCode = 2;
            System.out.println("关注事件错误");
        }
        return retCode;
    }

    /**
     * 搜索关注用户
     *
     * @param uid 账号
     * @return 关注列表
     */
    public List<UserFocus> findFocusUserServer(String uid) {
        List<UserFocus> userFocusList = new ArrayList<>();
        QueryRunner queryRunner = new QueryRunner();

        try {
            userFocusList = queryRunner.query(DruidUtils.getConnection(),
                    "SELECT * FROM project1.user_focus WHERE uid = ?",
                    new BeanListHandler<>(UserFocus.class),
                    uid);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userFocusList;
    }

    /**
     * 查看是否关注
     *
     * @param uid        账号
     * @param author_uid 关注UID
     * @return True 已关注<br>False 未关注
     */
    public boolean findFocusUid(String uid, String author_uid) {
        boolean retCode = false;
        QueryRunner queryRunner = new QueryRunner();

        try {
            UserFocus userFocus = new UserFocus();
            userFocus = queryRunner.query(DruidUtils.getConnection(),
                    "SELECT * FROM project1.user_focus WHERE uid = ? AND focus_uid = ?",
                    new BeanHandler<>(UserFocus.class),
                    uid, author_uid);
            if (userFocus != null) {
                retCode = true;
            } else {
                retCode = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtils.closeSource();
        }
        return retCode;
    }

}
