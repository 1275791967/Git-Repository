package com.irongteng.persistence.beans;

import java.util.Date;

import dwz.dal.object.AbstractDO;

public class DeviceRand extends AbstractDO {
    private static final long serialVersionUID = -2549770144649770857L;
    
    private Integer Id;
    private Device device; //设备
    private Integer rand;   //rand值
    private Integer normalCh = 0; //正常通道数
    private Integer dCount = 0; //分配给前端的数量
    private Date updateTime;
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
    public Integer getRand() {
        return rand;
    }
    public void setRand(Integer rand) {
        this.rand = rand;
    }
    public Integer getNormalCh() {
        return normalCh;
    }
    public void setNormalCh(Integer normalCh) {
        this.normalCh = normalCh;
    }
    public Integer getdCount() {
        return dCount;
    }
    public void setdCount(Integer dCount) {
        this.dCount = dCount;
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
