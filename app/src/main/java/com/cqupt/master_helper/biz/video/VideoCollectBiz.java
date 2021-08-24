package com.cqupt.master_helper.biz.video;

import com.cqupt.master_helper.dao.UserFavoriteDao;
import com.cqupt.master_helper.utils.DruidUtils;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.Connection;
import java.sql.SQLException;

public class VideoCollectBiz {

    public static final int EVENT_COLLECT = 1;
    public static final int EVENT_DISCOLLECT = 2;


    /**
     * 收藏（单元测试通过）
     *
     * @param uid   账号
     * @param vid   视频号
     * @param event 1 收藏事件<br>2 取消收藏事件
     * @return Code 0 收藏成功<br>Code 1 收藏失败<br>Code 2 事件错误<br>Code 3 连接错误
     */
    @ParameterizedTest
    @CsvSource({"李田所,野兽战车,1"})
    public int collect(String uid, String vid, int event) {
        UserFavoriteDao userFavoriteDao = new UserFavoriteDao();

        int retCode = 1;
        retCode = userFavoriteDao.collectServer(uid, vid, event);

        System.out.println("Code：" + retCode);

        return retCode;
    }
}
