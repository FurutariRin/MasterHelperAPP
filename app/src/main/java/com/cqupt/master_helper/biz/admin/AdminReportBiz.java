package com.cqupt.master_helper.biz.admin;

import com.cqupt.master_helper.dao.UserReportDao;
import com.cqupt.master_helper.entity.UserReport;

import java.util.ArrayList;
import java.util.List;

public class AdminReportBiz {

    private static final int LINE = 10;

    /**
     * 管理员查看举报
     *
     * @param page 页码
     * @return 举报集合
     */
    public List<UserReport> getAllReport(int page) {
        List<UserReport> userReportList = new UserReportDao().adminFoundReport();
        List<UserReport> userReportPage = new ArrayList<>();
        int allPage = userReportList.size() / LINE + 1;

        int n = 0;
        for (int i = (page - 1) * LINE; i < userReportList.size(); i++) {
            userReportPage.add(userReportList.get(i));
            n++;
            if (n >= 10) {
                break;
            }
        }

        return userReportPage;
    }

}
