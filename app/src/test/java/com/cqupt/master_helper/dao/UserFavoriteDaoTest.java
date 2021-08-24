package com.cqupt.master_helper.dao;

import com.cqupt.master_helper.entity.UserFavorite;
import com.cqupt.master_helper.entity.Video;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserFavoriteDaoTest {

    @ParameterizedTest
    @CsvSource("00000000000004")
    void foundUserCollection(String uid) {
        List<UserFavorite>userFavoriteList = new UserFavoriteDao().foundUserCollection(uid);
        System.out.print("[");
        for(int i = 0;i<userFavoriteList.size();i++){
            UserFavorite userFavorite = userFavoriteList.get(i);
            Video video = new VideoDao().videoInfo(userFavorite.getFavorite_vid());
            System.out.print(video.getVname());
        }
        System.out.print("]");
        assertEquals(0, 0);
    }
}