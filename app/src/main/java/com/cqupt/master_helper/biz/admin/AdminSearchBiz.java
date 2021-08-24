package com.cqupt.master_helper.biz.admin;

import com.cqupt.master_helper.dao.UserDao;
import com.cqupt.master_helper.entity.User;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminSearchBiz {

    private static final int LINE = 10;

    /**
     * 搜索用户
     *
     * @param keyword 关键词
     * @param page 页码
     * @return 用户列表
     */
    public List<User> foundUser(String keyword, int page) {
        List<User> userListPage = new ArrayList<>();
        UserDao userDao = new UserDao();

        if(keyword==null){
            System.out.println("请输入关键词");
            return null;
        }

        String[] ss = keyword.split(" ");
        List<String> keywordList = new ArrayList<>(Arrays.asList(ss));
        List<User> userList = userDao.foundUser(keywordList);

        int n = 0;
        for (int i = (page - 1) * LINE; i < userList.size(); i++) {
            userListPage.add(userList.get(i));
            n++;
            if (n >= 10) {
                break;
            }
        }


//        System.out.println("============================");
//        for (int i = 0; i < userListPage.size(); i++) {
//            User user = userList.get(i);
//            System.out.println("UID：" + user.getUid());
//            System.out.println("昵称：" + user.getUname());
//            System.out.println("Email：" + user.getEmail());
//            System.out.println("权限等级：" + user.getLevel());
//            System.out.println("============================");
//        }
        return userListPage;

    }

}
