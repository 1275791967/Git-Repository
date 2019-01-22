package com.irongteng.service;

import java.util.Date;
import com.irongteng.persistence.beans.LoadOP;

import dwz.framework.sys.business.AbstractBusinessObject;

public class LoadOPBean extends AbstractBusinessObject{
    
    private static final long serialVersionUID = 1L;
    private LoadOP loadOP  = null;

    @Override
    public Integer getId() {
        return this.loadOP.getId();
    }

    public void setId(Integer id) {
        this.getLoadOP().setId(id);
    }
    
    public LoadOPBean() {
        this.loadOP = new LoadOP();
    }
    
    public LoadOPBean(LoadOP loadOP){
         this.loadOP=loadOP;
    }
    
    public LoadOP getLoadOP(){
        return this.loadOP;
    }
    
    public Integer getDeviceID(){
        return this.loadOP.getDevice().getId();
    }

    public void setDeviceID(int deviceID){
        this.loadOP.getDevice().setId(deviceID);;
    }
    public Integer getOpNo() {
        return this.loadOP.getOpNo();
    }
    public void setOpNo(Integer opNo) {
        this.loadOP.setOpNo(opNo);
    }
    public Double getRatio() {
        return this.loadOP.getRatio();
    }
    public void setRatio(Double ratio) {
        this.loadOP.setRatio(ratio);
    }
    public Date getUpdateTime() {
        return this.loadOP.getUpdateTime();
    }
    public void setUpdateTime(Date updateTime) {
        this.loadOP.setUpdateTime(updateTime);
    }
}
