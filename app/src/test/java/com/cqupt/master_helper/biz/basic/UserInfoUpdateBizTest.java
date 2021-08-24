package com.cqupt.master_helper.biz.basic;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class UserInfoUpdateBizTest {

    @ParameterizedTest
    @CsvSource("00000000000004,1377430603@qq.com")
    void addEmail(String uid, String email) {
        assertEquals(1,new UserInfoUpdateBiz().addEmail(uid, email));
    }

}
