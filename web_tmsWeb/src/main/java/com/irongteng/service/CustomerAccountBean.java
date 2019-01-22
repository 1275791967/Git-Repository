package com.irongteng.service;


import java.util.Date;

import com.irongteng.persistence.beans.CustomerAccount;

import dwz.common.util.EnumUtils;
import dwz.framework.sys.business.AbstractBusinessObject;

public class CustomerAccountBean extends AbstractBusinessObject{
    private static final long serialVersionUID = 1L;
    private  CustomerAccount customerAccount;
    
    @Override
    public Integer getId() {
        return this.customerAccount.getId();
    }

    public void setId(Integer id) {
        this.getCustomerAccount().setId(id);
    }
   
    public CustomerAccountBean() {
        this.customerAccount = new CustomerAccount();
        this.customerAccount.setStatus(CustomerAccountStatus.ACTIVE.toString());
    }
    
    public CustomerAccountBean(CustomerAccount customerAccount){
         this.customerAccount=customerAccount;
    }
    public CustomerAccount getCustomerAccount(){
        return  this.customerAccount;
    }

    public String getName() {
        return this.customerAccount.getName();
    }

    public void setName(String name) {
        this.getCustomerAccount().setName(name);
    }
    
    public String getPsw() {
        return this.customerAccount.getPsw();
    }

    public void setPsw(String psw) {
        this.getCustomerAccount().setPsw(psw);
    }
    
    public String getPhone() {
        return this.customerAccount.getPhone();
    }

    public void setPhone(String phone) {
        this.getCustomerAccount().setPhone(phone);
    }
    
    public Integer getLocalBalance() {
        return this.customerAccount.getLocalBalance();
    }

    public void setLocalBalance(Integer localBalance) {
        this.getCustomerAccount().setLocalBalance(localBalance);
    }
    
    public Integer getThirdBalance() {
        return this.customerAccount.getThirdBalance();
    }

    public void setThirdBalance(Integer thirdBalance) {
        this.getCustomerAccount().setThirdBalance(thirdBalance);
    }
    
    public String getStatus() {
        return this.customerAccount.getStatus();
    }

    public void setStatus(String status) {
        this.getCustomerAccount().setStatus(status);
    }
    
    public java.util.Date getCreateDate() {
        return this.customerAccount.getCreateDate();
    }

    public void setCreateDate(java.util.Date createDate) {
        this.getCustomerAccount().setCreateDate(createDate);
    }
    
    public String getRemark() {
        return this.customerAccount.getRemark();
    }

    public void setRemark(String remark) {
        this.getCustomerAccount().setRemark(remark);
    }
    
    
    //设备信息
    public Integer getDeviceID() {
        return this.customerAccount.getDeviceID();
    }

    public void setDeviceID(Integer deviceID) {
        this.getCustomerAccount().setDeviceID(deviceID);
    }
    
    public Integer getCustomerID() {
        return this.customerAccount.getCustomerID();
    }

    public void setCustomerID(Integer ustomerID) {
        this.getCustomerAccount().setCustomerID(ustomerID);
    }
    
    public String getDeviceName() {
        return this.customerAccount.getDeviceName();
    }
    
    public void setDeviceName(String deviceName) {
        this.getCustomerAccount().setDeviceName(deviceName);
    }
    
    public Integer getDeviceType() {
        return this.customerAccount.getDeviceType();
    }

    public void setDeviceType(Integer deviceType) {
        this.getCustomerAccount().setDeviceType(deviceType);
    }
    
    public String getFeatureCode() {
        return this.customerAccount.getFeatureCode();
    }
    
    public void setFeatureCode(String featureCode) {
        this.getCustomerAccount().setFeatureCode(featureCode);
    }
    
    public String getLongitude() {
        return this.customerAccount.getLongitude();
    }
    
    public void setLongitude(String longitude) {
        this.getCustomerAccount().setLongitude(longitude);
    }
    
    public String getLatitude() {
        return this.customerAccount.getLatitude();
    }
    
    public void setLatitude(String latiude) {
        this.getCustomerAccount().setLatitude(latiude);
    }
    
    public String getAddress() {
        return this.customerAccount.getAddress();
    }
    
    public void setAddress(String address) {
        this.getCustomerAccount().setAddress(address);
    }
    
    public Date getCreateTime() {
        return this.customerAccount.getCreateTime();
    }
    
    public void setCreateTime(Date reateTime) {
        this.getCustomerAccount().setCreateTime(reateTime);
    }

    public String getDeviceIP() {
        return this.customerAccount.getDeviceIP();
    }
    
    public void setDeviceIP(String deviceIP) {
        this.getCustomerAccount().setDeviceIP(deviceIP);
    }
    
    public String getAreaLocation() {
        return this.customerAccount.getAreaLocation();
    }
    
    public void setAreaLocation(String areaLocation) {
        this.getCustomerAccount().setAreaLocation(areaLocation);
    }
    
    public CustomerAccountStatus getTypeofStatus() {
        if (EnumUtils.isDefined(CustomerAccountStatus.values(), customerAccount.getStatus()))
            return CustomerAccountStatus.valueOf(customerAccount.getStatus());

        return CustomerAccountStatus.INACTIVE;
    }

}
