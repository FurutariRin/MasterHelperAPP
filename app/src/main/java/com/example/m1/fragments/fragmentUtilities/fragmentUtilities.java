package com.example.m1.fragments.fragmentUtilities;

import android.os.Bundle;

import com.cqupt.master_helper.biz.video.VideoCollectBiz;
import com.cqupt.master_helper.biz.video.VideoRankBiz;
import com.cqupt.master_helper.biz.video.VideoRecommendBiz;
import com.cqupt.master_helper.dao.UserFavoriteDao;
import com.cqupt.master_helper.dao.VideoDao;
import com.cqupt.master_helper.entity.UserFavorite;
import com.cqupt.master_helper.entity.Video;
import com.example.m1.bean.recycleViewData;

import java.util.ArrayList;
import java.util.List;

public class fragmentUtilities {
    //获取数据
    public static ArrayList<recycleViewData> getDatas(int fromIndex, int toIndex, int Type, String uid) {
        ArrayList<recycleViewData> newDataTestSet = new ArrayList<>();
        if (Type == 1) {
            List<Video> videoList = new VideoRecommendBiz().VideoRecommend(uid);
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
        } else if (Type == 2) {
            List<Video> videoList = new VideoRankBiz().VideoRank();
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
            //获取排名数据
        } else if (Type == 3) {
            List<UserFavorite> userFavoriteList = new UserFavoriteDao().foundUserCollection(uid);
            List<Video> videoList = new ArrayList<>();
            for (int i = 0; i < userFavoriteList.size(); i++) {
                videoList.add(new VideoDao().videoInfo(userFavoriteList.get(i).getFavorite_vid()));
            }
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
            //获取收藏数据
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
