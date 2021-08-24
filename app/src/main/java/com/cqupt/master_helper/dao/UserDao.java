package com.cqupt.master_helper.dao;

import com.cqupt.master_helper.entity.User;
import com.cqupt.master_helper.utils.DruidUtils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private String uid;

    public String getUid() {
        return this.uid;
    }

    /**
     * 注册（单元测试通过）
     *
     * @param uname 昵称
     * @param psw   密码
     * @return Code 0 注册成功<br>Code 2 注册失败<br>Code 3 连接失败
     */
    @ParameterizedTest
    @CsvSource({"admin,psw123456"})
    public int registerServer(String uname, String psw) {
        QueryRunner queryRunner = new QueryRunner();

        String requestUid = null;
        try {
            User user = queryRunner.query(DruidUtils.getConnection(),
                    "SELECT * FROM project1.user ORDER BY uid DESC LIMIT 1",
                    new BeanHandler<>(User.class));
            if (user != null) {
                Format formatUid = new DecimalFormat("00000000000000");
                Long longUid = Long.parseLong(user.getUid());
                longUid = longUid + Long.parseLong("1");
                requestUid = formatUid.format(longUid);
            } else {
                requestUid = "00000000000001";
            }
            System.out.println("UID：" + requestUid);
        } catch (SQLException e) {
            e.printStackTrace();
            return 3;
        } finally {
            DruidUtils.closeSource();
        }
        if (uname.equals("")) {
            uname = requestUid;
        }
        uid = requestUid;
        try {
            int i = queryRunner.update(DruidUtils.getConnection(),
                    "INSERT INTO project1.user(uid, psw, uname, email, recommend_type, level) VALUES (?,?,?,?,?,?)",
                    requestUid, psw, uname, null, "编译原理", 2);
            if (i > 0) {
                System.out.println("插入成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 3;
        } finally {
            DruidUtils.closeSource();
        }
        return 0;
    }

    /**
     * 登录
     *
     * @param uid 账号
     * @param psw 密码
     * @return Code 0 登录成功<br>Code 1 密码错误<br>Code 5 连接错误<br>Code 6 没有该用户
     */
    public int loginServer(String uid, String psw) {
        QueryRunner queryRunner = new QueryRunner();

        String truePsw = null;
        try {
            User user = queryRunner.query(DruidUtils.getConnection(),
                    "SELECT psw FROM project1.user WHERE uid = ?",
                    new BeanHandler<>(User.class),
                    uid);
            if (user != null) {
                truePsw = user.getPsw();
            } else {
                System.out.println("没有该用户");
                return 6;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 5;
        } finally {
            DruidUtils.closeSource();
        }
        if (!truePsw.equals(psw)) {
            return 1;//Code 1 密码错误
        }
        return 0;
    }

    /**
     * 更改密码
     *
     * @param uid    账号
     * @param oldPsw 旧密码
     * @param newPsw 新密码
     * @return Code 1 更改成功<br>Code 2 旧密码错误<br>Code 3 连接失败
     */
    public int passwordUpdateServer(String uid, String oldPsw, String newPsw) {
        int retCode = 2;
        User user = new User();
        QueryRunner queryRunner = new QueryRunner();

        try {
            user = queryRunner.query(DruidUtils.getConnection(),
                    "SELECT * FROM project1.user WHERE uid = ?",
                    new BeanHandler<>(User.class),
                    uid);
            if (!user.getPsw().equals(oldPsw)) {
                System.out.println("旧密码错误");
                return 2;
            }
            int i = queryRunner.update(DruidUtils.getConnection(),
                    "UPDATE project1.user SET psw = ? WHERE uid = ?",
                    newPsw, uid);
            if (i > 0) {
                System.out.println("更新密码成功");
                retCode = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 3;
        } finally {
            DruidUtils.closeSource();
        }

        return retCode;
    }

    /**
     * 修改昵称
     *
     * @param uid      账号
     * @param newUname 新昵称
     * @return Code 1 修改成功<br>Code 2 修改失败<br>Code 3 连接失败
     */
    public int unameUpdateServer(String uid, String newUname) {
        int retCode = 2;
        QueryRunner queryRunner = new QueryRunner();

        try {
            int i = queryRunner.update(DruidUtils.getConnection(),
                    "UPDATE project1.user SET uname = ? WHERE uid = ?",
                    newUname, uid);
            if (i > 0) {
                System.out.println("更新昵称成功");
                retCode = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 3;
        } finally {
            DruidUtils.closeSource();
        }

        return retCode;
    }

    /**
     * 绑定邮箱
     *
     * @param uid   账号
     * @param email 邮箱
     * @return Code 1 绑定成功<br>Code 2 绑定失败<br>Code 3 连接失败
     */
    public int emailUpdateServer(String uid, String email) {
        int retCode = 2;
        QueryRunner queryRunner = new QueryRunner();
        User user = new User();

        try {
            int i = queryRunner.update(DruidUtils.getConnection(),
                    "UPDATE project1.user SET email = ? WHERE uid = ?",
                    email, uid);
            if (i > 0) {
                System.out.println("绑定邮箱成功");
                retCode = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 3;
        } finally {
            DruidUtils.closeSource();
        }

        return retCode;
    }

    /**
     * 搜索用户
     *
     * @param keywordList 关键词表
     * @return 用户表
     */
    public List<User> foundUser(List<String> keywordList) {
        List<User> userList = new ArrayList<>();
        QueryRunner queryRunner = new QueryRunner();

        try {
            String sql = "SELECT * FROM project1.user WHERE uname LIKE";

            if (keywordList.size() == 1) {
                sql += " '%" + keywordList.get(0) + "%'";
            } else {
                sql += " '%" + keywordList.get(0) + "%'";
                for (int i = 1; i < keywordList.size(); i++) {
                    sql += " AND uname LIKE '%" + keywordList.get(i) + "%'";
                }
            }
            userList = queryRunner.query(DruidUtils.getConnection(),
                    sql, new BeanListHandler<>(User.class));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtils.closeSource();
        }

        return userList;
    }

    /**
     * 修改用户权限（单元测试成功）
     *
     * @param uid         账号
     * @param changeLevel 修改权限
     * @return Code 1 修改成功<br>Code 2 修改失败<br>Code 3 连接失败
     */
    @ParameterizedTest
    @CsvSource("00000000000003,1")
    public int userLevelServer(String uid, int changeLevel) {
        int retCode = 2;
        QueryRunner queryRunner = new QueryRunner();

        try {
            int i = queryRunner.update(DruidUtils.getConnection(),
                    "UPDATE project1.user SET level = ? WHERE uid = ?",
                    changeLevel, uid);
            if (i > 0) {
                System.out.println("更改权限成功");
                retCode = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Code：3");
            return 3;
        } finally {
            DruidUtils.closeSource();
        }

        System.out.println("Code：" + retCode);

        return retCode;
    }

    /**
     * 获取用户信息
     *
     * @param uid UID
     * @return 用户信息
     */
    public User userInfo(String uid) {
        User user = new User();
        QueryRunner queryRunner = new QueryRunner();

        try {
            user = queryRunner.query(DruidUtils.getConnection(),
                    "SELECT * FROM project1.user WHERE uid = ?",
                    new BeanHandler<>(User.class),
                    uid);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtils.closeSource();
        }

        return user;
    }

}
