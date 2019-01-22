package com.irongteng.persistence.beans;

import java.util.Date;

import dwz.dal.object.AbstractDO;

public class DeviceOp extends AbstractDO {
    private static final long serialVersionUID = -2549770144649770857L;
    
    private Integer Id;
    private Device device; //设备
    private Integer op1;   //运营商1
    private Integer op2;   //运营商2
    private Integer op3;   //运营商3
    private Integer op4;   //运营商4
    private Integer op5;   //运营商5
    private Date updateTime;//更新时间 
    private String remark;//说明
    
    
    @Override
    public Integer getId() {
        return Id;
    }
    public void setId(Integer id) {
        Id = id;
    }
    public Device getDevice() {
        return device;
    }
    public void setDevice(Device device) {
        this.device = device;
    }
    public Integer getDeviceID() {
        return this.device.getId();
    }
    public void setDeviceID(Integer deviceID) {
        this.device.setId(deviceID);
    }
    public Integer getOp1() {
        return op1;
    }
    public void setOp1(Integer op1) {
        this.op1 = op1;
    }
    public Integer getOp2() {
        return op2;
    }
    public void setOp2(Integer op2) {
        this.op2 = op2;
    }
    public Integer getOp3() {
        return op3;
    }
    public void setOp3(Integer op3) {
        this.op3 = op3;
    }
    public Integer getOp4() {
        return op4;
    }
    public void setOp4(Integer op4) {
        this.op4 = op4;
    }
    public Integer getOp5() {
        return op5;
    }
    public void setOp5(Integer op5) {
        this.op5 = op5;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
   
}
