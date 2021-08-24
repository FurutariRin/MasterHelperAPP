package com.cqupt.master_helper.dao;

import com.cqupt.master_helper.entity.Video;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VideoDaoTest {

    @ParameterizedTest
    @CsvSource("00000000000004")
    void videoDeleteServer(String vid) {
        assertEquals(1, new VideoDao().videoDeleteServer(vid));
    }

    @ParameterizedTest
    @CsvSource("00000000000003,2")
    void reportVideoDeleteServer(String vid, Integer report_id) {
        assertEquals(1, new VideoDao().reportVideoDeleteServer(vid, report_id));
    }

    @ParameterizedTest
    @CsvSource("数据结构")
    void useCourseTypeFoundVideo(String course_type) {
        List<Video> videoList = new VideoDao().useCourseTypeFoundVideo(course_type);
        System.out.println("=============================");
        for (int i = 0; i < videoList.size(); i++) {
            Video video = videoList.get(i);
            System.out.println(video.getVname());
        }
        System.out.println("=============================");
        assertEquals(0, 0);
    }
}