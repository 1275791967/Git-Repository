package com.irongteng.persistence;

import java.util.Date;

public class DeviceVO  extends BaseConditionVO{
    
    private Integer Id;
    private Integer customerID; //设备id
    private String deviceName; //设备名称
    private String  customerName; //客户名
    private String deviceIP; //设备IP地址
    private String areaLocation;//区域位置
    private Integer deviceType; //设备类型
    private String featureCode;//特征码
    private String longitude;//经度
    private String latitude; //纬度
    private String address; //地址
    private Date  createTime;//创建时间 
    private String remark;//说明
    private String contact; //联系方式
    private Date updateTime;//修改时间
    
    public Integer getId() {
        return Id;
    }
    public void setId(Integer id) {
        Id = id;
    }
    public Integer getCustomerID() {
        return customerID;
    }
    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }
    
    public String getDeviceName() {
        return deviceName;
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
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
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}