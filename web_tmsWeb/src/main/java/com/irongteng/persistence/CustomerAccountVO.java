package com.irongteng.persistence;

import java.util.Date;

public class CustomerAccountVO  extends BaseConditionVO{
    
    private Integer Id;
    private Integer deviceID;//设备id
    private String name;// 账户名
    private String psw;// 密码（加密过后）
    private String phone;// 电话号码
    private Integer thirdBalance;// 第三方余额
    private Integer localBalance;// 本地余额
    private String status;// 客户状态(使用,停用)
    private java.util.Date createDate;// 创建时间
    private String remark;// 说明
    private Integer customerID; //设备id
    private String deviceName; //设备名称
    private String deviceIP; //设备IP地址
    private String areaLocation;//区域位置
    private Integer deviceType; //设备类型
    private String featureCode;//特征码
    private String longitude;//经度
    private String latitude; //纬度
    private String address; //地址
    private Date  createTime;//创建时间 
    private String CustomerAccountStatus;

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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPsw() {
        return psw;
    }
    public void setPsw(String psw) {
        this.psw = psw;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Integer getLocalBalance() {
        return localBalance;
    }
    public void setLocalBalance(Integer localBalance) {
        this.localBalance = localBalance;
    }
    public Integer getThirdBalance() {
        return thirdBalance;
    }
    public void setThirdBalance(Integer thirdBalance) {
        this.thirdBalance = thirdBalance;
    }
    @Override
    public String getStatus() {
        return status;
    }
    @Override
    public void setStatus(String status) {
        this.status = status;
    }
    public java.util.Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
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
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getCustomerAccountStatus() {
        return CustomerAccountStatus;
    }
    public void setCustomerAccountStatus(String customerAccountStatus) {
        CustomerAccountStatus = customerAccountStatus;
    }
    
}
