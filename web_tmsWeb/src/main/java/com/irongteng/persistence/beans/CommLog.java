package com.irongteng.persistence.beans;

import java.util.Date;

import dwz.dal.object.AbstractDO;

public class CommLog extends AbstractDO {
    private static final long serialVersionUID = -2549770144649770857L;
    
    private Integer ID;  
    private Integer deviceType;  //设备类型
    private Integer logTypeID;  //日志类型
    private String content;   //内容
    private java.util.Date recordTime; //记录时间
    private String remark; //说明
    private Device device;//设备信息表
    
    public CommLog(){
        device=new Device();
    }
    
    public CommLog(Integer id){
        this.ID = id;
    }
    
    @Override
    public Integer getId() {
        return ID;
    }
    public void setId(Integer id) {
        ID = id;
    }

    public Integer getLogTypeID() {
        return logTypeID;
    }

    public void setLogTypeID(Integer logTypeID) {
        this.logTypeID = logTypeID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
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
        this.device.setCustomerID(customerID);;
    }

    public String getDeviceName() {
        return this.device.getDeviceName();
    }

    public void setDeviceName(String deviceName) {
        this.device.setDeviceName(deviceName);
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

    public void setCreateTime(Date createTime) {
        this.device.setCreateTime(createTime);
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
