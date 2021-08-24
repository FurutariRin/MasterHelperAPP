package com.cqupt.master_helper.dao;

import com.cqupt.master_helper.biz.basic.RegisterBiz;
import com.cqupt.master_helper.biz.basic.UserInfoUpdateBiz;
import com.cqupt.master_helper.dao.UserDao;
import com.cqupt.master_helper.dao.VideoDao;
import com.cqupt.master_helper.entity.User;
import com.cqupt.master_helper.entity.Video;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        UserInfoUpdateBiz userInfoUpdateBiz = new UserInfoUpdateBiz();
        RegisterBiz registerBiz = new RegisterBiz();
        assertEquals(0, 0);
    }

    @ParameterizedTest
    @CsvSource("李田所和济南屁王达成交易")
    public void adminSearchServer(String keyword){
        String[] ss = keyword.split(" ");
        List<String> keywordList = new ArrayList<>(Arrays.asList(ss));

        VideoDao videoDao = new VideoDao();
        List<Video> videoList = videoDao.adminSearchServer(keywordList);

        System.out.println("============================");
        for (int i = 0; i < videoList.size(); i++) {
            Video video = videoList.get(i);
            System.out.println("视频号：" + video.getVid());
            System.out.println("视频名：" + video.getVname());
            System.out.println("视频简介：" + video.getVintroduction());
            System.out.println("作者UID：" + video.getAuthor_uid());
            System.out.println("视频路径：" + video.getVpath());
            System.out.println("课程类型：" + video.getCourse_type());
            System.out.println("热度：" + video.getHot_point());
            System.out.println("赞数：" + video.getLike_num());
            System.out.println("踩数：" + video.getHate_num());
            System.out.println("视频可见性：" + video.getVideo_visible());
            System.out.println("图片路径：" + video.getVpicture_path());
            System.out.println("============================");
        }
        assertEquals(0, 0);
    }

    @ParameterizedTest
    @CsvSource("00000000000003")
    public void adminDeleteVideo_isCorrect(String vid){
        assertEquals(1,new VideoDao().videoDeleteServer(vid));
    }
}