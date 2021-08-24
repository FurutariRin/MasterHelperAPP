package com.cqupt.master_helper.biz.admin;

import com.cqupt.master_helper.entity.UserReport;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdminReportBizTest {

    @ParameterizedTest
    @CsvSource("1")
    void getAllReport(int page) {
        List<UserReport> userReportList = new AdminReportBiz().getAllReport(page);
        System.out.println("=============================");
        for (int i = 0; i < userReportList.size(); i++) {
            UserReport userReport = userReportList.get(i);
            System.out.println(userReport.getSomething_id());
        }
        System.out.println("=============================");
    }
}