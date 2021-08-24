package com.cqupt.master_helper.biz.video;

import com.cqupt.master_helper.dao.VideoDao;
import com.cqupt.master_helper.entity.Video;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

public class VideoRankBiz {

    private static final int LINE = 10;

    /**
     * 获取视频排名
     *
     * @return 视频列表
     */
    public List<Video> VideoRank() {
        List<Video> videoListPage = new ArrayList<>();
        VideoDao videoDao = new VideoDao();

        List<Video> videoList = videoDao.videoRankServer();

//        int n = 0;
//        for (int i = (page - 1) * LINE; i < videoList.size(); i++) {
//            videoListPage.add(videoList.get(i));
//            n++;
//            if (n >= 10) {
//                break;
//            }
//        }


        return videoList;
    }
}
