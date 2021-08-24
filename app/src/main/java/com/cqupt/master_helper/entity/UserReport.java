package com.cqupt.master_helper.entity;

import java.util.List;

/**
 * 定义UserReport类
 */
public class UserReport {

    /**
     * 举报编号
     */
    private Integer report_id;
    /**
     * ID类型
     */
    private Integer id_type;
    /**
     * 举报号
     */
    private String something_id;
    /**
     * 举报次数
     */
    private Integer report_value;
    /**
     * 定位用vid
     */
    private String vid;

    public static final Integer ID_TYPE_VIDEO = 1;
    public static final Integer ID_TYPE_COMMENT = 2;
    public static final Integer ID_TYPE_REPLY = 3;

    public Integer getReport_id() {
        return report_id;
    }

    public Integer getId_type() {
        return id_type;
    }

    public String getSomething_id() {
        return something_id;
    }

    public Integer getReport_value() {
        return report_value;
    }

    public String getVid() {
        return vid;
    }

    public void setReport_id(Integer report_id) {
        this.report_id = report_id;
    }

    public void setId_type(Integer id_type) {
        this.id_type = id_type;
    }

    public void setSomething_id(String something_id) {
        this.something_id = something_id;
    }

    public void setReport_value(Integer report_value) {
        this.report_value = report_value;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }




    //    /**
//     * 获取所有举报
//     * @return 举报集
//     */
//    public List<UserReport> getAllReport(){return null;}
//
//    /**
//     * 添加举报编号
//     * @param report_id 举报编号
//     * @return 是否成功
//     */
//    public int addReportId(Integer report_id){return 0;}
//
//    /**
//     * 添加ID类型
//     * @param id_type ID类型
//     * @return 是否成功
//     */
//    public int addIdType(Integer id_type){return 0;}
//
//    /**
//     * 添加举报号
//     * @param something_id 举报号
//     * @return 是否成功
//     */
//    public int addSomethingId(String something_id){return 0;}
//
//    /**
//     * 添加举报次数
//     * @param report_id 举报编号
//     * @return 是否成功
//     */
//    public int addReportValue(Integer report_id){return 0;}
//
//    /**
//     * 添加定位用vid
//     * @param vid 定位用vid
//     * @return 是否成功
//     */
//    public int addVid(String vid){return 0;}
//
//    /**
//     * 增加举报次数
//     * @return 是否成功
//     */
//    public int setReportValue(){return 0;}
//
//    /**
//     * 删除举报
//     * @return 是否成功
//     */
//    public int deleteReport(Integer report_id){return 0;}
//
//    /**
//     * 查看是否有举报记录
//     * @param id_type ID类型
//     * @param something_id 举报号
//     * @return 1 无举报<br>2 有举报
//     */
//    public int foundVideoReport(Integer id_type, String something_id){return 1;}
//
//    /**
//     * 查看是否有举报记录
//     * @param id_type ID类型
//     * @param something_id 举报号
//     * @param vid 定位用vid
//     * @return 1 无举报<br>2 有举报
//     */
//    public int foundCommentReport(Integer id_type,String something_id,String vid){return 1;}

}
