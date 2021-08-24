package com.cqupt.master_helper.biz.report;

import com.cqupt.master_helper.dao.UserReportDao;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class VideoReportBiz {


    /**
     * 举报视频（单元测试通过）
     *
     * @param vid 视频号
     * @return Code 0 举报成功<br>Code 1 举报失败<br>Code 2 事件错误
     */
    @ParameterizedTest
    @CsvSource({"00000000000018", "00000000000019"})
    public int VideoReportBiz(String vid) {
        UserReportDao userReportDao = new UserReportDao();

        int event = userReportDao.foundReportVideoRecord(vid);

        int retCode = 1;
        if (event == 1) {
            retCode = userReportDao.newReportVideoServer(vid);
        } else if (event == 2) {
            retCode = userReportDao.addReportVideoServer(vid);
        } else {
            retCode = 2;
            System.out.println("事件错误");
        }
        System.out.println("Code：" + retCode);
        return retCode;
    }
}
