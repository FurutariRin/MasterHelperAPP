package com.cqupt.master_helper.entity;


/**
 * 定义UserFocus类
 */
public class UserFocus {

    /**
     * ID
     */
    private Integer id;
    /**
     * 账号
     */
    private String uid;
    /**
     * 关注用户
     */
    private String focus_uid;

    public Integer getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public String getFocus_uid() {
        return focus_uid;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setFocus_uid(String focus_uid) {
        this.focus_uid = focus_uid;
    }


    //    /**
//     * 获取关注用户
//     * @return 关注用户
//     */
//    public List<UserFocus> getFocusUid(String uid){
//        List<UserFocus> focus_uid_list = new ArrayList<>();
//        String sql = null;//sql语句
//        DruidConnection db = new DruidConnection(sql);
//        ResultSet resultSet = null;
//        try {
//            resultSet = db.preparedStatement.executeQuery();
//            while(resultSet.next()){
//                UserFocus userFocus = new UserFocus();
//                userFocus.focus_uid = resultSet.getString(1);
//                focus_uid_list.add(userFocus);
//            }
//            resultSet.close();
//            db.close();
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//        return focus_uid_list;
//    }
//
//    /**
//     * 添加关注用户
//     * @param uid 账号
//     * @param focus_uid 关注用户
//     * @return 是否成功
//     */
//    public int addFocusUid(String uid,String focus_uid){return 0;}
//
//    /**
//     * 删除关注用户
//     * @param uid 账号
//     * @param focus_uid 关注账号
//     * @return 是否成功
//     */
//    public int deleteFocusUid(String uid,String focus_uid){return 0;}
//
//    /**
//     * 查看是否关注
//     * @param uid 账号
//     * @param focus_uid 关注账号
//     * @return 1 未关注<br>2 已关注
//     */
//    public int findFocusUid(String uid,String focus_uid){return 1;}

}
