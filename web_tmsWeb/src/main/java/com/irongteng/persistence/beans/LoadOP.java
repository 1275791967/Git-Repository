package com.irongteng.persistence.beans;

import java.util.Date;

import dwz.dal.object.AbstractDO;

public class LoadOP extends AbstractDO {
    private static final long serialVersionUID = -2549770144649770857L;
    
    private Integer Id;
    private Device device; //设备
    private Integer opNo;   //运营商编号
    private Double ratio;
    private Date updateTime;
    
    @Override
    public Integer getId() {
        return Id;
    }
    public void setId(Integer id) {
        Id = id;
    }
    public Integer getDeviceID() {
        return this.device.getId();
    }
    public void setDeviceID(Integer deviceID) {
        this.device.setId(deviceID);
    }
    public Device getDevice() {
        return device;
    }
    public void setDevice(Device device) {
        this.device = device;
    }
    public Integer getOpNo() {
        return opNo;
    }
    public void setOpNo(Integer opNo) {
        this.opNo = opNo;
    }
    public Double getRatio() {
        return ratio;
    }
    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
   
}
