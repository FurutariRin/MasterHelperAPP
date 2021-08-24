package com.example.m1.bean;

import com.cqupt.master_helper.entity.UserReport;

public class reportData {
    public String videoName;
    public Integer Rid;
    public String Vid;
    public int reportNum;

    public reportData(UserReport userReport) {
        this.Rid = userReport.getReport_id();
        this.Vid = userReport.getSomething_id();
        this.reportNum = userReport.getReport_value();
    }

    public String setVideoName(String vname) {
        return this.videoName = vname;
    }
}
