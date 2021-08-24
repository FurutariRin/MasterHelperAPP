package com.cqupt.master_helper.dao;

import com.cqupt.master_helper.entity.UserReport;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserReportDaoTest {

    @Test
    void adminFoundReport() {
        List<UserReport> userReportList = new UserReportDao().adminFoundReport();
        System.out.println("=============================");
        for(int i=0;i<userReportList.size();i++){
            UserReport userReport = userReportList.get(i);
            System.out.println(userReport.getSomething_id());
        }
        System.out.println("=============================");
        assertEquals(0,0);
    }
}