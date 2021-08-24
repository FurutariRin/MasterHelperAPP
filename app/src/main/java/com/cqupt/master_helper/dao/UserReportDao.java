package com.cqupt.master_helper.dao;

import com.cqupt.master_helper.entity.UserReport;
import com.cqupt.master_helper.utils.DruidUtils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;

public class UserReportDao {

    private Integer report_id;

    public Integer getReport_id() {
        return report_id;
    }

    /**
     * 视频是否有举报记录
     *
     * @param vid 视频号
     * @return Code 1 无举报<br>Code 2 有举报<br>Code 3 连接失败
     */
    public int foundReportVideoRecord(String vid) {
        QueryRunner queryRunner = new QueryRunner();

        int retCode = 1;
        try {
            UserReport userReport = queryRunner.query(DruidUtils.getConnection(),
                    "SELECT report_id FROM project1.user_report WHERE id_type = ? AND something_id = ?",
                    new BeanHandler<>(UserReport.class),
                    UserReport.ID_TYPE_VIDEO, vid);
            if (userReport != null) {
                report_id = userReport.getReport_id();
                retCode = 2;
            } else {
                retCode = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 3;
        } finally {
            DruidUtils.closeSource();
        }
        return retCode;


    }

    /**
     * 新建视频举报
     *
     * @param vid 视频号
     * @return Code 0 举报成功<br>Code 1 举报失败<br>Code 2 连接失败
     */
    public int newReportVideoServer(String vid) {
        QueryRunner queryRunner = new QueryRunner();

        Integer requestRid = null;
        int retCode = 1;
        try {
            UserReport userReport = queryRunner.query(DruidUtils.getConnection(),
                    "SELECT * FROM project1.user_report ORDER BY report_id DESC LIMIT 1",
                    new BeanHandler<>(UserReport.class));
            if (userReport != null) {
                requestRid = userReport.getReport_id() + 1;
            } else {
                requestRid = 1;
            }
            System.out.println("举报号：" + requestRid);
        } catch (SQLException e) {
            e.printStackTrace();
            return 2;
        } finally {
            DruidUtils.closeSource();
        }

        try {
            int i = queryRunner.update(DruidUtils.getConnection(),
                    "INSERT INTO project1.user_report(report_id, id_type, something_id, report_value, vid) VALUES (?, ?, ?, ?, ?)",
                    requestRid, UserReport.ID_TYPE_VIDEO, vid, 1, "");
            if (i > 0) {
                System.out.println("插入成功");
                retCode = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 2;
        } finally {
            DruidUtils.closeSource();
        }
        return retCode;

    }

    /**
     * 添加举报次数
     *
     * @param vid 视频号
     * @return Code 0 举报成功<br>Code 1 举报失败<br>Code 2 连接失败
     */
    public int addReportVideoServer(String vid) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        QueryRunner queryRunner = new QueryRunner();

        int retCode = 1;
        try {
            int i = queryRunner.update(DruidUtils.getConnection(),
                    "UPDATE project1.user_report SET report_value = report_value + 1 WHERE report_id = ?",
                    report_id);
            if (i > 0) {
                System.out.println("更新成功");
                retCode = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 2;
        } finally {
            DruidUtils.closeSource();
        }
        return retCode;
    }

    /**
     * 获取举报
     *
     * @return 举报列表
     */
    public List<UserReport> adminFoundReport() {
        List<UserReport> userReportList = new ArrayList<>();
        QueryRunner queryRunner = new QueryRunner();

        try {
            userReportList = queryRunner.query(DruidUtils.getConnection(),
                    "SELECT * FROM project1.user_report",
                    new BeanListHandler<>(UserReport.class));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtils.closeSource();
        }

        return userReportList;
    }

    /**
     * 删除举报
     *
     * @param report_id 举报号
     * @return Code 1 删除成功<br>Code 2 删除失败<br>Code 3 连接失败
     */
    public int deleteReport(Integer report_id) {
        int retCode = 2;
        QueryRunner queryRunner = new QueryRunner();

        try {
            int i = queryRunner.update(DruidUtils.getConnection(),
                    "DELETE FROM project1.user_report WHERE report_id = ?",
                    report_id);
            if (i > 0) {
                retCode = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtils.closeSource();
        }
        return retCode;
    }


//    /**
//     * 评论是否有举报记录
//     *
//     * @param vid        视频号
//     * @param comment_id 评论号
//     * @return 1 无举报<br>2 有举报
//     */
//    public int foundReportCommentRecord(String vid, String comment_id) {
//        UserReport userReport = new UserReport();
//
//        int retCode = 1;
////        retCode = userReport.foundCommentReport(UserReport.ID_TYPE_COMMENT,comment_id,vid);
//
//        return retCode;
//    }
//
//    /**
//     * 新建评论举报
//     *
//     * @param vid        视频号
//     * @param comment_id 评论号
//     * @return Code 0 举报成功<br>Code 1 举报失败
//     */
//    public int newReportCommentServer(String vid, String comment_id) {
//        UserReport userReport = new UserReport();
//
//        int retCode = 1;
//        Integer report_id = null;
//        //寻找最大举报编号
////        int successAddReportId = userReport.addReportId(report_id);
////        int successAddIdType = userReport.addIdType(UserReport.ID_TYPE_COMMENT);
////        int successAddSomethingId = userReport.addSomethingId(comment_id);
////        int successAddVid = userReport.addVid(vid);
//
////        if (successAddReportId != 0
////                && successAddIdType != 0
////                && successAddSomethingId != 0
////                && successAddVid != 0){
////            retCode = 1;
////        }else {
////            retCode = 0;
////        }
//        return retCode;
//    }
//
//    /**
//     * 添加评论举报次数
//     *
//     * @param vid 视频号
//     * @return Code 0 举报成功<br>Code 1 举报失败
//     */
//    public int addReportCommentServer(String vid, String comment_id) {
//        UserReport userReport = new UserReport();
//
//        int retCode = 1;
//
////        int successAddReportValue = userReport.addReportValue(userReport.foundCommentReport(UserReport.ID_TYPE_COMMENT,comment_id,vid));
//
////        if(successAddReportValue != 0){
////            retCode = 1;
////        }else {
////            retCode = 0;
////        }
//        return retCode;
//    }


}
