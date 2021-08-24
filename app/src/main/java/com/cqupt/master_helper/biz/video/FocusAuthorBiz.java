package com.cqupt.master_helper.biz.video;

import com.cqupt.master_helper.dao.UserFocusDao;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class FocusAuthorBiz {

    public static final int EVENT_FOCUS = 1;
    public static final int EVENT_DISFOCUS = 2;


    /**
     * 关注（单元测试通过）
     *
     * @param uid       账号
     * @param focus_uid 关注账号
     * @param event     1 关注事件<br>2 取关事件
     * @return Code 0 关注成功<br>Code 1 关注失败<br>Code 2 事件错误
     */
    @ParameterizedTest
    @CsvSource("济南屁王的UID,田所浩二的UID,1")
    public int FocusAuthorBiz(String uid, String focus_uid, int event) {
        UserFocusDao userFocusDao = new UserFocusDao();

        int retCode = 1;
        retCode = userFocusDao.focusAuthorServer(uid, focus_uid, event);
        System.out.println("Code：" + retCode);

        return retCode;
    }
}
