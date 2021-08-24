package com.cqupt.master_helper.biz.dynamic;

import com.cqupt.master_helper.dao.VideoDao;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class UploadVideoBiz {

    /**
     * 上传视频数据库类（单元测试通过）
     *
     * @param vname         视频名
     * @param vintroduction 视频简介
     * @param author_uid    发布者
     * @param vpath         视频路径
     * @param course_type   课程类型
     * @param vpicture_path 图片路径
     * @return Code 0 上传成功<br>Code 1 连接失败<br>Code 2 上传失败
     */
    @ParameterizedTest
    @CsvSource({"亚历山大绝境战战士开荒实录,我觉得不用介绍,00000000000002,不知道怎么获取到的视频仓库地址,龙学,算是个图片地址吧",
            "绝亚各种奇怪的死法,蒙古上单偷袭日本天皇,蛤,vpath,龙学,vp",
            "绝亚战士MTlogs99,,00000000000003,视频地址,龙学,图片地址"})
    public int UploadVideoBiz(String vname, String vintroduction, String author_uid, String vpath, String course_type, String vpicture_path) {

        VideoDao videoDao = new VideoDao();
        int retCode = videoDao.uploadVideoServer(vname, vintroduction, author_uid, vpath, course_type, vpicture_path);
        System.out.println("Code：" + retCode);
        return retCode;
    }
}
