package com.cqupt.master_helper.biz.admin;

import com.cqupt.master_helper.entity.User;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdminSearchBizTest {

    @ParameterizedTest
    @CsvSource({" ,1",
            "杰哥,1"})
    void foundUser(String keyword, int page) {
        List<User> userList = new AdminSearchBiz().foundUser(keyword, page);
        System.out.println("============================");
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            System.out.println("UID：" + user.getUid());
            System.out.println("昵称：" + user.getUname());
            System.out.println("Email：" + user.getEmail());
            System.out.println("权限等级：" + user.getLevel());
            System.out.println("============================");
        }
    }
}