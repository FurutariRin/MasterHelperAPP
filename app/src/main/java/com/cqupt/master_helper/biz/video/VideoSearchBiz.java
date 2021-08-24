package com.cqupt.master_helper.biz.video;

import com.cqupt.master_helper.dao.VideoDao;
import com.cqupt.master_helper.entity.Video;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VideoSearchBiz {

    private static final int LINE = 10;

    /**
     * 搜索（单元测试通过）
     *
     * @param keyword 关键词
     * @return
     */
    public List<Video> VideoSearch(String keyword/*, int page*/) {
        List<Video> videoListPage = new ArrayList<>();
        VideoDao videoDao = new VideoDao();

        String[] ss = keyword.split(" ");
        List<String> keywordList = new ArrayList<>(Arrays.asList(ss));

        List<Video> videoList = videoDao.searchServer(keywordList);

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
