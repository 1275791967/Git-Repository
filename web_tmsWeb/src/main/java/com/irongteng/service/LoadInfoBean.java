package com.irongteng.service;

import java.util.Date;

import com.irongteng.Unit.TranslateUnit;
import com.irongteng.persistence.beans.LoadInfo;

import dwz.framework.sys.business.AbstractBusinessObject;

public class LoadInfoBean extends AbstractBusinessObject{
  private static final long serialVersionUID = 1L;
    
    private LoadInfo loadInfo;
    
    public void setLoadInfo(TranslateUnit unit){
        this.loadInfo.setCh1Counts(unit.ch1Counts.getValue());
        this.loadInfo.setCh1NormalCounts(unit.ch1NormalCounts.getValue());
        this.loadInfo.setCh1WaitTranslates(unit.ch1WaitTranslates.getValue());
        this.loadInfo.setCh2Counts(unit.ch2Counts.getValue());
        this.loadInfo.setCh2NormalCounts(unit.ch2NormalCounts.getValue());
        this.loadInfo.setCh2WaitTranslates(unit.ch2WaitTranslates.getValue());
        this.loadInfo.setCh3Counts(unit.ch3Counts.getValue());
        this.loadInfo.setCh3NormalCounts(unit.ch3NormalCounts.getValue());
        this.loadInfo.setCh3WaitTranslates(unit.ch3WaitTranslates.getValue());
        this.loadInfo.setCh4Counts(unit.ch4Counts.getValue());
        this.loadInfo.setCh4NormalCounts(unit.ch4NormalCounts.getValue());
        this.loadInfo.setCh4WaitTranslates(unit.ch4WaitTranslates.getValue());
        this.loadInfo.setCh5Counts(unit.ch5Counts.getValue());
        this.loadInfo.setCh5NormalCounts(unit.ch5NormalCounts.getValue());
        this.loadInfo.setCh5WaitTranslates(unit.ch5WaitTranslates.getValue());
        this.loadInfo.setAlarmStatus(unit.alarmState.getValue());
    }
    
    @Override
    public Integer getId() {
        return this.loadInfo.getId();
    }

    public void setId(Integer id) {
        this.getLoadInfo().setId(id);
    }
    
    public LoadInfoBean() {
        this.loadInfo = new LoadInfo();
    }
    
    public LoadInfoBean(LoadInfo loadInfo){
         this.loadInfo=loadInfo;
    }
    public LoadInfo getLoadInfo(){
        return  this.loadInfo;
    }

    //通道1

    public Integer getCh1Counts() {
        return this.loadInfo.getCh1Counts();
    }

    public void setCh1Counts(Integer ch1Counts) {

        this.getLoadInfo().setCh1Counts(ch1Counts);
    }
    
    public Integer getCh1NormalCounts() {
        return this.loadInfo.getCh1NormalCounts();

    }

    public void setCh1NormalCounts(Integer ch1NormalCounts) {
        this.getLoadInfo().setCh1NormalCounts(ch1NormalCounts);
    }
    
    public Integer getCh1WaitTranslates() {
        return this.loadInfo.getCh1WaitTranslates();

    }

    public void setCh1WaitTranslates(Integer ch1WaitTranslates) {
        this.getLoadInfo().setCh1WaitTranslates(ch1WaitTranslates);
    }
    
    //通道2
    public Integer getCh2Counts() {
        return this.loadInfo.getCh2Counts();
    }


    public void setCh2Counts(Integer ch2Counts) {
        this.loadInfo.setCh2Counts(ch2Counts);
    }


    public Integer getCh2WaitTranslates() {
        return this.loadInfo.getCh2WaitTranslates();
    }
    
    public void setCh2WaitTranslates(Integer ch2WaitTranslates) {
        this.getLoadInfo().setCh2WaitTranslates(ch2WaitTranslates);
    }

    public Integer getCh2NormalCounts() {
        return this.loadInfo.getCh2NormalCounts();
    }

    public void setCh2NormalCounts(Integer ch2NormalCounts) {
        this.loadInfo.setCh2NormalCounts(ch2NormalCounts);
    }

    
   

    //通道3
    public Integer getCh3Counts() {
        return this.loadInfo.getCh3Counts();
    }
    
    public void setCh3Counts(Integer ch3Counts) {
        this.getLoadInfo().setCh3Counts(ch3Counts);

    }

    public void setCh3NormalCounts(Integer ch3NormalCounts) {
        this.getLoadInfo().setCh3NormalCounts(ch3NormalCounts);
    }
    
    public Integer getCh3NormalCounts() {
        return this.loadInfo.getCh3NormalCounts();
    }
    
