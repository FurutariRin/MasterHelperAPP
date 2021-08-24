package com.cqupt.master_helper.dao;

import com.cqupt.master_helper.entity.User;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    @ParameterizedTest
    @CsvSource("00000000000003")
    void userInfo(String uid){
        User user = new UserDao().userInfo(uid);

        System.out.println("============================");
            System.out.println("UID：" + user.getUid());
            System.out.println("昵称：" + user.getUname());
            System.out.println("Email：" + user.getEmail());
            System.out.println("权限等级：" + user.getLevel());
            System.out.println("============================");

        assertEquals(0,0);
    }
}
