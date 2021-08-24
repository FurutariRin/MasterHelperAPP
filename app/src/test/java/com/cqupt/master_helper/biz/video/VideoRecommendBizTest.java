package com.cqupt.master_helper.biz.video;

import com.cqupt.master_helper.entity.Video;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VideoRecommendBizTest {

    @ParameterizedTest
    @CsvSource({"00000000000001,1"})
    void videoRecommendBiz(String uid) {
        List<Video> videoList = new VideoRecommendBiz().VideoRecommend(uid);

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

    }
}