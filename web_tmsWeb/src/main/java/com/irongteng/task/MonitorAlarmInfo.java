package com.irongteng.task;

import java.io.Serializable;
import java.util.Date;

public class MonitorAlarmInfo implements Serializable{
    
    private static final long serialVersionUID = 992L;
        
    private Integer deviceID;
    private String deviceName;
    
    private Integer monitorID;
    private String monitorName;
    private Date recordTime;
    private int type; // 1-MAC 2-IMSI 3-IMEI
    private String content; //告警内容
    
    
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
    public String getMonitorName() {
        return monitorName;
    }
    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }
    public Integer getMonitorID() {
        return monitorID;
    }
    public void setMonitorID(Integer monitorID) {
        this.monitorID = monitorID;
    }
    public Date getRecordTime() {
        return recordTime;
    }
    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
}