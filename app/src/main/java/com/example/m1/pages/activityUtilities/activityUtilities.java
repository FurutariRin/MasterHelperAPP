package com.example.m1.pages.activityUtilities;

import android.os.Bundle;

import com.cqupt.master_helper.biz.video.VideoRecommendBiz;
import com.cqupt.master_helper.biz.video.VideoSearchBiz;
import com.cqupt.master_helper.dao.VideoDao;
import com.cqupt.master_helper.entity.User;
import com.cqupt.master_helper.entity.Video;
import com.example.m1.bean.recycleViewData;

import java.util.ArrayList;
import java.util.List;

public class activityUtilities {
    //视频数据
    public static ArrayList<recycleViewData> getDatas(int fromIndex, int toIndex, int Type, String key) {
        ArrayList<recycleViewData> newDataTestSet = new ArrayList<>();

        if (Type == 4) {
            List<Video> videoList = new VideoSearchBiz().VideoSearch(key);
            if (videoList.size() > toIndex) {
                for (int i = fromIndex; i < toIndex; i++) {
                    recycleViewData recycleViewData = new recycleViewData(videoList.get(i));
                    newDataTestSet.add(recycleViewData);
                }
            } else {
                for (int i = 0; i < videoList.size(); i++) {
                    recycleViewData recycleViewData = new recycleViewData(videoList.get(i));
                    newDataTestSet.add(recycleViewData);
                }
            }
            //获取搜索课程数据
        } else if (Type == 5) {
            List<Video> videoList = new VideoDao().useCourseTypeFoundVideo(key);
            if (videoList.size() > toIndex) {
                for (int i = fromIndex; i < toIndex; i++) {
                    recycleViewData recycleViewData = new recycleViewData(videoList.get(i));
                    newDataTestSet.add(recycleViewData);
                }
            } else {
                for (int i = 0; i < videoList.size(); i++) {
                    recycleViewData recycleViewData = new recycleViewData(videoList.get(i));
                    newDataTestSet.add(recycleViewData);
                }
            }
            //获取分类课程数据
        }
        return newDataTestSet;
    }

    public static Bundle bundlePack(ArrayList<recycleViewData> LocalData, int position, String uid) {
        Bundle tempBundle = new Bundle();
        tempBundle.putString("videoName", LocalData.get(position).videoName);
        tempBundle.putString("uploaderName", LocalData.get(position).uploaderName);
        tempBundle.putString("videoUri", LocalData.get(position).videoUri);
        tempBundle.putString("introduction", LocalData.get(position).videoDesc);
        tempBundle.putString("authorUid", LocalData.get(position).authorUid);
        tempBundle.putString("uid", uid);
        tempBundle.putString("vid", LocalData.get(position).vid);
        return tempBundle;
    }
}
