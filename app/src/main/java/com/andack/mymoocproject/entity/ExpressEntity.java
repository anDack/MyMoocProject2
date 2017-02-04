package com.andack.mymoocproject.entity;

/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/2/4
 * 邮箱：    1160083806@qq.com
 * 描述：    快递显示实体类
 */

public class ExpressEntity {
    private String Zone;
    private String Remark;
    private String Datetime;

    public String getZone() {
        return Zone;
    }

    public void setZone(String zone) {
        Zone = zone;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getDatetime() {
        return Datetime;
    }

    public void setDatetime(String datetime) {
        Datetime = datetime;
    }
}
