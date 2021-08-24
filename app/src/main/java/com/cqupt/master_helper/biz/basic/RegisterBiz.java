package com.cqupt.master_helper.biz.basic;

import com.cqupt.master_helper.dao.UserDao;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class RegisterBiz {

    private String uid;


    /**
     * 判定密码并注册（单元测试成功）
     *
     * @param uname    昵称
     * @param psw      密码
     * @param pswAgain 重复密码
     * @return Code 0 注册成功<br>Code 1 密码不合法<br>Code 2 注册失败<br>Code 3 连接失败<br>Code 4 两次密码不一致<br>Code 5 请输入密码
     */
    @ParameterizedTest
    @CsvSource({"杰哥,admin1234,admin1234",
            "杰哥,admin1234,admin1234",
            "杰哥,admin1234,admin1234",
            "杰哥,admin1234,admin1234",
            "杰哥,admin1234,admin1234",
            "杰哥,admin1234,admin1234",
            "杰哥,admin1234,admin1234",
            "杰哥,admin1234,admin1234",
            "杰哥,admin1234,admin1234",
            "杰哥,admin1234,admin1234",
            "杰哥,admin1234,admin1234",
            "杰哥,admin1234,admin1234",
            "杰哥,admin1234,admin1234",
            "杰哥,admin1234,admin1234"})
    public int RegisterBiz(String uname, String psw, String pswAgain) {
        if (psw == null || pswAgain == null) {
            System.out.println("请输入密码");
            return 5;
        } else if (checkPassword(psw)) {
            System.out.println("密码不合法");
            return 1;//Code 1，密码不合法
        } else if (!psw.equals(pswAgain)) {
            System.out.println("两次密码不一致");
            return 4;//Code 4，两次密码不一致
        }
        UserDao userDao = new UserDao();
        int successRegister = 2;
        successRegister = userDao.registerServer(uname, psw);
        if (successRegister != 0) {
            System.out.println("注册失败");
            return successRegister;
        }
        uid = userDao.getUid();
        System.out.println("注册成功");
        return successRegister;//Code 0，正常运行
    }

    private boolean checkPassword(String psw) {
        if (true) {//判定密码是否合法
            return false;
        }
        return true;
    }

    public String getUid() {
        return this.uid;
    }

}
