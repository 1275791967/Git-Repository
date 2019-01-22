package com.irongteng.service;

import java.util.Date;

import com.irongteng.persistence.beans.TranstFlow;

import dwz.framework.sys.business.AbstractBusinessObject;

public class TranstFlowBean extends AbstractBusinessObject{
 private static final long serialVersionUID = 1L;
    
    private  TranstFlow transtFlow;
    
    @Override
    public Integer getId() {
        return this.transtFlow.getId();
    }

    public void setId(Integer id) {
        this.getTranstFlow().setId(id);
    }
    
    public TranstFlowBean() {
        this.transtFlow = new TranstFlow();
    }
    
    public TranstFlowBean(TranstFlow transtFlow){
         this.transtFlow=transtFlow;
    }
    public TranstFlow getTranstFlow(){
        return  this.transtFlow;
    }
    
    public Integer getLogTypeID() {
        return this.transtFlow.getLogTypeID();
    }

    public void setLogTypeID(Integer logTypeID) {
        this.getTranstFlow().setLogTypeID(logTypeID);
    }
    
    public String getChannelType() {
        return this.transtFlow.getChannelType();
    }
    
    public void setChannelType(String channelType) {
        this.getTranstFlow().setChannelType(channelType);
    }
    
    public String getContent() {
        return this.transtFlow.getContent();
    }
    
    public void setContent(String content) {
        this.getTranstFlow().setContent(content);
    }
    
    public String getImsi() {
        return this.transtFlow.getImsi();
    }
    
    public void setImsi(String imsi) {
        this.getTranstFlow().setImsi(imsi);
    }
    
    public String getImei() {
        return this.transtFlow.getImei();
    }
    
    public void setImei(String imei) {
        this.getTranstFlow().setImei(imei);
    }
    
    public String getPhone() {
        return this.transtFlow.getPhone();
    }
    
    public void setPhone(String phone) {
        this.getTranstFlow().setPhone(phone);
    }
    
    public Date getRecordTime() {
        return this.transtFlow.getRecordTime();
    }
    
    public void setRecordTime(Date phone) {
        this.getTranstFlow().setRecordTime(phone);
    }
    
    public String getRemark() {
        return this.transtFlow.getRemark();
    }
    
    public void setRemark(String remark) {
        this.getTranstFlow().setRemark(remark);
    }

    
    //发起的设备FromDeviceID
    public Integer getFromDeviceID() {
        return this.transtFlow.getFromDeviceID();
    }

    public void setFromDeviceID(Integer fromDeviceID) {
        this.getTranstFlow().setFromDeviceID(fromDeviceID);
    }
    
    public Integer getFromCustomerID() {
        return this.transtFlow.getFromCustomerID();
    }

    public void setFromCustomerID(Integer fromcustomerID) {
        this.getTranstFlow().setFromCustomerID(fromcustomerID);
    }
    
    public String getFromDeviceName() {
        return this.transtFlow.getFromDeviceName();
    }
    
    public void setFromDeviceName(String fromdeviceName) {
        this.getTranstFlow().setFromDeviceName(fromdeviceName);
    }
    
    public Integer getFromDeviceType() {
        return this.transtFlow.getFromDeviceType();
    }

    public void setFromDeviceType(Integer fromdeviceType) {
        this.getTranstFlow().setFromDeviceType(fromdeviceType);
    }
    
    public String getFromFeatureCode() {
        return this.transtFlow.getFromFeatureCode();
    }
    
    public void setFromFeatureCode(String fromfeatureCode) {
        this.getTranstFlow().setFromFeatureCode(fromfeatureCode);
    }
    
    public String getFromLongitude() {
        return this.transtFlow.getFromLongitude();
    }
    
    public void setFromLongitude(String fromlongitude) {
        this.getTranstFlow().setFromLongitude(fromlongitude);
    }
    
    public String getFromLatitude() {
        return this.transtFlow.getFromLatitude();
    }
    
