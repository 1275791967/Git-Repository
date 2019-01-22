package com.irongteng.persistence.beans;

import java.util.Date;

import dwz.dal.object.AbstractDO;

public class TranstFlow extends AbstractDO{
    private static final long serialVersionUID = -2549770144648770857L;
    private Integer Id; 
    private Integer logTypeID;//日志类型
    private String content;//传输内容
    private String channelType;//翻译通道类型
    private String imsi;//imsi号码
    private String imei;//imei号码
    private String phone;//电话号码
    private Date recordTime;  //记录时间
    private String remark;//备注

//    private String DeviceName; //设备名 
    private String toDeviceName; //发起的设备名 
    private String fromDeviceName;//需要传输的设备名
    

    private Device fromDevice; //设备信息表（发起的设备）
    private Device toDevice;//设备信息表（目的的设备）
   

    public TranstFlow(){
        fromDevice=new Device();
        toDevice=new Device();
    }
    
    public TranstFlow(Integer id){
        this.Id = id;
    }
    
    @Override
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
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

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    //fromDevice发起的设备
    public Device getFromDevice() {
        return fromDevice;
    }

    public void setFromDevice(Device fromDevice) {
        this.fromDevice = fromDevice;
    }

    
    public Integer getFromDeviceID() {
        return this.fromDevice.getId();
    }
    public void setFromDeviceID(Integer fromDeviceID) {
        this.fromDevice.setId(fromDeviceID);
    }

    public Integer getFromCustomerID() {
        return this.fromDevice.getCustomerID();
    }

    public void setFromCustomerID(Integer fromcustomerID) {
        this.fromDevice.setCustomerID(fromcustomerID);;
    }

    public String getFromDeviceName() {
        return this.fromDevice.getDeviceName();
    }

    public void setFromDeviceName(String fromdeviceName) {
        this.fromDevice.setDeviceName(fromdeviceName);
    }

    public Integer getFromDeviceType() {
        return this.fromDevice.getDeviceType();
    }

    public void setFromDeviceType(Integer fromdeviceType) {
        this.fromDevice.setDeviceType(fromdeviceType);
    }

    public String getFromFeatureCode() {
        return this.fromDevice.getFeatureCode();
    }

    public void setFromFeatureCode(String fromfeatureCode) {
        this.fromDevice.setFeatureCode(fromfeatureCode);
    }

    public String getFromLongitude() {
        return this.fromDevice.getLongitude();
    }

    public void setFromLongitude(String fromlongitude) {
        this.fromDevice.setLongitude(fromlongitude);
    }

    public String getFromLatitude() {
        return this.fromDevice.getLatitude();
    }

    public void setFromLatitude(String fromlatitude) {
        this.fromDevice.setLatitude(fromlatitude);
    }

    public String getFromAddress() {
        return this.fromDevice.getAddress();
    }

    public void setFromAddress(String fromaddress) {
        this.fromDevice.setAddress(fromaddress);
    }

    public Date getFromCreateTime() {
        return this.fromDevice.getCreateTime();
    }

    public void setFromCreateTime(Date fromcreateTime) {
        this.fromDevice.setCreateTime(fromcreateTime);
    }

    public String getFromRemark() {
        return this.fromDevice.getRemark();
    }

    public void setFromRemark(String fromremark) {
        this.fromDevice.setRemark(fromremark);
    }

    public String getFromDeviceIP() {
        return this.fromDevice.getDeviceIP();
    }

    public void setFromDeviceIP(String fromdeviceIP) {
        this.fromDevice.setDeviceIP(fromdeviceIP);
    }

    public String getFromAreaLocation() {
        return this.fromDevice.getAreaLocation();
    }

    public void setFromAreaLocation(String fromareaLocation) {
        this.fromDevice.setAreaLocation(fromareaLocation);
    }
    
    //toDevice目的的设备
    public Device getToDevice() {
        return toDevice;
    }

    public void setToDevice(Device toDevice) {
        this.toDevice = toDevice;
    }
    
    
    public Integer getToDeviceID() {
        return this.toDevice.getId();
    }
    public void setToDeviceID(Integer toDeviceID) {
        this.toDevice.setId(toDeviceID);
    }
    
    public Integer getToCustomerID() {
        return this.toDevice.getCustomerID();
    }

    public void setToCustomerID(Integer tocustomerID) {
        this.toDevice.setCustomerID(tocustomerID);;
    }

    public String getToDeviceName() {
        return this.toDevice.getDeviceName();
    }

    public void setToDeviceName(String todeviceName) {
        this.toDevice.setDeviceName(todeviceName);
    }

    public Integer getToDeviceType() {
        return this.toDevice.getDeviceType();
    }

    public void setToDeviceType(Integer todeviceType) {
        this.toDevice.setDeviceType(todeviceType);
    }

    public String getToFeatureCode() {
        return this.toDevice.getFeatureCode();
    }

    public void setToFeatureCode(String tofeatureCode) {
        this.toDevice.setFeatureCode(tofeatureCode);
    }

    public String getToLongitude() {
        return this.toDevice.getLongitude();
    }

    public void setToLongitude(String tolongitude) {
        this.toDevice.setLongitude(tolongitude);
    }

    public String getToLatitude() {
        return this.toDevice.getLatitude();
    }

    public void setToLatitude(String tolatitude) {
        this.toDevice.setLatitude(tolatitude);
    }

    public String getToAddress() {
        return this.toDevice.getAddress();
    }

    public void setToAddress(String toaddress) {
        this.toDevice.setAddress(toaddress);
    }

    public Date getToCreateTime() {
        return this.toDevice.getCreateTime();
    }

    public void setToCreateTime(Date tocreateTime) {
        this.toDevice.setCreateTime(tocreateTime);
    }

    public String getToRemark() {
        return this.toDevice.getRemark();
    }

    public void setToRemark(String toremark) {
        this.toDevice.setRemark(toremark);
    }

    public String getToDeviceIP() {
        return this.toDevice.getDeviceIP();
    }

    public void setToDeviceIP(String todeviceIP) {
        this.toDevice.setDeviceIP(todeviceIP);
    }

    public String getToAreaLocation() {
        return this.toDevice.getAreaLocation();
    }

    public void setToAreaLocation(String toareaLocation) {
        this.toDevice.setAreaLocation(toareaLocation);
    }
}
