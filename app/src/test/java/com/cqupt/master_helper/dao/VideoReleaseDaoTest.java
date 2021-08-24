package com.cqupt.master_helper.dao;

import com.cqupt.master_helper.entity.Video;
import com.cqupt.master_helper.entity.VideoRelease;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VideoReleaseDaoTest {

    @ParameterizedTest
    @CsvSource("00000000000007")
    void foundUserVideoRelease(String uid) {
        List<VideoRelease> videoReleaseList = new VideoReleaseDao().foundUserVideoRelease(uid);
        System.out.print("[");
        for (int i = 0 ; i<videoReleaseList.size();i++){
            VideoRelease videoRelease = videoReleaseList.get(i);
            Video video = new VideoDao().videoInfo(videoRelease.getRelease_vid());
            System.out.print(video.getVname()+" ");
        }
        System.out.print("]");
        assertEquals(0,0);
    }
}