    public void setFromLatitude(String fromlatiude) {
        this.getTranstFlow().setFromLatitude(fromlatiude);
    }
    
    public String getFromAddress() {
        return this.transtFlow.getFromAddress();
    }
    
    public void setFromAddress(String fromaddress) {
        this.getTranstFlow().setFromAddress(fromaddress);
    }
 
    public Date getFromCreateTime() {
        return this.transtFlow.getFromCreateTime();
    }
    
    public void setFromCreateTime(Date fromcreateTime) {
        this.getTranstFlow().setFromCreateTime(fromcreateTime);
    }

    public String getFromRemark() {
        return this.transtFlow.getFromRemark();
    }
    
    public void setFromRemark(String fromremark) {
        this.getTranstFlow().setFromRemark(fromremark);
    }
    
    public String getFromDeviceIP() {
        return this.transtFlow.getFromDeviceIP();
    }
    
    public void setFromDeviceIP(String fromdeviceIP) {
        this.getTranstFlow().setFromDeviceIP(fromdeviceIP);
    }
    
    public String getFromAreaLocation() {
        return this.transtFlow.getFromAreaLocation();
    }
    
    public void setFromAreaLocation(String fromareaLocation) {
        this.getTranstFlow().setFromAreaLocation(fromareaLocation);
    }
    
    
    
    //目的的设备ToDeviceID
    public Integer getToDeviceID() {
        return this.transtFlow.getToDeviceID();
    }

    public void setToDeviceID(Integer toDeviceID) {
        this.getTranstFlow().setToDeviceID(toDeviceID);
    }
    
    public Integer getToCustomerID() {
        return this.transtFlow.getToCustomerID();
    }

    public void setToCustomerID(Integer tocustomerID) {
        this.getTranstFlow().setToCustomerID(tocustomerID);
    }
    
    public String getToDeviceName() {
        return this.transtFlow.getToDeviceName();
    }
    
    public void setToDeviceName(String todeviceName) {
        this.getTranstFlow().setToDeviceName(todeviceName);
    }
    
    public Integer getToDeviceType() {
        return this.transtFlow.getToDeviceType();
    }

    public void setToDeviceType(Integer todeviceType) {
        this.getTranstFlow().setToDeviceType(todeviceType);
    }
    
    public String getToFeatureCode() {
        return this.transtFlow.getToFeatureCode();
    }
    
    public void setToFeatureCode(String tofeatureCode) {
        this.getTranstFlow().setToFeatureCode(tofeatureCode);
    }
    
    public String getToLongitude() {
        return this.transtFlow.getToLongitude();
    }
    
    public void setToLongitude(String tolongitude) {
        this.getTranstFlow().setToLongitude(tolongitude);
    }
    
    public String getToLatitude() {
        return this.transtFlow.getToLatitude();
    }
    
    public void setToLatitude(String tolatiude) {
        this.getTranstFlow().setToLatitude(tolatiude);
    }
    
    public String getToAddress() {
        return this.transtFlow.getToAddress();
    }
    
    public void setToAddress(String toaddress) {
        this.getTranstFlow().setToAddress(toaddress);
    }
 
    public Date getToCreateTime() {
        return this.transtFlow.getToCreateTime();
    }
    
    public void setToCreateTime(Date tocreateTime) {
        this.getTranstFlow().setToCreateTime(tocreateTime);
    }

    public String getToRemark() {
        return this.transtFlow.getToRemark();
    }
    
    public void setToRemark(String toremark) {
        this.getTranstFlow().setToRemark(toremark);
    }
    
    public String getToDeviceIP() {
        return this.transtFlow.getToDeviceIP();
    }
    
    public void setToDeviceIP(String todeviceIP) {
        this.getTranstFlow().setToDeviceIP(todeviceIP);
    }
    
    public String getToAreaLocation() {
        return this.transtFlow.getToAreaLocation();
    }
    
    public void setToAreaLocation(String toareaLocation) {
        this.getTranstFlow().setToAreaLocation(toareaLocation);
    }
}
