package com.irongteng.service;

import java.util.Date;
import com.irongteng.persistence.beans.Device;
import com.irongteng.persistence.beans.DeviceRand;

import dwz.framework.sys.business.AbstractBusinessObject;

public class DeviceRandBean extends AbstractBusinessObject{
    
    private static final long serialVersionUID = 1L;
    private DeviceRand deviceRand = null;

    @Override
    public Integer getId() {
        return this.deviceRand.getId();
    }

    public void setId(Integer id) {
        this.getDeviceRand().setId(id);
    }
    
    public DeviceRandBean() {
        this.deviceRand = new DeviceRand();
    }
    
    public DeviceRandBean(DeviceRand deviceRand){
         this.deviceRand = deviceRand;
    }
    
    public DeviceRand getDeviceRand(){
        return this.deviceRand;
    }
    
    public Device getDevice() {
        return this.deviceRand.getDevice();
    }
    public void setDevice(Device device) {
        this.deviceRand.setDevice(device);
    }
    public Integer getDeviceID(){
        return this.deviceRand.getDeviceID();
    }
    public void setDeviceID(int deviceID){
        this.deviceRand.setDeviceID(deviceID);;
    }
    public Integer getRand() {
        return this.deviceRand.getRand();
    }
    public void setRand(Integer rand) {
        this.deviceRand.setRand(rand);
    }
    public Integer getNormalCh() {
        return this.deviceRand.getNormalCh();
    }
    public void setNormalCh(Integer normalCh) {
        this.deviceRand.setNormalCh(normalCh);
    }
    public Integer getdCount() {
        return this.deviceRand.getdCount();
    }
    public void setdCount(Integer dCount) {
        this.deviceRand.setdCount(dCount);
    }
    public Date getUpdateTime() {
        return this.deviceRand.getUpdateTime();
    }
    public void setUpdateTime(Date updateTime) {
        this.deviceRand.setUpdateTime(updateTime);
    }
    public String getRemark() {
        return this.deviceRand.getRemark();
    }
    public void setRemark(String remark) {
        this.deviceRand.setRemark(remark);
    }
    
}
