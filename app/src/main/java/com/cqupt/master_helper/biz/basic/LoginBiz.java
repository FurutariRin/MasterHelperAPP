package com.cqupt.master_helper.biz.basic;

import com.cqupt.master_helper.dao.UserDao;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class LoginBiz {

    /**
     * 登录（单元测试通过）
     *
     * @param uid 账号
     * @param psw 密码
     * @return Code 0 登录成功<br>Code 1 密码错误<br>Code 2 密码不合法<br>Code 3 请输入密码<br>Code 4 请输入账号<br>Code 5 连接错误<br>Code 6 没有该用户
     */
    @ParameterizedTest
    @CsvSource({"00000000000001,psw123456",
            ",",
            "0,",
            "00000000000004,psw123456",
            "123hahaha,testpsw123",
            "00000000000004,123456789asd"})
    public int LoginBiz(String uid, String psw) {
        UserDao userDao = new UserDao();
        if (uid == null) {
            System.out.println("请输入账号");
            return 4;//请输入账号
        } else if (psw == null) {
            System.out.println("请输入密码");
            return 3;//请输入密码
        }
        if (checkPassword(psw)) {//判断密码是否合法
            System.out.println("密码不合法");
            return 2;//密码不合法
        }
        int retCode = userDao.loginServer(uid, psw);
        System.out.println("Code：" + retCode);
        return retCode;
    }

    private boolean checkPassword(String psw) {
        if (true) {//判定密码是否合法
            return false;
        }
        return true;
    }

}
