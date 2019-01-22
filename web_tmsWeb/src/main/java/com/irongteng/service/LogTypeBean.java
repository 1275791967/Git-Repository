package com.irongteng.service;

import javax.xml.crypto.Data;

import com.irongteng.persistence.beans.LogType;

import dwz.framework.sys.business.AbstractBusinessObject;

public class LogTypeBean extends AbstractBusinessObject{
private static final long serialVersionUID = 1L;
    
    private  LogType logType;
    
    
    @Override
    public Integer getId() {
        return this.logType.getId();
    }

    public void setId(Integer id) {
        this.getLogType().setId(id);
    }
    
    public LogTypeBean() {
        this.logType = new LogType();
    }
    
    public LogTypeBean(LogType logType){
         this.logType=logType;
    }
    public LogType getLogType(){
        return  this.logType;
    }
    
    public String getName() {
        return this.logType.getName();
    }

    public void setName(String name) {
        this.getLogType().setName(name);
    }
    
    
    public String getStatus() {
        return this.logType.getStatus();
    }

    public void setStatus(String status) {
        this.getLogType().setStatus(status);
    }
    
    public Data getCreateTime() {
        return this.logType.getCreateTime();
    }
    public void setCreateTime(Data createTime) {
        this.getLogType().setCreateTime(createTime);
    }
    
    public String getRemark() {
        return this.logType.getRemark();
    }

    public void setRemark(String remark) {
        this.getLogType().setRemark(remark);
    }

}
