package com.irongteng.service;

import java.util.Date;

import com.irongteng.persistence.beans.CommLog;

import dwz.framework.sys.business.AbstractBusinessObject;

public class CommLogBean extends AbstractBusinessObject{
    private static final long serialVersionUID = 1L;
    
    private  CommLog commLog;
    
    @Override
    public Integer getId() {
        return this.commLog.getId();
    }

    public void setId(Integer id) {
        this.getCommLog().setId(id);
    }
    
    public CommLogBean() {
        this.commLog = new CommLog();
    }
    
    public CommLogBean(CommLog commLog){
         this.commLog=commLog;
    }
    public CommLog getCommLog(){
        return  this.commLog;
    }
    
    public Integer getLogTypeID() {
        return this.commLog.getLogTypeID();
    }

    public void setLogTypeID(Integer logTypeID) {
        this.getCommLog().setLogTypeID(logTypeID);
    
   }
    
    public Integer getDeviceType() {
        return this.commLog.getDeviceType();
    }

    public void setDeviceType(Integer deviceType) {
        this.getCommLog().setDeviceType(deviceType);
    
   }
    
    public String getContent() {
        return this.commLog.getContent();
    }

    public void setContent(String content) {
        this.getCommLog().setContent(content);
    }
    
    public Date getRecordTime() {
        return this.commLog.getRecordTime();
    }

    public void setRecordTime(Date recordTime) {
        this.getCommLog().setRecordTime(recordTime);
    }
    
    public String getRemark() {
        return this.commLog.getRemark();
    }

    public void setRemark(String remark) {
        this.getCommLog().setRemark(remark);
    }
    
    //设备信息
    public Integer getDeviceID() {
        return this.commLog.getDeviceID();
    }

    public void setDeviceID(Integer deviceID) {
        this.getCommLog().setDeviceID(deviceID);
    }
    
    public Integer getCustomerID() {
        return this.commLog.getCustomerID();
    }

    public void setCustomerID(Integer customerID) {
        this.getCommLog().setCustomerID(customerID);
    }
    
    public String getDeviceName() {
        return this.commLog.getDeviceName();
    }
    
    public void setDeviceName(String deviceName) {
        this.getCommLog().setDeviceName(deviceName);
    }
    
    
    public String getFeatureCode() {
        return this.commLog.getFeatureCode();
    }
    
    public void setFeatureCode(String featureCode) {
        this.getCommLog().setFeatureCode(featureCode);
    }
    
    public String getLongitude() {
        return this.commLog.getLongitude();
    }
    
    public void setLongitude(String longitude) {
        this.getCommLog().setLongitude(longitude);
    }
    
    public String getLatitude() {
        return this.commLog.getLatitude();
    }
    
    public void setLatitude(String latiude) {
        this.getCommLog().setLatitude(latiude);
    }
    
    public String getAddress() {
        return this.commLog.getAddress();
    }
    
    public void setAddress(String address) {
        this.getCommLog().setAddress(address);
    }
 
    public Date getCreateTime() {
        return this.commLog.getCreateTime();
    }
    
    public void setCreateTime(Date createTime) {
        this.getCommLog().setCreateTime(createTime);
    }
    
    public String getDeviceIP() {
        return this.commLog.getDeviceIP();
    }
    
    public void setDeviceIP(String deviceIP) {
        this.getCommLog().setDeviceIP(deviceIP);
    }
    
    public String getAreaLocation() {
        return this.commLog.getAreaLocation();
    }
    
    public void setAreaLocation(String areaLocation) {
        this.getCommLog().setAreaLocation(areaLocation);
    }
  
}
