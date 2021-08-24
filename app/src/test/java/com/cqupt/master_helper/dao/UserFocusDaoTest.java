package com.cqupt.master_helper.dao;

import com.cqupt.master_helper.entity.User;
import com.cqupt.master_helper.entity.UserFocus;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserFocusDaoTest {

    @ParameterizedTest
    @CsvSource("田所浩二的UID")
    void findFocusUserServer(String uid) {
        List<UserFocus> userFocusList = new UserFocusDao().findFocusUserServer(uid);

        System.out.print("[");
        for (int i = 0; i < userFocusList.size(); i++) {
            UserFocus userFocus = userFocusList.get(i);
            User user = new UserDao().userInfo(userFocus.getFocus_uid());
            System.out.print(user.getUname());
        }
        System.out.print("]");

        assertEquals(0, 0);
    }
}