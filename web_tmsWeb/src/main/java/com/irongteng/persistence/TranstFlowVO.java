package com.irongteng.persistence;

import java.util.Date;

public class TranstFlowVO extends BaseConditionVO{
    
    private Integer Id; 
    private Integer logTypeID;//日志类型
    private String content;//传输内容
    private Integer fromDeviceID;//发起的设备id
    private Integer toDeviceID;//需要传输的设备（目的设备id）
    private String channelType;//翻译通道类型
    private String imsi;//imsi号码
    private String imei;//imei号码
    private String toDeviceName; //发起的设备名 
    private String fromDeviceName;//需要传输的设备名
    private String phone;//电话号码
    private Date recordTime;  //记录时间
    private String remark;//备注
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
    private Integer opInfoID;
    
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
    @Override
    public String getContent() {
        return content;
    }
    @Override
    public void setContent(String content) {
        this.content = content;
    }
    public Integer getFromDeviceID() {
        return fromDeviceID;
    }
    public void setFromDeviceID(Integer fromDeviceID) {
        this.fromDeviceID = fromDeviceID;
    }
    public Integer getToDeviceID() {
        return toDeviceID;
    }
    public void setToDeviceID(Integer toDeviceID) {
        this.toDeviceID = toDeviceID;
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
    public String getToDeviceName() {
        return toDeviceName;
    }
    public void setToDeviceName(String toDeviceName) {
        this.toDeviceName = toDeviceName;
    }
    public String getFromDeviceName() {
        return fromDeviceName;
    }
    public void setFromDeviceName(String fromDeviceName) {
        this.fromDeviceName = fromDeviceName;
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
    public Integer getOpInfoID() {
        return opInfoID;
    }
    public void setOpInfoID(Integer opInfoID) {
        this.opInfoID = opInfoID;
    }
}
