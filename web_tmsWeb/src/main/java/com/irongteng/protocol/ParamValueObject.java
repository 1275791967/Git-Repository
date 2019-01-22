package com.irongteng.protocol;

import java.util.Date;

public class ParamValueObject {
    private Integer deviceID;
    private Integer deviceNumber;
    private String deviceName;
    private String cityCode;
    private Short commPackageID; //包序号
    
    private Integer categoryID; //设备类型
    private Integer commCategory; //通信方式
    private String ip;         //IP地址
    private int port;          //端口号
    
    private Date startDate;    //开始时间
    private Date endDate;      //结束时间
    private Date recordDate;   //记录时间
    private boolean endTag;    //结束标识，用来标记查询设置是否结束的表示
    private int sendCount;         //已经发送次数
    private int maxSendCount = 3;  //最大发送次数，默认3次
    
    public Integer getDeviceID() {
        return deviceID;
    }
    public void setDeviceID(Integer deviceID) {
        this.deviceID = deviceID;
    }
    public String getDeviceName() {
        return deviceName;
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
    
    
    public String getCityCode() {
        return cityCode;
    }
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    
    /**
     * @return the categoryID
     */
    public Integer getCategoryID() {
        return categoryID;
    }

    /**
     * @param categoryID the categoryID to set
     */
    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }
    public int getCommCategory() {
        return commCategory;
    }
    public void setCommCategory(Integer commCategory) {
        this.commCategory = commCategory;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public boolean getEndTag() {
        return endTag;
    }
    public void setEndTag(boolean endTag) {
        this.endTag = endTag;
    }
    public int getSendCount() {
        return sendCount;
    }
    public void setSendCount(int sendCount) {
        this.sendCount = sendCount;
    }
    public int getMaxSendCount() {
        return maxSendCount;
    }
    public void setMaxSendCount(int maxSendCount) {
        this.maxSendCount = maxSendCount;
    }

    /**
     * @return the recordDate
     */
    public Date getRecordDate() {
        return recordDate;
    }

    /**
     * @param recordDate the recordDate to set
     */
    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    /**
     * @return the deviceNumber
     */
    public Integer getDeviceNumber() {
        return deviceNumber;
    }

    /**
     * @param deviceNumber the deviceNumber to set
     */
    public void setDeviceNumber(Integer deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    /**
     * @return the commPackageID
     */
    public Short getCommPackageID() {
        return commPackageID;
    }

    /**
     * @param commPackageID the commPackageID to set
     */
    public void setCommPackageID(Short commPackageID) {
        this.commPackageID = commPackageID;
    }
    
}
