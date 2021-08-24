package com.cqupt.master_helper.biz.video;

import com.cqupt.master_helper.dao.VideoDao;
import com.cqupt.master_helper.entity.User;
import com.cqupt.master_helper.entity.Video;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VideoRecommendBiz {

    private static final int LINE = 10;

    /**
     * 推荐视频（单元测试通过）
     *
     * @param uid uid
     * @return 推荐视频列表
     */
    public ArrayList<Video> VideoRecommend(String uid) {
        ArrayList<Video> videoListPage = new ArrayList<>();
        VideoDao videoDao = new VideoDao();

        List<Video> videoList = videoDao.videoRecommendServer(uid);

        ArrayList<Integer> integerArrayList = new ArrayList<>();
        Random random = new Random();
        int i = 0;
        while (i < videoList.size()) {
            int r = random.nextInt(videoList.size());
            if (!integerArrayList.contains(r)) {
                integerArrayList.add(r);
                i++;
            }
        }

//        int n = 0;
//        for (int m = (page - 1) * LINE; m < videoList.size(); m++) {
//            videoListPage.add(videoList.get(integerArrayList.get(n)));
//            n++;
//            if (n >= 10) {
//                break;
//            }
//        }

        for(int m = 0;m<videoList.size();m++){
            videoListPage.add(videoList.get(integerArrayList.get(m)));
        }



        return videoListPage;
    }
}
