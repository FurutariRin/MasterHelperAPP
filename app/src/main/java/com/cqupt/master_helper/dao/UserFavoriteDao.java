package com.cqupt.master_helper.dao;

import com.cqupt.master_helper.entity.UserFavorite;
import com.cqupt.master_helper.utils.DruidUtils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserFavoriteDao {

    /**
     * 收藏
     *
     * @param uid          账号
     * @param favorite_vid 收藏视频号
     * @param event        1 收藏事件<br>2 取消收藏事件
     * @return Code 0 事件成功<br>Code 1 事件失败<br>Code 2 事件错误<br>Code 3 连接错误
     */
    public int collectServer(String uid, String favorite_vid, int event) {
        QueryRunner queryRunner = new QueryRunner();

        int retCode = 1;
        if (event == 1) {
            try {
                int i = queryRunner.update(DruidUtils.getConnection(),
                        "INSERT INTO project1.user_favorite(uid, favorite_vid) VALUES (?, ?)",
                        uid, favorite_vid);
                if (i > 0) {
                    System.out.println("收藏" + favorite_vid + "成功");
                    retCode = 0;
                } else {
                    System.out.println("收藏" + favorite_vid + "失败");
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
                        "DELETE FROM project1.user_favorite WHERE uid = ? AND favorite_vid = ?",
                        uid, favorite_vid);
                if (i > 0) {
                    System.out.println("取消收藏" + favorite_vid + "成功");
                    retCode = 0;
                } else {
                    System.out.println("取消收藏" + favorite_vid + "失败");
                    retCode = 1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return 3;
            } finally {
                DruidUtils.closeSource();
            }
        }
        return retCode;
    }

    /**
     * 获取用户收藏列表
     *
     * @param uid 账号
     * @return 收藏列表
     */
    public List<UserFavorite> foundUserCollection(String uid) {
        List<UserFavorite> userFavoriteList = new ArrayList<>();
        QueryRunner queryRunner = new QueryRunner();

        try {
            userFavoriteList = queryRunner.query(DruidUtils.getConnection(),
                    "SELECT * FROM project1.user_favorite WHERE uid = ?",
                    new BeanListHandler<>(UserFavorite.class),
                    uid);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtils.closeSource();
        }

        return userFavoriteList;
    }

    /**
     * 是否收藏
     *
     * @param uid 账号
     * @param vid 视频号
     * @return True 已收藏<br>False 未收藏
     */
    public boolean haveCollect(String uid, String vid) {
        boolean retCode = false;
        QueryRunner queryRunner = new QueryRunner();

        try {
            UserFavorite userLike = new UserFavorite();
            userLike = queryRunner.query(DruidUtils.getConnection(),
                    "SELECT * FROM project1.user_favorite WHERE uid = ? AND favorite_vid = ?",
                    new BeanHandler<>(UserFavorite.class),
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
