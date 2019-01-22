package com.irongteng.persistence.beans;

import java.util.Date;

import dwz.dal.object.AbstractDO;

public class LoadInfo extends AbstractDO {
    
    private static final long serialVersionUID = -2549770144649770857L;
    
    private Integer ID; 
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
    private java.util.Date recordTime; //记录时间
    private String remark; //备注
    private Device device;//设备信息表
   
    public LoadInfo(){
        device=new Device();
    }  
    
    public LoadInfo(Integer id){
        this.ID = id;
    }
    
    @Override
    public Integer getId() {
        return ID;
    }

    public void setId(Integer id) {
        ID = id;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer iD) {
        ID = iD;
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

    public java.util.Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(java.util.Date recordTime) {
        this.recordTime = recordTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }    
    
    //设备信息表
    public Device getDevice() {
        return device;
    }

    public void setDevice(Device Device) {
        this.device = Device;
    }
    
    public Integer getDeviceID() {
        return this.device.getId();
    }

    public void setDeviceID(Integer deviceID) {
        this.device.setId(deviceID);
    }
    
    public Integer getCustomerID() {
        return this.device.getCustomerID();
    }

    public void setCustomerID(Integer customerID) {
        this.device.setCustomerID(customerID);
    }

    public String getDeviceName() {
        return this.device.getDeviceName();
    }

    public void setDeviceName(String deviceName) {
        this.device.setDeviceName(deviceName);
    }

    public Integer getDeviceType() {
        return this.device.getDeviceType();
    }

    public void setDeviceType(Integer deviceType) {
        this.device.setDeviceType(deviceType);
    }

    public String getFeatureCode() {
        return this.device.getFeatureCode();
    }

    public void setFeatureCode(String featureCode) {
        this.device.setFeatureCode(featureCode);
    }

    public String getLongitude() {
        return this.device.getLongitude();
    }

    public void setLongitude(String longitude) {
        this.device.setLongitude(longitude);
    }

    public String getLatitude() {
        return this.device.getLatitude();
    }

    public void setLatitude(String latitude) {
        this.device.setLatitude(latitude);
    }

    public String getAddress() {
        return this.device.getAddress();
    }

    public void setAddress(String address) {
        this.device.setAddress(address);
    }

    public Date getCreateTime() {
        return this.device.getCreateTime();
    }

    public void setCreateTime(Date reateTime) {
        this.device.setCreateTime(reateTime);
    }

    public String getDeviceIP() {
        return this.device.getDeviceIP();
    }

    public void setDeviceIP(String deviceIP) {
        this.device.setDeviceIP(deviceIP);
    }

    public String getAreaLocation() {
        return this.device.getAreaLocation();
    }

    public void setAreaLocation(String areaLocation) {
        this.device.setAreaLocation(areaLocation);
    }
}