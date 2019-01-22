package com.irongteng.service;

import java.util.Date;

import com.irongteng.persistence.beans.Device;

import dwz.framework.sys.business.AbstractBusinessObject;

public class DeviceBean extends AbstractBusinessObject{
    
    private static final long serialVersionUID = 1L;
    private  Device device = null;
    

    @Override
    public Integer getId() {
        return this.device.getId();
    }

    public void setId(Integer id) {
        this.getDevice().setId(id);
    }
    
    public DeviceBean() {
        this.device = new Device();
    }
    
    public DeviceBean(Device device){
         this.device=device;
    }
    public Device getDevice(){
        return  this.device;
    }
    
    public String getDeviceName() {
        return this.device.getDeviceName();
    }

    public void setDeviceName(String deviceName) {
        this.getDevice().setDeviceName(deviceName);
    }
    
    public Integer getDeviceType() {
        return this.device.getDeviceType();
    }

    public void setDeviceType(Integer deviceType) {
        this.getDevice().setDeviceType(deviceType);
    }
    
    public String getFeatureCode() {
        return this.device.getFeatureCode();
    }

    public void setFeatureCode(String featureCode) {
        this.getDevice().setFeatureCode(featureCode);
    }
    
    public String getLongitude() {
        return this.device.getLongitude();
    }

    public void setLongitude(String longitude) {
        this.getDevice().setLongitude(longitude);
    }
    
    public String getLatitude() {
        return this.device.getLatitude();
    }

    public void setLatitude(String latitude) {
        this.getDevice().setLatitude(latitude);
    }
    
    public String getAddress() {
        return this.device.getAddress();
    }

    public void setAddress(String address) {
        this.getDevice().setAddress(address);
    }
    
    public Date getCreateTime() {
        return this.device.getCreateTime();
    }

    public void setCreateTime(Date createTime) {
        this.getDevice().setCreateTime(createTime);
    }
    
    public String getRemark() {
        return this.device.getRemark();
    }

    public void setRemark(String remark) {
        this.getDevice().setRemark(remark);
    }
    
    public String getDeviceIP() {
        return this.device.getDeviceIP();
    }

    public void setDeviceIP(String deviceIP) {
        this.getDevice().setDeviceIP(deviceIP);
    }
    

    public String getAreaLocation() {
        return this.device.getAreaLocation();
    }

    public void setAreaLocation(String areaLocation) {
        this.device.setAreaLocation(areaLocation);
    }
    
    //客户表信息
    public Integer getCustomerID() {
        return this.device.getCustomerID();
    }

    public void setCustomerID(Integer customerID) {
        this.getDevice().setCustomerID(customerID);
    }
    
    public String getCustomerName() {
        return this.device.getCustomerName();
    }

    public void setCustomerName(String customerName) {
        this.getDevice().setCustomerName(customerName);
    }
    
    public String getContact() {
        return this.getDevice().getContact();
    }
    
    public void setContact(String contact) {
        this.getDevice().setContact(contact);
    }
    
    public Date getUpdateTime() {
        return this.getDevice().getUpdateTime();
    }
    
    public void setUpdateTime(Date updateTime) {
        this.getDevice().setUpdateTime(updateTime);
    }
    
}
