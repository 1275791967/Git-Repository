package com.irongteng.persistence;

import java.util.Date;

public class PartitionVO {
    
    private String tableName;    //表名
    private String partName;     //指定分区
    private String endPartName;  //最后一个分区，之前的分区要删除
    private String dividPartName;//被分割的分区
    private Integer duration;    //数据保存天数
    private Date recordTime;     //分区时间
    
    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public String getPartName() {
        return partName;
    }
    public void setPartName(String partName) {
        this.partName = partName;
    }
    public String getEndPartName() {
        return endPartName;
    }
    public void setEndPartName(String endPartName) {
        this.endPartName = endPartName;
    }
    public String getDividPartName() {
        return dividPartName;
    }
    public void setDividPartName(String dividPartName) {
        this.dividPartName = dividPartName;
    }
    public Integer getDuration() {
        return duration;
    }
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    public Date getRecordTime() {
        return recordTime;
    }
    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }
}
