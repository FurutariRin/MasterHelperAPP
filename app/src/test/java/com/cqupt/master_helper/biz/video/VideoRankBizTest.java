package com.cqupt.master_helper.biz.video;

import com.cqupt.master_helper.entity.Video;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VideoRankBizTest {

    @ParameterizedTest
    @CsvSource({"1"})
    void videoRank(int page) {
        List<Video> videoList = new VideoRankBiz().VideoRank();

        System.out.println("============================");
        for (int i = 0; i < videoList.size(); i++) {
            Video video = videoList.get(i);
            System.out.println("" + video.getVid());
            System.out.println("" + video.getVname());
            System.out.println("" + video.getVintroduction());
            System.out.println("" + video.getAuthor_uid());
            System.out.println("" + video.getVpath());
            System.out.println("" + video.getCourse_type());
            System.out.println("" + video.getHot_point());
            System.out.println("" + video.getLike_num());
            System.out.println("" + video.getHate_num());
            System.out.println("" + video.getVideo_visible());
            System.out.println("" + video.getVpicture_path());
            System.out.println("============================");
        }

    }
}