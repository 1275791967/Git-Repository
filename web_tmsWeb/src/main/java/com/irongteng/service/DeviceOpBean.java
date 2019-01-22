package com.irongteng.service;

import java.util.Date;

import com.irongteng.persistence.beans.DeviceOp;

import dwz.framework.sys.business.AbstractBusinessObject;

public class DeviceOpBean extends AbstractBusinessObject{
    
    private static final long serialVersionUID = 1L;
    private DeviceOp deviceOP = null;

    @Override
    public Integer getId() {
        return this.deviceOP.getId();
    }

    public void setId(Integer id) {
        this.getDeviceOp().setId(id);
    }
    
    public DeviceOpBean() {
        this.deviceOP = new DeviceOp();
    }
    
    public DeviceOpBean(DeviceOp deviceOP){
         this.deviceOP=deviceOP;
    }
    
    public DeviceOp getDeviceOp(){
        return this.deviceOP;
    }
    
    public Integer getDeviceID(){
        return this.deviceOP.getDevice().getId();
    }

    public void setDeviceID(int deviceID){
        this.deviceOP.getDevice().setId(deviceID);;
    }
    public Integer getOp1() {
        return this.deviceOP.getOp1();
    }
    public void setOp1(Integer op1) {
        this.deviceOP.setOp1(op1);
    }
    public Integer getOp2() {
        return this.deviceOP.getOp2();
    }
    public void setOp2(Integer op2) {
        this.deviceOP.setOp2(op2);
    }
    public Integer getOp3() {
        return this.deviceOP.getOp3();
    }
    public void setOp3(Integer op3) {
        this.deviceOP.setOp3(op3);
    }
    public Integer getOp4() {
        return this.deviceOP.getOp4();
    }
    public void setOp4(Integer op4) {
        this.deviceOP.setOp4(op4);
    }
    public Integer getOp5() {
        return this.deviceOP.getOp5();
    }
    public void setOp5(Integer op5) {
        this.deviceOP.setOp5(op5);
    }
    public Date getUpdateTime() {
        return this.deviceOP.getUpdateTime();
    }
    public void setUpdateTime(Date updateTime) {
        this.deviceOP.setUpdateTime(updateTime);
    }
    public String getRemark() {
        return this.deviceOP.getRemark();
    }
    public void setRemark(String remark) {
        this.deviceOP.setRemark(remark);
    }
    
}
