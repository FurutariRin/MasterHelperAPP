package com.cqupt.master_helper.biz.basic;

import com.cqupt.master_helper.dao.UserDao;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class UserInfoUpdateBiz {

    /**
     * 更改密码（单元测试通过）
     *
     * @param uid    账号
     * @param oldPsw 旧密码
     * @param newPsw 新密码
     * @return Code 1 更改成功<br>Code 2 旧密码错误<br>Code 3 连接失败
     */
    @ParameterizedTest
    @CsvSource("00000000000003,psw1234,haha1234")
    public int passwordUpdate(String uid, String oldPsw, String newPsw) {
        int retCode;
        UserDao userDao = new UserDao();

        retCode = userDao.passwordUpdateServer(uid, oldPsw, newPsw);

        System.out.println("Code：" + retCode);

        return retCode;
    }

    /**
     * 更改昵称（单元测试通过）
     *
     * @param uid      账号
     * @param newUname 新昵称
     * @return Code 1 更改成功<br>Code 2 修改失败<br>Code 3 连接失败
     */
    @ParameterizedTest
    @CsvSource("00000000000003,喵喵")
    public int unameUpdate(String uid, String newUname) {
        int retCode;
        UserDao userDao = new UserDao();

        retCode = userDao.unameUpdateServer(uid, newUname);

        System.out.println("Code：" + retCode);

        return retCode;
    }

    /**
     * 绑定邮箱（单元测试通过）
     *
     * @param uid   账号
     * @param email 邮箱
     * @return Code 1 绑定成功<br>Code 2 绑定失败<br>Code 3 连接失败
     */
    public int addEmail(String uid, String email) {
        int retCode = 2;
        UserDao userDao = new UserDao();

        retCode = userDao.emailUpdateServer(uid, email);

        System.out.println("Code：" + retCode);

        return retCode;
    }
}