    public Integer getCh3WaitTranslates() {
        return this.loadInfo.getCh3WaitTranslates();
    }
    public void setCh3WaitTranslates(Integer ch3WaitTranslates) {
        this.loadInfo.setCh3WaitTranslates(ch3WaitTranslates);
    }
    
    //通道4
    public Integer getCh4Counts() {
        return this.loadInfo.getCh4Counts();
    }
    
    public void setCh4Counts(Integer ch4Counts) {
         this.loadInfo.setCh4Counts(ch4Counts);
    }
    
    public Integer getCh4NormalCounts() {
        return  this.getLoadInfo().getCh4NormalCounts();

    }
    public void setCh4NormalCounts(Integer ch4NormalCounts) {
         this.loadInfo.setCh4NormalCounts(ch4NormalCounts);
    }
    
    public Integer getCh4WaitTranslates() {
        return  this.getLoadInfo().getCh4WaitTranslates();

    }
    public void setCh4WaitTranslates(Integer ch4WaitTranslates) {
         this.loadInfo.setCh4WaitTranslates(ch4WaitTranslates);
    }

    
    //通道5
    public Integer getCh5Counts() {
        return this.loadInfo.getCh5Counts();
    }

    public void setCh5Counts(Integer ch5Counts) {
        this.loadInfo.setCh5Counts(ch5Counts);
    }

    public Integer getCh5NormalCounts() {
        return this.loadInfo.getCh5NormalCounts();
    }

    public void setCh5NormalCounts(Integer ch5NormalCounts) {
        this.loadInfo.setCh5NormalCounts(ch5NormalCounts);
    }

    public Integer getCh5WaitTranslates() {
        return this.loadInfo.getCh5WaitTranslates();
    }
    
    public void setCh5WaitTranslates(Integer ch5WaitTranslates) {
        this.loadInfo.setCh5WaitTranslates(ch5WaitTranslates);
    }
    
    public Integer getAlarmStatus() {
        return this.loadInfo.getAlarmStatus();
    }

    public void setAlarmStatus(Integer alarmStatus) {
        this.loadInfo.setAlarmStatus(alarmStatus);
    }
    
    public Date getRecordTime() {
        return this.loadInfo.getRecordTime();
    }

    public void setRecordTime(Date recordTime) {
        this.getLoadInfo().setRecordTime(recordTime);
    }
    
    public String getRemark() {
        return this.loadInfo.getRemark();
    }

    public void setRemark(String recordTime) {
        this.getLoadInfo().setRemark(recordTime);
    }
    
    //设备信息表
    public Integer getDeviceID() {
        return this.loadInfo.getDeviceID();
    }

    public void setDeviceID(Integer deviceID) {
        this.getLoadInfo().setDeviceID(deviceID);
    }
    
    
    public Integer getCustomerID() {
        return this.loadInfo.getCustomerID();
    }

    public void setCustomerID(Integer ustomerID) {
        this.getLoadInfo().setCustomerID(ustomerID);
    }
    
    public String getDeviceName() {
        return this.loadInfo.getDeviceName();
    }
    
    public void setDeviceName(String deviceName) {
        this.getLoadInfo().setDeviceName(deviceName);
    }
    
    public Integer getDeviceType() {
        return this.loadInfo.getDeviceType();
    }

    public void setDeviceType(Integer deviceType) {
        this.getLoadInfo().setDeviceType(deviceType);
    }
    
    public String getFeatureCode() {
        return this.loadInfo.getFeatureCode();
    }
    
    public void setFeatureCode(String featureCode) {
        this.getLoadInfo().setFeatureCode(featureCode);
    }
    
    public String getLongitude() {
        return this.loadInfo.getLongitude();
    }
    
    public void setLongitude(String longitude) {
        this.getLoadInfo().setLongitude(longitude);
    }
    
    public String getLatitude() {
        return this.loadInfo.getLatitude();
    }
    
    public void setLatitude(String latiude) {
        this.getLoadInfo().setLatitude(latiude);
    }
    
    public String getAddress() {
        return this.loadInfo.getAddress();
    }
    
    public void setAddress(String address) {
        this.getLoadInfo().setAddress(address);
    }
    
    public Date getCreateTime() {
        return this.loadInfo.getCreateTime();
    }
    
    public void setCreateTime(Date reateTime) {
        this.getLoadInfo().setCreateTime(reateTime);
    }

    public String getDeviceIP() {
        return this.loadInfo.getDeviceIP();
    }
    
    public void setDeviceIP(String deviceIP) {
        this.getLoadInfo().setDeviceIP(deviceIP);
    }
    
    public String getAreaLocation() {
        return this.loadInfo.getAreaLocation();
    }
    
    public void setAreaLocation(String areaLocation) {
        this.getLoadInfo().setAreaLocation(areaLocation);
    }
}
