package com.irongteng.persistence.beans;


import java.util.Date;

import dwz.dal.object.AbstractDO;

public class CustomerAccount extends AbstractDO {
    
    private static final long serialVersionUID = -2549770144649770857L;
    
    private Integer ID;
    private String name;// 账户名
    private String psw;// 密码（加密过后）
    private String phone;// 电话号码
    private Integer localBalance;// 本地余额
    private Integer thirdBalance;// 第三方余额
    private String status;// 客户状态(使用,停用)
    private java.util.Date createDate;// 创建时间
    private String remark;// 说明
    private Device device;//设备信息表
    
    public CustomerAccount(){
        device=new Device();
    }
    
    public CustomerAccount(Integer id){
        this.ID = id;
    }
  
    @Override
    public Integer getId() {
        return ID;
    }

    public void setId(Integer id) {
        this.ID = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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