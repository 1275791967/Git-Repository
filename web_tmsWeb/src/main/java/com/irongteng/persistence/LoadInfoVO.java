package com.irongteng.persistence;

import java.util.Date;

public class LoadInfoVO extends BaseConditionVO{
    
    private Integer Id; 
    private Integer deviceID; // 设备id
    private String deviceName; //设备名
    private Integer ch1Counts;//通道1总通道数
    private Integer ch1NormalCounts;//通道1正常通道数
    private Integer ch1WaitTranslates;//通道1待翻译数
    private Integer ch2Counts;//通道2总通道数
    private Integer ch2NormalCounts;//通道2正常通道数
    private Integer ch2WaitTranslates;//通道2待翻译数
    private Integer ch3Counts;//通道3总通道数
    private Integer ch3NormalCounts;//通道3正常通道数
    private Integer ch3WaitTranslates;//通道3待翻译数
    private Integer ch4Counts;//通道4总通道数
    private Integer ch4NormalCounts;//通道4正常通道数
    private Integer ch4WaitTranslates;//通道4待翻译数
    private Integer ch5Counts;//通道5总通道数
    private Integer ch5NormalCounts;//通道5正常通道数
    private Integer ch5WaitTranslates;//通道5待翻译数
    private Integer alarmStatus;//告警状态
    private Date recordTime; //记录时间
    private String remark; //备注
    private Integer customerID; //设备id
    private String deviceIP; //设备IP地址
    private String areaLocation;//区域位置
    private Integer deviceType; //设备类型
    private String featureCode;//特征码
    private String longitude;//经度
    private String latitude; //纬度
    private String address; //地址
    private Date  createTime;//创建时间 
    
    public Integer getId() {
        return Id;
    }
    public void setId(Integer id) {
        Id = id;
    }
    public Integer getDeviceID() {
        return deviceID;
    }
    public void setDeviceID(Integer deviceID) {
        this.deviceID = deviceID;
    }
    public String getDeviceName() {
        return deviceName;
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
    public Integer getCh1Counts() {
        return ch1Counts;
    }
    public void setCh1Counts(Integer ch1Counts) {
        this.ch1Counts = ch1Counts;
    }
    public Integer getCh1NormalCounts() {
        return ch1NormalCounts;
    }
    public void setCh1NormalCounts(Integer ch1NormalCounts) {
        this.ch1NormalCounts = ch1NormalCounts;
    }
    public Integer getCh1WaitTranslates() {
        return ch1WaitTranslates;
    }
    public void setCh1WaitTranslates(Integer ch1WaitTranslates) {
        this.ch1WaitTranslates = ch1WaitTranslates;
    }
    public Integer getCh2Counts() {
        return ch2Counts;
    }
    public void setCh2Counts(Integer ch2Counts) {
        this.ch2Counts = ch2Counts;
    }
    public Integer getCh2NormalCounts() {
        return ch2NormalCounts;
    }
    public void setCh2NormalCounts(Integer ch2NormalCounts) {
        this.ch2NormalCounts = ch2NormalCounts;
    }
    public Integer getCh2WaitTranslates() {
        return ch2WaitTranslates;
    }
    public void setCh2WaitTranslates(Integer ch2WaitTranslates) {
        this.ch2WaitTranslates = ch2WaitTranslates;
    }
    public Integer getCh3Counts() {
        return ch3Counts;
    }
    public void setCh3Counts(Integer ch3Counts) {
        this.ch3Counts = ch3Counts;
    }
    public Integer getCh3NormalCounts() {
        return ch3NormalCounts;
    }
    public void setCh3NormalCounts(Integer ch3NormalCounts) {
        this.ch3NormalCounts = ch3NormalCounts;
    }
    public Integer getCh3WaitTranslates() {
        return ch3WaitTranslates;
    }
    public void setCh3WaitTranslates(Integer ch3WaitTranslates) {
        this.ch3WaitTranslates = ch3WaitTranslates;
    }
    public Integer getCh4Counts() {
        return ch4Counts;
    }
    public void setCh4Counts(Integer ch4Counts) {
        this.ch4Counts = ch4Counts;
    }
    public Integer getCh4NormalCounts() {
        return ch4NormalCounts;
    }
    public void setCh4NormalCounts(Integer ch4NormalCounts) {
        this.ch4NormalCounts = ch4NormalCounts;
    }
    public Integer getCh4WaitTranslates() {
        return ch4WaitTranslates;
    }
    public void setCh4WaitTranslates(Integer ch4WaitTranslates) {
        this.ch4WaitTranslates = ch4WaitTranslates;
    }
    public Integer getCh5Counts() {
        return ch5Counts;
    }
    public void setCh5Counts(Integer ch5Counts) {
        this.ch5Counts = ch5Counts;
    }
    public Integer getCh5NormalCounts() {
        return ch5NormalCounts;
    }
    public void setCh5NormalCounts(Integer ch5NormalCounts) {
        this.ch5NormalCounts = ch5NormalCounts;
    }
    public Integer getCh5WaitTranslates() {
        return ch5WaitTranslates;
    }
    public void setCh5WaitTranslates(Integer ch5WaitTranslates) {
        this.ch5WaitTranslates = ch5WaitTranslates;
    }
    public Integer getAlarmStatus() {
        return alarmStatus;
    }
    public void setAlarmStatus(Integer alarmStatus) {
        this.alarmStatus = alarmStatus;
    }
    public Date getRecordTime() {
        return recordTime;
    }
    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Integer getCustomerID() {
        return customerID;
    }
    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }
    public String getDeviceIP() {
        return deviceIP;
    }
    public void setDeviceIP(String deviceIP) {
        this.deviceIP = deviceIP;
    }
    public String getAreaLocation() {
        return areaLocation;
    }
    public void setAreaLocation(String areaLocation) {
        this.areaLocation = areaLocation;
    }
    public Integer getDeviceType() {
        return deviceType;
    }
    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }
    public String getFeatureCode() {
        return featureCode;
    }
    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode;
    }
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}