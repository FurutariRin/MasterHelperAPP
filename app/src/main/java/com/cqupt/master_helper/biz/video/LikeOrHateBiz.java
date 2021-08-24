package com.cqupt.master_helper.biz.video;

import com.cqupt.master_helper.dao.UserHateDao;
import com.cqupt.master_helper.dao.UserLikeDao;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class LikeOrHateBiz {

    public static final int EVENT_OK = 1;
    public static final int EVENT_CANCEL = 2;

    public static final int LIKE = 1;
    public static final int HATE = 2;


    /**
     * 点赞/点踩，取消点赞/取消点踩（单元测试通过）
     *
     * @param uid          账号
     * @param vid          视频号
     * @param event        1 点赞/点踩事件<br>2 取消点赞/点踩事件
     * @param like_or_hate 1 点赞<br>2 点踩
     * @return Code 0 点赞/点踩成功<br>Code 1 点赞/点踩失败<br>Code 2 事件错误<br>Code 3 连接错误
     */
    @ParameterizedTest
    @CsvSource({"00000000000003,00000000000001,1,1",
            "00000000000002,00000000000002,1,2"})
    public int LikeOrHateBiz(String uid, String vid, int event, int like_or_hate) {
        UserLikeDao userLikeDao = new UserLikeDao();
        UserHateDao userHateDao = new UserHateDao();

        int retCode = 1;
        if (like_or_hate == 1) {
            retCode = userLikeDao.likeVideoServer(uid, vid, event);
        } else if (like_or_hate == 2) {
            retCode = userHateDao.hateVideoServer(uid, vid, event);
        } else {
            retCode = 2;
        }
        System.out.println("Code：" + retCode);
        return retCode;
    }

}
