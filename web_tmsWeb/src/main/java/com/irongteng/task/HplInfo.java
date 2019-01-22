package com.irongteng.task;

import java.io.Serializable;

public class HplInfo implements Serializable{
    
    private static final long serialVersionUID = 992L;
        
    private Integer deviceID;  //设备ID
    private int commOperator;  //通信模式
    
    public Integer getDeviceID() {
        return deviceID;
    }
    public void setDeviceID(Integer deviceID) {
        this.deviceID = deviceID;
    }
    public int getCommOperator() {
        return commOperator;
    }
    public void setCommOperator(int commOperator) {
        this.commOperator = commOperator;
    }
    
}